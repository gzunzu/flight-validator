package org.gzunzu.flightvalidator.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReverseGeodataResponseDTO implements Serializable {

    private static final long serialVersionUID = 60606060L;

    private String type;
    private List<FeatureDTO> features;
    private QueryDTO query;
}
