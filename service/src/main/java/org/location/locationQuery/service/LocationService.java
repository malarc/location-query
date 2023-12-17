package org.location.locationQuery.service;

import org.location.locationQuery.domains.LocationMaster;
import org.location.locationQuery.models.Location;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Flux<LocationMaster> searchByAttributes(String locationType, String locationName, String locationCode, String locationCodeType);
}
