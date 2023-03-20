package com.votemetric.biometricchoice.service;


import com.votemetric.biometricchoice.dto.LocationDTO;
import com.votemetric.biometricchoice.dto.VoterHistoryDTO;
import com.votemetric.biometricchoice.entity.Location;
import com.votemetric.biometricchoice.entity.VoterHistory;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.ILocationService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.LocationRepository;
import com.votemetric.biometricchoice.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class LocationService implements ILocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private Mapper mapper;



    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> mapper.convertToType(location, LocationDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long locationId) {
        Location location = locationRepository.findById(locationId).orElseThrow(
                () -> new EntityNotFoundException(""));
        return mapper.convertToType(location, LocationDTO.class);
    }

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = mapper.convertToType(locationDTO, Location.class);
        Location savedLocation = locationRepository.save(location);
        return mapper.convertToType(savedLocation, LocationDTO.class);
    }

    @Override
    public LocationDTO updateLocation(LocationDTO locationDTO) {
        Location voter = locationRepository.findById(locationDTO.getLocationId()).orElseThrow(
                () -> new EntityNotFoundException("")
        );
       Location location= mapper.convertToType(locationDTO, Location.class);
       Location savedLocation= locationRepository.save(location);
        return mapper.convertToType(savedLocation, LocationDTO.class);


    }

    @Override
    public void deleteLocationById(Long locationId) {
        locationRepository.deleteById(locationId);
    }
}
