package org.location.locationQuery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationSearchRequest {

    @Pattern(regexp = "^[a-zA-Z]+$")
    private String locationName;

    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String locationId;

    private String locationType;

    private String locationCodeType;
}
