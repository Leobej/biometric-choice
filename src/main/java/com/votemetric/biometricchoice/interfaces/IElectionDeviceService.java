package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.device.DeviceDTO;
import com.votemetric.biometricchoice.modules.electiondevices.ElectionDeviceDTO;

import java.util.List;

public interface IElectionDeviceService {
    ElectionDeviceDTO addDeviceToElection(long electionId, long deviceId);

    void removeDeviceFromElection(long id);

    List<DeviceDTO> getAllDevicesForElection(long electionId);

    void editElectionDevices(long electionId, List<Long> newDeviceIds);
}
