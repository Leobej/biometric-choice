package com.votemetric.biometricchoice.interfaces;


import com.votemetric.biometricchoice.dto.ElectionResultDTO;

import java.util.List;

public interface IElectionResultService {

    ElectionResultDTO getElectionResultById(Long electionResultId);

    List<ElectionResultDTO> getAllElectionResults();

    ElectionResultDTO createElectionResult(ElectionResultDTO electionResultDTO);

    ElectionResultDTO updateElectionResult(ElectionResultDTO electionResultDTO);

    void deleteElectionResultById(Long electionResultId);
}
