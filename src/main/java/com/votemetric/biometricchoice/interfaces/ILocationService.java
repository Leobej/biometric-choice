package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.LocationDTO;

import java.util.List;

public interface ILocationService {
    LocationDTO getLocationById(Long locationId);

    List<LocationDTO> getAllLocations();

    LocationDTO createLocation(LocationDTO locationDto);

    LocationDTO updateLocation(LocationDTO locationDto);

    void deleteLocationById(Long locationId);
}
