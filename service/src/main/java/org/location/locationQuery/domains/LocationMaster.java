package org.location.locationQuery.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.location.locationQuery.models.AlternateCodes;
import org.location.locationQuery.models.Country;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "location_master")
public class LocationMaster {

    @Id
    private Long id;

    private String locationName;

    private String locationId;

    private String status;

    private String locationType;

    private String locationCodeType;

    private String  latitude;

    private String longitude;

    private Country country;



    @JsonCreator
    public LocationMaster(
            @JsonProperty("name") String locationName,
            @JsonProperty("locationId") String locationId,
            @JsonProperty("status") String status,
            @JsonProperty("latitude") String latitude,
            @JsonProperty("longitude") String longitude,
            @JsonProperty("geoType") String locationType,
           @JsonProperty("country") Country country

    ) {
        this.locationName = locationName;
        this.locationId = locationId;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationType = locationType;
       this.country = country;

    }



}
