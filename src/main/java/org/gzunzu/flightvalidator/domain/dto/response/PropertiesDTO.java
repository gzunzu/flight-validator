package org.gzunzu.flightvalidator.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.TimeZone;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PropertiesDTO implements Serializable {

    private final static long serialVersionUID = 20202020L;

    private String name;
    private String country;
    private String country_code;
    private String state;
    private String city;
    private String portal_code;
    private String district;
    private String suburb;
    private String street;
    private String housenumber;
    private double lat;
    private double lon;
    private double distance;
    private String result_type;
    private String formatted;
    private String address_line1;
    private String address_line2;
    private String category;
    private TimeZone timezone;
    private String plus_code;
    private String place_id;
}
