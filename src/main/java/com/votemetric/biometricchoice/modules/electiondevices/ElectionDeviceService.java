package com.votemetric.biometricchoice.modules.electiondevices;


import com.votemetric.biometricchoice.interfaces.IElectionDeviceService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.modules.device.DeviceDTO;
import com.votemetric.biometricchoice.modules.device.DeviceRepository;
import com.votemetric.biometricchoice.modules.election.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElectionDeviceService implements IElectionDeviceService {

    @Autowired
    private ElectionDeviceRepository electionDeviceRepository;
    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public ElectionDeviceDTO addDeviceToElection(long electionId, long deviceId) {
        var election = electionRepository.findById(electionId)
                .orElseThrow(() -> new RuntimeException("Election not found"));
        var device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        var electionDevice = new ElectionDevice();
        electionDevice.setElection(election);
        electionDevice.setDevice(device);
        electionDevice = electionDeviceRepository.save(electionDevice);
        return mapper.convertToType(electionDevice, ElectionDeviceDTO.class);
    }

    @Override
    public void removeDeviceFromElection(long id) {
        electionDeviceRepository.deleteById(id);
    }

    @Override
    public List<DeviceDTO> getAllDevicesForElection(long electionId) {
        List<ElectionDevice> electionDevices = electionDeviceRepository.findAllByElection_ElectionId(electionId);
        return electionDevices.stream()
                .map(ElectionDevice::getDevice)
                .map(device -> deviceRepository.findById(device.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(device -> mapper.convertToType(device, DeviceDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public void editElectionDevices(long electionId, List<Long> newDeviceIds) {
        // Fetch the current devices associated with the election
        List<ElectionDevice> currentDevices = electionDeviceRepository.findAllByElection_ElectionId(electionId);

        // Extract the IDs of current devices
        Set<Long> currentDeviceIds = currentDevices.stream()
                .map(device -> device.getDevice().getId())
                .collect(Collectors.toSet());

        // Determine devices to add and to remove
        Set<Long> devicesToAdd = new HashSet<>(newDeviceIds);
        devicesToAdd.removeAll(currentDeviceIds);

        Set<Long> devicesToRemove = new HashSet<>(currentDeviceIds);
        devicesToRemove.removeAll(newDeviceIds);

        // Add new devices
        for (Long deviceId : devicesToAdd) {
            addDeviceToElection(electionId, deviceId);
        }

        // Remove unwanted devices
        for (ElectionDevice device : currentDevices) {
            if (devicesToRemove.contains(device.getDevice().getId())) {
                electionDeviceRepository.delete(device);
            }
        }
    }
}