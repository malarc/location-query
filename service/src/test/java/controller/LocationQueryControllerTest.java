package controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.location.locationQuery.controller.LocationQueryController;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.models.LocationSearchRequest;
import org.location.locationQuery.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = LocationQueryController.class)
@ContextConfiguration(classes = { LocationQueryController.class })
public class LocationQueryControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testLocationQuery_ResponseOK() throws Exception {
        LocationMaster locationMaster = LocationMaster.builder().locationId("xyz").locationName("xyz").build();
        LocationSearchRequest LocationSearchRequest = new LocationSearchRequest();
        LocationSearchRequest.setLocationId("xyz");
        LocationSearchRequest.setLocationName("abc");
        LocationSearchRequest.setLocationType("city");
        LocationSearchRequest.setLocationCodeType("city1");

        when(locationService.searchByAttributes(any(), any(),any(),any())).thenReturn(Flux.just(locationMaster));
        webTestClient.post()
                .uri(builder -> builder.path("/locations/search")
                        .build())
                .body(Mono.just(LocationSearchRequest), LocationSearchRequest.class).exchange()
                .expectStatus().isOk();
    }
}
