package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.ElectionDTO;
import com.votemetric.biometricchoice.entity.Candidate;
import com.votemetric.biometricchoice.entity.Election;
import com.votemetric.biometricchoice.entity.Voter;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.IElectionService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.CandidateRepository;
import com.votemetric.biometricchoice.repository.ElectionRepository;
import com.votemetric.biometricchoice.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService implements IElectionService {

    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;
    private final Mapper mapper;

    public ElectionService(ElectionRepository electionRepository, CandidateRepository candidateRepository, VoterRepository voterRepository, Mapper mapper) {
        this.electionRepository = electionRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
        this.mapper = mapper;
    }

    @Override
    public ElectionDTO getElectionById(Long electionId) {
        Election election = electionRepository.findById(electionId).orElseThrow(() -> new EntityNotFoundException("Election with id " + electionId + " not found."));
        return mapper.convertToType(election, ElectionDTO.class);
    }

    @Override
    public List<ElectionDTO> getAllElections() {
        List<Election> elections = electionRepository.findAll();
        return elections.stream()
                .map(election -> mapper.convertToType(election, ElectionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ElectionDTO createElection(ElectionDTO electionDto) {
        Candidate candidate = candidateRepository.findById(electionDto.getCandidateId())
                .orElseThrow(() -> new EntityNotFoundException("Candidate with id " + electionDto.getCandidateId() + " not found."));
        Voter voter = voterRepository.findById(electionDto.getVoterId())
                .orElseThrow(() -> new EntityNotFoundException("Voter with id " + electionDto.getVoterId() + " not found."));

        Election election = mapper.convertToType(electionDto, Election.class);
        election.setCreatedAt(LocalDateTime.now());
        election = electionRepository.save(election);
        return mapper.convertToType(election, ElectionDTO.class);
    }

    @Override
    public ElectionDTO updateElection() {

    }

    @Override
    public void deleteElectionById(Long electionId) {
        if (!electionRepository.existsById(electionId)) {
            throw new EntityNotFoundException("Election with id " + electionId + " not found.");
        }
        electionRepository.deleteById(electionId);
    }
}
