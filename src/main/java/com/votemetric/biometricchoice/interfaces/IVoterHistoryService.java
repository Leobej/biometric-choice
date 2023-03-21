package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.VoterHistoryDTO;

import java.util.List;

public interface IVoterHistoryService {
    List<VoterHistoryDTO> getAllVoterHistory();
    VoterHistoryDTO getVoterHistoryById(Long voterId);
    VoterHistoryDTO saveVoterHistory(VoterHistoryDTO voterHistoryDTO);
    VoterHistoryDTO updateVoterHistory( VoterHistoryDTO voterHistoryDTO);
    void deleteVoterHistoryById(Long voterId);
}
