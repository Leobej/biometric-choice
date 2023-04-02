package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.LocationDTO;
import com.votemetric.biometricchoice.interfaces.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final ILocationService locationService;

    @Autowired
    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable("id") Long id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        return ResponseEntity.ok(locationDTO);
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        List<LocationDTO> locationDTOList = locationService.getAllLocations();
        return ResponseEntity.ok(locationDTOList);
    }

    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocationDTO = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(createdLocationDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO updatedLocationDTO = locationService.updateLocation(locationDTO);
        return ResponseEntity.ok(updatedLocationDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationById(@PathVariable("id") Long id) {
        locationService.deleteLocationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
