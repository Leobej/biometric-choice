package com.votemetric.biometricchoice.service;


import com.votemetric.biometricchoice.dto.FingerprintDTO;
import com.votemetric.biometricchoice.dto.LocationDTO;
import com.votemetric.biometricchoice.entity.Fingerprint;
import com.votemetric.biometricchoice.entity.Location;
import com.votemetric.biometricchoice.exception.ElectionNotFoundException;
import com.votemetric.biometricchoice.exception.LocationNotFoundException;
import com.votemetric.biometricchoice.interfaces.IFingerprintService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.FingerprintRepository;
import com.votemetric.biometricchoice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FingerprintService implements IFingerprintService {
    @Autowired
    private FingerprintRepository fingerprintRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public FingerprintDTO getFingerprintById(Long fingerprintId) {
        Fingerprint location = findFingerPrintById(fingerprintId);
        return mapper.convertToType(location, FingerprintDTO.class);
    }

    @Override
    public List<FingerprintDTO> getFingerprints() {
        List<Fingerprint> fingerprints = fingerprintRepository.findAll();
        return fingerprints.stream()
                .map(fingerprint -> mapper.convertToType(fingerprint, FingerprintDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFingerprintById(Long fingerprintId) {
        checkIfFingerPrintExists(fingerprintId);
        fingerprintRepository.deleteById(fingerprintId);
    }

    Fingerprint findFingerPrintById(Long locationId) {
        return fingerprintRepository.findById(locationId).orElseThrow(
                () -> new ElectionNotFoundException(locationId));
    }


    private void checkIfFingerPrintExists(Long fingerprintId) {
        boolean exists = fingerprintRepository.existsById(fingerprintId);
        if (!exists) {
            throw new LocationNotFoundException(fingerprintId);
        }
    }
}