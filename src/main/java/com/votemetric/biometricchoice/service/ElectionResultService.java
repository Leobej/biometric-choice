package com.votemetric.biometricchoice.service;


import com.votemetric.biometricchoice.dto.ElectionResultDTO;
import com.votemetric.biometricchoice.entity.ElectionResult;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.IElectionResultService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.ElectionResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionResultService implements IElectionResultService {

    private final ElectionResultRepository electionResultRepository;

    private final Mapper mapper;

    public ElectionResultService(ElectionResultRepository electionResultRepository, Mapper mapper) {
        this.electionResultRepository = electionResultRepository;
        this.mapper = mapper;
    }


    @Override
    public ElectionResultDTO getElectionResultById(Long electionId) {
        ElectionResult election = electionResultRepository.findById(electionId).orElseThrow(() -> new EntityNotFoundException("Election with id " + electionId + " not found."));
        return mapper.convertToType(election, ElectionResultDTO.class);
    }

    @Override
    public List<ElectionResultDTO> getAllElectionResults() {
        List<ElectionResult> elections = electionResultRepository.findAll();
        return elections.stream()
                .map(election -> mapper.convertToType(election, ElectionResultDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ElectionResultDTO createElectionResult(ElectionResultDTO electionDto) {
//        Candidate candidate = candidateRepository.findById(electionDto.getCandidateId())
//                .orElseThrow(() -> new EntityNotFoundException("Candidate with id " + electionDto.getCandidateId() + " not found."));
//        Voter voter = voterRepository.findById(electionDto.getVoterId())
//                .orElseThrow(() -> new EntityNotFoundException("Voter with id " + electionDto.getVoterId() + " not found."));

        ElectionResult election = mapper.convertToType(electionDto, ElectionResult.class);
        ElectionResult savedElection = electionResultRepository.save(election);
        return mapper.convertToType(savedElection, ElectionResultDTO.class);
    }

    @Override
    public ElectionResultDTO updateElectionResult(ElectionResultDTO electionDto) {
        electionResultRepository.existsById(electionDto.getElectionId());
        electionResultRepository.save(mapper.convertToType(electionDto, ElectionResult.class));
        return electionDto;
    }

    @Override
    public void deleteElectionResultById(Long electionId) {

        electionResultRepository.deleteById(electionId);
    }
}
