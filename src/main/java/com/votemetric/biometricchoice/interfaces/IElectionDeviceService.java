package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.electiondevices.ElectionDeviceDTO;

import java.util.List;

public interface IElectionDeviceService {
    ElectionDeviceDTO addDeviceToElection(long electionId, long deviceId);
    void removeDeviceFromElection(long id);
    List<ElectionDeviceDTO> getAllDevicesForElection(long electionId);
}
