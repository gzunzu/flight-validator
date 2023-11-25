package org.gzunzu.flightvalidator.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Flight implements Serializable {

    private static final long serialVersionUID = 987654321L;

    private String flightNumber;

    private short paxCount;

    private LocalTime takeOffTime;

    private double departureLatitude;

    private double departureLongitude;

    private double arrivalLatitude;

    private double arrivalLongitude;
}
