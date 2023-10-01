package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.VoterDTO;
import com.votemetric.biometricchoice.entity.Voter;
import com.votemetric.biometricchoice.exception.VoterNotFoundException;
import com.votemetric.biometricchoice.interfaces.IVoterService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterService implements IVoterService {
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private Mapper mapper;



    @Override
    public Page<VoterDTO> getAllVoters(Pageable pageable) {
        Page<Voter> candidates = voterRepository.findAll(pageable);
        return candidates.map((voter) -> mapper.convertToType(voter, VoterDTO.class));

    }

    @Override
    public Page<VoterDTO> getVoterByName(String description, Pageable pageable) {
        Page<Voter> candidates = voterRepository.getVotersByFirstname(description, pageable);
        return candidates.map(voter -> mapper.convertToType(voter, VoterDTO.class));
    }

    @Override
    public VoterDTO getVoterById(Long voterId) {
        Voter voter = findVoterById(voterId);
        return mapper.convertToType(voter, VoterDTO.class);
    }

    @Override
    public VoterDTO saveVoter(VoterDTO voterDTO) {
        Voter voter = mapper.convertToType(voterDTO, Voter.class);
        Voter savedVoter = voterRepository.save(voter);
        return mapper.convertToType(savedVoter, VoterDTO.class);
    }

    @Override
    public VoterDTO updateVoter(VoterDTO voterDTO) {
        checkIfVoterWithFingerprintIdExists(voterDTO.getFingerprintId());
        Voter voter = mapper.convertToType(voterDTO, Voter.class);
        Voter updatedVoter = voterRepository.save(voter);
        return mapper.convertToType(updatedVoter, VoterDTO.class);
    }

    @Override
    public void deleteVoterById(Long voterId) {
        checkIfVoterExists(voterId);
        voterRepository.deleteById(voterId);
    }

    Voter findVoterById(Long voterId) {
        return voterRepository.findById(voterId).orElseThrow(
                () -> new VoterNotFoundException(voterId));
    }

    void checkIfVoterExists(Long voterId) {
        boolean exists = voterRepository.existsById(voterId);
        if (!exists) {
            throw new VoterNotFoundException(voterId);
        }
    }

    void checkIfVoterWithFingerprintIdExists(Long fingerprintID) {
//        boolean exists = voterRepository.existsByFingerprints(fingerprintID);
//        if (!exists) {
//            throw new VoterNotFoundException(fingerprintID);
//        }
    }

}
