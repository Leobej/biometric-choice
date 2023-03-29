package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.FingerprintDTO;
import com.votemetric.biometricchoice.dto.LocationDTO;
import com.votemetric.biometricchoice.service.FingerprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fingerprints")
@CrossOrigin(origins = "http://localhost:8080")
public class FingerprintController {

    private final FingerprintService fingerprintService;

    @Autowired
    public FingerprintController(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FingerprintDTO> getFingerprintById(@PathVariable("id") Long id) {
        FingerprintDTO fingerprintDTO = fingerprintService.getFingerprintById(id);
        return ResponseEntity.ok(fingerprintDTO);
    }

    @GetMapping
    public ResponseEntity<List<FingerprintDTO>> getAllFingerprints() {
        List<FingerprintDTO> fingerprints = fingerprintService.getFingerprints();
        return ResponseEntity.ok(fingerprints);
    }





    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFingerprintById(@PathVariable("id") Long id) {
        fingerprintService.deleteFingerprintById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
