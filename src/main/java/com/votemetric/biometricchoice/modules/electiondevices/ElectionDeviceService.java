package com.votemetric.biometricchoice.modules.electiondevices;


import com.votemetric.biometricchoice.interfaces.IElectionDeviceService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.modules.device.DeviceRepository;
import com.votemetric.biometricchoice.modules.election.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<ElectionDeviceDTO> getAllDevicesForElection(long electionId) {
        return electionDeviceRepository.findAllByElection_ElectionId(electionId)
                .stream()
                .map(device -> mapper.convertToType(device, ElectionDeviceDTO.class))
                .collect(Collectors.toList());
    }
}