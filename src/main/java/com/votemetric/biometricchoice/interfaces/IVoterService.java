package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.VoterDTO;

import java.util.List;

public interface IVoterService {
    List<VoterDTO> getAllVoters();
    VoterDTO getVoterById(long voterId);
    VoterDTO saveVoter(VoterDTO voterHistoryDTO);
    VoterDTO updateVoter( VoterDTO voterHistoryDTO);
    void deleteVoterById(long voterId);
}
