package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.voter.VoterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVoterService {

    Page<VoterDTO> getAllVoters(Pageable pageable);

    Page<VoterDTO> getVoterByName(String description, Pageable pageable);

    VoterDTO getVoterById(Long voterId);

    VoterDTO saveVoter(VoterDTO voterHistoryDTO);

    VoterDTO updateVoter(VoterDTO voterHistoryDTO);

    void deleteVoterById(Long voterId);
}
