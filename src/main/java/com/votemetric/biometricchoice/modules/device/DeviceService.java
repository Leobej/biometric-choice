package com.votemetric.biometricchoice.modules.device;

import com.votemetric.biometricchoice.exception.DeviceNotFoundException;
import com.votemetric.biometricchoice.interfaces.IDeviceService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;
    private final Mapper mapper;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, Mapper mapper) {
        this.deviceRepository = deviceRepository;
        this.mapper = mapper;
    }

//    @Override
//    public List<DeviceDTO> getAllDevices() {
//        List<Device> devices = deviceRepository.findAll();
//        return devices.stream()
//                .map((device) -> mapper.convertToType(device, DeviceDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        return null;
    }

    @Override
    public Page<DeviceDTO> getAllDevices(Pageable pageable) {
        Page<Device> devices = deviceRepository.findAll(pageable);
        return devices.map(device -> mapper.convertToType(device, DeviceDTO.class));
    }
    @Override
    public Page<DeviceDTO> getAllDevices(String search, Pageable pageable) {
        Page<Device> devices;
        if (search != null && !search.trim().isEmpty()) {
            devices = deviceRepository.findByNameContainingOrTypeContainingOrStatusContaining(search, search, search, pageable);
        } else {
            devices = deviceRepository.findAll(pageable);
        }
        return devices.map(device -> mapper.convertToType(device, DeviceDTO.class));
    }

    @Override
    public DeviceDTO getDeviceById(Long id) throws DeviceNotFoundException {
        Device device = findDeviceById(id);
        return mapper.convertToType(device, DeviceDTO.class);
    }

    @Override
    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        Device device = mapper.convertToType(deviceDTO, Device.class);
        Device savedDevice = deviceRepository.save(device);
        return mapper.convertToType(savedDevice, DeviceDTO.class);
    }

    @Override
    public DeviceDTO updateDevice(DeviceDTO deviceDTO) {
        checkIfDeviceExist(deviceDTO.getId());
        Device deviceSave = mapper.convertToType(deviceDTO, Device.class);
        deviceRepository.save(deviceSave);
        return deviceDTO;
    }

    @Override
    public void deleteDevice(Long id) {
        checkIfDeviceExist(id);
        deviceRepository.deleteById(id);
    }

    Device findDeviceById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));
    }

    void checkIfDeviceExist(Long id) {
        boolean exists = deviceRepository.existsById(id);
        if (!exists) {
            throw new DeviceNotFoundException(id);
        }
    }
}
