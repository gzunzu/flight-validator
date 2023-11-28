package org.gzunzu.flightvalidator.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QueryDTO implements Serializable {

    private static final long serialVersionUID = 50505050L;

    private double lat;
    private double lon;
    private String plus_code;
}
