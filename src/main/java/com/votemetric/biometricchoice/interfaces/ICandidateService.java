package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.dto.CandidateDTO;

import java.util.List;

public interface ICandidateService {

    List<CandidateDTO> getAllCandidates();

    CandidateDTO getCandidateById(Long id);

    CandidateDTO addCandidate(CandidateDTO candidateDTO);

    CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO);

    void deleteCandidate(Long id);
}