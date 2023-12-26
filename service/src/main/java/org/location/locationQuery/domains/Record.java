package org.location.locationQuery.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.r2dbc.postgresql.codec.Json;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "record")
public class Record {

    @Id
    private Long id;


    private Json record;
}
