package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.exception.DeviceNotFoundException;
import com.votemetric.biometricchoice.modules.device.DeviceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDeviceService {

    Page<DeviceDTO> getAllDevices(String search, Pageable pageable);

    DeviceDTO getDeviceById(Long id) throws DeviceNotFoundException;


    DeviceDTO addDevice(DeviceDTO deviceDTO);

    DeviceDTO updateDevice(DeviceDTO deviceDTO) throws DeviceNotFoundException;

    void deleteDevice(Long id) throws DeviceNotFoundException;
}
