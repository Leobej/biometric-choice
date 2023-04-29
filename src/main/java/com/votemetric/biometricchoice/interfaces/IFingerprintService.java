package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.FingerprintDTO;

import java.util.List;

public interface IFingerprintService {
    FingerprintDTO getFingerprintById(Long fingerprintId);

    List<FingerprintDTO> getFingerprints();

    void deleteFingerprintById(Long fingerprintId);

    FingerprintDTO createFingerprint(FingerprintDTO fingerprintDTO);

    Long getFingerprintByDeviceId(String deviceId);
}
