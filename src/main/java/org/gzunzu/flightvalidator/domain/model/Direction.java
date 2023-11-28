package org.gzunzu.flightvalidator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Direction implements Serializable {

    private static final long serialVersionUID = 10101010L;

    private Cardinal lat;
    private Cardinal lng;

    @Override
    public String toString() {
        return this.lat.name() + this.lng.name();
    }
}
