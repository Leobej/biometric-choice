package com.votemetric.biometricchoice.modules.location;

import com.votemetric.biometricchoice.interfaces.ILocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final ILocationService locationService;

    public LocationController(ILocationService locationService) {
        this.locationService = locationService;
    }

    // Retrieve a single location by ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDTO> getLocationById(@PathVariable("id") Long id) {
        LocationDTO locationDTO = locationService.getLocationById(id);
        return ResponseEntity.ok(locationDTO);
    }

    // Retrieve all locations with pagination
    @GetMapping("")
    public ResponseEntity<Page<LocationDTO>> getAllLocations(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable) {
        Page<LocationDTO> page;
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            page = locationService.getLocationsBySearchTerm(searchTerm, pageable);
        } else {
            page = locationService.getAllLocations(pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    // Create a new location
    @PostMapping
    public ResponseEntity<LocationDTO> createLocation(@RequestBody LocationDTO locationDTO) {
        LocationDTO createdLocationDTO = locationService.createLocation(locationDTO);
        return new ResponseEntity<>(createdLocationDTO, HttpStatus.CREATED);
    }

    // Update an existing location
    @PutMapping("/{id}")
    public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO locationDTO, @PathVariable("id") Long id) {
        locationDTO.setLocationId(id); // Ensure the ID is set correctly
        LocationDTO updatedLocationDTO = locationService.updateLocation(locationDTO);
        return ResponseEntity.ok(updatedLocationDTO);
    }

    // Delete a location
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocationById(@PathVariable("id") Long id) {
        locationService.deleteLocationById(id);
        return ResponseEntity.noContent().build();
    }
}
