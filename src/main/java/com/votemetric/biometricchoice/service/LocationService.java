package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.LocationDTO;
import com.votemetric.biometricchoice.entity.Location;
import com.votemetric.biometricchoice.exception.LocationNotFoundException;
import com.votemetric.biometricchoice.interfaces.ILocationService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final Mapper mapper;

    public LocationService(LocationRepository locationRepository, Mapper mapper) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    // Retrieve all locations with pagination
    @Override
    public Page<LocationDTO> getAllLocations(Pageable pageable) {
        Page<Location> locations = locationRepository.findAll(pageable);
        return locations.map(location -> mapper.convertToType(location, LocationDTO.class));
    }

    // Retrieve a single location by ID
    @Override
    public LocationDTO getLocationById(Long locationId) {
        Location location = findLocationById(locationId);
        return mapper.convertToType(location, LocationDTO.class);
    }

    // Create a new location
    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = mapper.convertToType(locationDTO, Location.class);
        location = locationRepository.save(location);
        return mapper.convertToType(location, LocationDTO.class);
    }

    // Update an existing location
    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO) {
        checkIfLocationExists(locationDTO.getLocationId());
        locationRepository.save(mapper.convertToType(locationDTO, Location.class));
        return locationDTO;
    }

    // Delete a location
    @Override
    public void deleteLocationById(Long locationId) {
        checkIfLocationExists(locationId);
        locationRepository.deleteById(locationId);
    }

    private Location findLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(
                () -> new LocationNotFoundException(locationId));
    }

    private void checkIfLocationExists(Long id) {
        boolean exists = locationRepository.existsById(id);
        if (!exists) {
            throw new LocationNotFoundException(id);
        }
    }

    @Override
    public Page<LocationDTO> getLocationsBySearchTerm(String searchTerm, Pageable pageable) {
        Page<Location> locations = locationRepository.findByCityContaining(searchTerm, pageable);
        return locations.map(location -> mapper.convertToType(location, LocationDTO.class));
    }
}
