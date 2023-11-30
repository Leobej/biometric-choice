package com.votemetric.biometricchoice.modules.device;

import com.votemetric.biometricchoice.interfaces.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private IDeviceService deviceService;

//    @GetMapping
//    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
//        List<DeviceDTO> devices = deviceService.getAllDevices();
//        return new ResponseEntity<>(devices, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<Page<DeviceDTO>> getAllDevices(Pageable pageable) {
        Page<DeviceDTO> devices = deviceService.getAllDevices(pageable);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable("id") Long id) {
        DeviceDTO device = deviceService.getDeviceById(id);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DeviceDTO> addDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.addDevice(deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.updateDevice(deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable("id") Long id) {
        deviceService.deleteDevice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
