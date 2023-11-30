package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.location.LocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILocationService {
    LocationDTO getLocationById(Long locationId);

    Page<LocationDTO> getAllLocations(Pageable pageable);

    LocationDTO createLocation(LocationDTO locationDto);

    LocationDTO updateLocation(LocationDTO locationDto);

    void deleteLocationById(Long locationId);

    Page<LocationDTO> getLocationsBySearchTerm(String searchTerm, Pageable pageable);

}
