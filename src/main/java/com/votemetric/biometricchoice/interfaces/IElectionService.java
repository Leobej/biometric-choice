package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.election.ElectionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IElectionService {
    ElectionDTO getElectionById(Long electionId);

    List<ElectionDTO> getAllElections();

    Page<ElectionDTO> getAllElections(Pageable pageable);

    Page<ElectionDTO> getElectionsByDescription(String description, Pageable pageable);


    ElectionDTO createElection(ElectionDTO electionDto);

    ElectionDTO updateElection(ElectionDTO electionDTO);

    void deleteElectionById(Long electionId);
}