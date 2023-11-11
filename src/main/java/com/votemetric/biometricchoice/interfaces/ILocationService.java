package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.LocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILocationService {
    LocationDTO getLocationById(Long locationId);

    Page<LocationDTO> getAllLocations(Pageable pageable);

    LocationDTO createLocation(LocationDTO locationDto);

    LocationDTO updateLocation(LocationDTO locationDto);

    void deleteLocationById(Long locationId);

    Page<LocationDTO> getLocationsBySearchTerm(String searchTerm, Pageable pageable);

}
