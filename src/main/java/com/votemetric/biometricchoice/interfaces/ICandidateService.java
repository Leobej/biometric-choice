package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.candidate.CandidateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICandidateService {

//    List<CandidateDTO> getAllCandidates();

    Page<CandidateDTO> getAllCandidates(Pageable pageable);

    Page<CandidateDTO> getCandidateByName(String description, Pageable pageable);

    CandidateDTO getCandidateById(Long id);

    CandidateDTO addCandidate(CandidateDTO candidateDTO);

    CandidateDTO updateCandidate(CandidateDTO candidateDTO);

    void deleteCandidate(Long id);

    List<CandidateDTO> searchCandidates(String query);
}
