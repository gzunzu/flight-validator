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
public class GeometryDTO implements Serializable {

    private final static long serialVersionUID = 40404040L;

    private String type;
    private double[] coordinates;
}
