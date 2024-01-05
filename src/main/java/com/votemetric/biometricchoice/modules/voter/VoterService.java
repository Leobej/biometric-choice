package com.votemetric.biometricchoice.modules.voter;

import com.votemetric.biometricchoice.exception.VoterNotFoundException;
import com.votemetric.biometricchoice.interfaces.IVoterService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

//    @Override
//    public Page<VoterDTO> getVotersByName(String query, Pageable pageable) {
//        Page<Voter> voters;
//        if (query != null && !query.trim().isEmpty()) {
//            // Search by both first name and last name using the single 'query' parameter
//            voters = voterRepository.findByFirstnameContainingOrLastnameContaining(query, query, pageable);
//        } else {
//            // Return all voters if no search criteria are provided
//            return getAllVoters(pageable);
//        }
//        return voters.map(voter -> mapper.convertToType(voter, VoterDTO.class));
//    }

    @Override
    public VoterDTO getVoterById(Long voterId) {
        Voter voter = findVoterById(voterId);
        return mapper.convertToType(voter, VoterDTO.class);
    }

    @Override
    public VoterDTO saveVoter(VoterDTO voterDTO) {
        Voter voter = mapper.convertToType(voterDTO, Voter.class);
        voter.setCreatedAt(String.valueOf(new java.sql.Timestamp(System.currentTimeMillis())));
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

    @Override
    public Page<VoterDTO> getVotersByName(String search, Pageable pageable) {
        Page<Voter> voters = voterRepository.findByFirstnameContainingOrLastnameContaining(search, search, pageable);
        return voters.map(voter -> mapper.convertToType(voter, VoterDTO.class));
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
