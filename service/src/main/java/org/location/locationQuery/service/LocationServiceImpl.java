package org.location.locationQuery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.exception.CustomException;
import org.location.locationQuery.repository.LocationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService{


    private LocationRepository locationRepository;

    @Override
    public Flux<LocationMaster> searchByAttributes(String locationType, String locationCode, String locationCodeType, String locationName) {
        return locationRepository.searchByAttributes(locationType,locationCode,locationCodeType,locationName)
               .switchIfEmpty(Mono.error(new CustomException("Entity not found with search criteria locationType,locationCode,locationName ")));
    }
}
