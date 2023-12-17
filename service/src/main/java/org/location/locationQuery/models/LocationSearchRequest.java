package org.location.locationQuery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationSearchRequest {

    private String locationName;
    private String locationId;
    private String locationType;
    private String locationCodeType;
}
