package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.CandidateDTO;
import com.votemetric.biometricchoice.dto.CandidateNameDTO;
import com.votemetric.biometricchoice.entity.Candidate;
import com.votemetric.biometricchoice.exception.CandidateNotFoundException;
import com.votemetric.biometricchoice.interfaces.ICandidateService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService implements ICandidateService {

    private final CandidateRepository candidateRepository;
    private final Mapper mapper;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository, Mapper mapper) {
        this.candidateRepository = candidateRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CandidateDTO> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .map((candidate) -> mapper.convertToType(candidate, CandidateDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CandidateDTO getCandidateById(Long id) throws CandidateNotFoundException {
        Candidate candidate = findCandidateById(id);
        return mapper.convertToType(candidate, CandidateDTO.class);
    }

    @Override
    public CandidateDTO addCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = mapper.convertToType(candidateDTO, Candidate.class);
        Candidate savedCandidate = candidateRepository.save(candidate);
        return mapper.convertToType(savedCandidate, CandidateDTO.class);
    }

    @Override
    public CandidateDTO updateCandidate(CandidateDTO candidateDTO) {
        checkIfCandidateExist(candidateDTO.getCandidateId());
        Candidate candidateSave = mapper.convertToType(candidateDTO, Candidate.class);
        candidateRepository.save(candidateSave);
        return candidateDTO;
    }

    @Override
    public void deleteCandidate(Long id) {
        checkIfCandidateExist(id);
        candidateRepository.deleteById(id);
    }

    @Override
    public List<CandidateDTO> searchCandidates(String query) {
        List<Candidate> candidates = candidateRepository.searchCandidates(query);
        return candidates.stream()
                .map((candidate) -> mapper.convertToType(candidate, CandidateDTO.class))
                .collect(Collectors.toList());
    }

    Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new CandidateNotFoundException(id));
    }

    void checkIfCandidateExist(Long id) {
        boolean exists = candidateRepository.existsById(id);
        if (!exists) {
            throw new CandidateNotFoundException(id);
        }
    }

    public List<CandidateNameDTO> findCandidatesByFirstnameOrLastname(String query) {
        return candidateRepository.findCandidatesByFirstnameOrLastname(query);
    }
}
