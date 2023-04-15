package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.DeviceDTO;
import com.votemetric.biometricchoice.exception.DeviceNotFoundException;

import java.util.List;

public interface IDeviceService {

    List<DeviceDTO> getAllDevices();

    DeviceDTO getDeviceById(Long id) throws DeviceNotFoundException;

    DeviceDTO addDevice(DeviceDTO deviceDTO);

    DeviceDTO updateDevice(DeviceDTO deviceDTO) throws DeviceNotFoundException;

    void deleteDevice(Long id) throws DeviceNotFoundException;
}
