package org.location.locationQuery.repository;

import org.location.locationQuery.domains.LocationMaster;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LocationRepository extends ReactiveCrudRepository<LocationMaster, Long> {

        @Query("SELECT * FROM location_master l " +
                "WHERE " +
                "(:locationType is null OR l.location_type = :locationType) AND " +
                "(:locationId is null OR l.location_id = :locationId) AND " +
                "(:locationCodeType is null OR l.location_code_type = :locationCodeType) AND " +
                "(" +
                "LOWER(l.location_name) LIKE LOWER(CONCAT('%', :locationName, '%')) OR " +
                "LOWER(SUBSTRING(l.location_name FROM 1 FOR 3)) LIKE LOWER(CONCAT(:locationName, '%'))" +
                ")")
        Flux<LocationMaster> searchByAttributes(
                @Param("locationType") String locationType,
                @Param("locationId") String locationId,
                @Param("locationCodeType") String locationCodeType,
                @Param("locationName") String locationName);

}
