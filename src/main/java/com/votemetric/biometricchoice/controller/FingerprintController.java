package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.FingerprintDTO;
import com.votemetric.biometricchoice.mqtt.MqttPublisher;
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
    private final MqttPublisher mqttPublisher;

    @Autowired
    public FingerprintController(FingerprintService fingerprintService, MqttPublisher mqttPublisher) {
        this.fingerprintService = fingerprintService;
        this.mqttPublisher = mqttPublisher;
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

    @PostMapping
    public ResponseEntity<FingerprintDTO> addFingerprint(@RequestBody FingerprintDTO fingerprintDTO) {
        FingerprintDTO createdFingerprintDTO = fingerprintService.createFingerprint(fingerprintDTO);
        return new ResponseEntity<>(createdFingerprintDTO, HttpStatus.CREATED);
    }

    @GetMapping("/getFingerprintId/{deviceId}")
    public ResponseEntity<Long> getFingerprintId(@PathVariable ("deviceId")String deviceId) {
        Long id = fingerprintService.getFingerprintByDeviceId(deviceId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/nextFingerprint")
    public void sendNextFingerprint() {
        mqttPublisher.publish("inTopic", "nextFingerprint");
    }
}
