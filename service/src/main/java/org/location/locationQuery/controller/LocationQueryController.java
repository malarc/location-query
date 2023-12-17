package org.location.locationQuery.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.models.Location;
import org.location.locationQuery.models.LocationSearchRequest;
import org.location.locationQuery.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/locations")
public class LocationQueryController {


    private final LocationService locationService;

    @PostMapping("/search")
    public Flux<LocationMaster> searchByAttributes(@RequestBody LocationSearchRequest request) {
        return locationService.searchByAttributes(request.getLocationType(),request.getLocationId(),request.getLocationCodeType(),request.getLocationName());
    }

}
