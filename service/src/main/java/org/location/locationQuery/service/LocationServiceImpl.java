package org.location.locationQuery.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.models.Location;
import org.location.locationQuery.repository.LocationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService{


    private LocationRepository locationRepository;

    @Override
    public Flux<LocationMaster> searchByAttributes(String locationType, String locationCode, String locationCodeType, String locationName) {
        return locationRepository.searchByAttributes(locationType,locationCode,locationCodeType,locationName);
    }
}
