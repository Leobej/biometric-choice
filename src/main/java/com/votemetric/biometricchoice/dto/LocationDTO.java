package com.votemetric.biometricchoice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {
    private long locationId;
    private String postalCode;
    private String country;
    private String city;
    private String street;
    private String number;
}
