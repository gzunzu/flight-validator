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
public class FeatureDTO implements Serializable {

    private static final long serialVersionUID = 30303030L;

    private String type;
    private PropertiesDTO properties;
    private GeometryDTO geometry;
    private double[] bbox;
}
