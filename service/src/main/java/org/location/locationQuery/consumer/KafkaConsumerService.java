package org.location.locationQuery.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.domains.Record;
import org.location.locationQuery.exception.CustomException;
import org.location.locationQuery.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;
import reactor.util.retry.Retry;

import java.nio.charset.StandardCharsets;
import java.time.Duration;


@RequiredArgsConstructor
@EnableKafka
@Slf4j
@Service
public class KafkaConsumerService implements CommandLineRunner {

    @Value("classpath:schema.sql")
    private Resource initSql;

    private final R2dbcEntityTemplate entityTemplate;


    private final ReactiveKafkaConsumerTemplate<String, String> kafkaConsumerTemplate;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private final LocationRepository locationRepository;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<ReceiverRecord<String, String>> consumeMessages() {
      log.info(" Kafka consumer listener");
        return    kafkaConsumerTemplate.receive()
                .doOnNext(record -> processKafkaRecords(record)
                        .doOnError(e -> new CustomException("Kafka exception occured " + e.getMessage()))
                        .subscribe());
    }

    @SneakyThrows
    private Mono<LocationMaster> processKafkaRecords(ReceiverRecord<String ,String> record) {
        var locationMaster = OBJECT_MAPPER.readValue(record.value(), LocationMaster.class);

        return entityTemplate.insert(locationMaster)
                .doOnSuccess(savedEntity -> {
                    log.info("Entity saved: {}", savedEntity);
                    record.receiverOffset().acknowledge();
                })
                .doOnError(DataIntegrityViolationException.class, this::handleDataIntegrityViolation);
    }

    @SneakyThrows
    private Disposable processRecords(ReceiverRecord<String ,String> record) {
      //  var locationMaster = OBJECT_MAPPER.readValue(record.value(), Record.class);
        //return entityTemplate.insert(record.value());
        //Record rec = new Record();
//        return this.entityTemplate.insert(Record.class)
//                .using(rec)
//                .map(loc -> loc.getId());
        var rec = OBJECT_MAPPER.readValue(record.value(), Record.class);
       return  entityTemplate
                .getDatabaseClient()
                .sql("INSERT INTO  Record (record) VALUES (:record)")
                .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                //.bind("title", "my first post")
                //.bind("id", id)
                .bind("record", rec)
                .fetch()
                .first()
                .subscribe(
                        data -> log.info("inserted data : {}", data),
                        error -> log.info("error: {}", error)
                );

    }


    private Mono<Object> handleError(Throwable error, String message) {
        log.error(message);
        return Mono.error(new CustomException("Exception occured in the save operation "+message));
    }

    private Mono<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: " + ex.getMessage());
        return Mono.error(new CustomException("Exception occured in the save operation "+ex.getMessage()));
    }

    private Flux<Object> handleDatabaseFailure(Throwable ex) {
       log.error("Database failure: " + ex.getMessage());
        return Flux.error(ex)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)).maxBackoff(Duration.ofSeconds(5)));
    }

    @Override
    public void run(String... args) throws Exception {
        String query = StreamUtils.copyToString(initSql.getInputStream(), StandardCharsets.UTF_8);
        this.entityTemplate
                .getDatabaseClient()
                .sql(query)
                .then()
                .subscribe();

    }
}

