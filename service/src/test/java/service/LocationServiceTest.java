package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.repository.LocationRepository;
import org.location.locationQuery.service.LocationService;
import org.location.locationQuery.service.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { LocationServiceImpl.class })
public class LocationServiceTest {

    @MockBean
    private LocationRepository locationRepository;

    @Autowired
    private LocationServiceImpl locationService;


    @Test
    public void testSearchattributes() throws JsonProcessingException {

        LocationMaster locationMaster = LocationMaster.builder().locationId("xyz").locationName("xyz").status("Active").build();

        when(locationRepository.searchByAttributes(any(),any(),any(),any())).thenReturn(Flux.just(locationMaster));


        Flux<LocationMaster> location = locationService.searchByAttributes("city","Arizona","12345","code12345");

        StepVerifier.create(location)
                .expectNext(locationMaster)
                 .expectComplete();

    }

}
