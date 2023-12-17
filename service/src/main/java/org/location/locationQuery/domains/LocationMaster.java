package org.location.locationQuery.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@Data
@NoArgsConstructor
@Getter
@Setter
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

    private String  latitude;

    private String longitude;



}
