package com.votemetric.biometricchoice.modules.electiondevices;

import com.votemetric.biometricchoice.interfaces.IElectionDeviceService;
import com.votemetric.biometricchoice.modules.device.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/election-devices")
public class ElectionDeviceController {

    @Autowired
    private IElectionDeviceService electionDeviceService;

    @PostMapping("/add")
    public ResponseEntity<ElectionDeviceDTO> addDeviceToElection(@RequestBody ElectionDeviceDTO electionDeviceDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(electionDeviceService.addDeviceToElection(electionDeviceDTO.getElectionId(), electionDeviceDTO.getDeviceId()));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeDeviceFromElection(@PathVariable long id) {
        electionDeviceService.removeDeviceFromElection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<DeviceDTO>> getAllDevicesForElection(@PathVariable long electionId) {
        return ResponseEntity.ok(electionDeviceService.getAllDevicesForElection(electionId));
    }

    @PutMapping("/edit/{electionId}")
    public ResponseEntity<List<DeviceDTO>> editDeviceOfElection(@PathVariable long electionId, @RequestBody List<Long> deviceIds) {
        electionDeviceService.editElectionDevices(electionId, deviceIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}