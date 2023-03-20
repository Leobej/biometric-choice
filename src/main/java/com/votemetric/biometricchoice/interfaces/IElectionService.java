package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.ElectionDTO;

import java.util.List;

public interface IElectionService {
    ElectionDTO getElectionById(Long electionId);

    List<ElectionDTO> getAllElections();

    ElectionDTO createElection(ElectionDTO electionDto);

    ElectionDTO updateElection(ElectionDTO electionDTO);

    void deleteElectionById(Long electionId);
}