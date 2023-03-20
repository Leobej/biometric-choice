package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.CandidateDTO;
import com.votemetric.biometricchoice.entity.Candidate;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.ICandidateService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public CandidateDTO getCandidateById(Long id) throws EntityNotFoundException {
        Candidate candidate = findCandidateById(id);
        return mapper.convertToType(candidate, CandidateDTO.class);
    }

    @Override
    public CandidateDTO addCandidate(CandidateDTO candidateDTO) {
        Candidate candidate = mapper.convertToType(candidateDTO, Candidate.class);
        candidate.setCreatedAt(LocalDateTime.now());
        candidate = candidateRepository.save(candidate);
        return mapper.convertToType(candidate, CandidateDTO.class);
    }

    @Override
    public CandidateDTO updateCandidate(Long id, CandidateDTO candidateDTO) throws EntityNotFoundException {
        Candidate candidate = findCandidateById(id);
        Candidate candidateSave = mapper.convertToType(candidateDTO, Candidate.class);
        candidateSave.setCreatedAt(candidate.getCreatedAt());
        candidateRepository.save(candidateSave);
        return candidateDTO;
    }

    @Override
    public void deleteCandidate(Long id) throws EntityNotFoundException {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
        candidateRepository.delete(candidate);
    }

    Candidate findCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id: " + id));
    }
}
