package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.ElectionDTO;
import com.votemetric.biometricchoice.entity.Election;
import com.votemetric.biometricchoice.exception.ElectionNotFoundException;
import com.votemetric.biometricchoice.exception.ElectionResultNotFoundException;
import com.votemetric.biometricchoice.interfaces.IElectionService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.ElectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService implements IElectionService {

    private final ElectionRepository electionRepository;
    private final Mapper mapper;

    public ElectionService(ElectionRepository electionRepository, Mapper mapper) {
        this.electionRepository = electionRepository;

        this.mapper = mapper;
    }

    @Override
    public ElectionDTO getElectionById(Long electionId) {
        Election election = findElectionById(electionId);
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
    public Page<ElectionDTO> getElectionsByDescription(String description, Pageable pageable) {
        Page<Election> elections = electionRepository.findByDescriptionContaining(description, pageable);
        return elections.map(election -> mapper.convertToType(election, ElectionDTO.class));
    }


    public Page<ElectionDTO> getAllElections(Pageable pageable) {
        Page<Election> elections = electionRepository.findAll(pageable);
        return elections.map(election -> mapper.convertToType(election, ElectionDTO.class));
    }

    @Override
    public ElectionDTO createElection(ElectionDTO electionDto) {
        Election election = mapper.convertToType(electionDto, Election.class);
        election.setCreatedAt(LocalDateTime.now());
        election = electionRepository.save(election);
        return mapper.convertToType(election, ElectionDTO.class);
    }

    @Override
    public ElectionDTO updateElection(ElectionDTO electionDto) {
        checkIfElectionExists(electionDto.getElectionId());
        electionRepository.save(mapper.convertToType(electionDto, Election.class));
        return electionDto;
    }

    @Override
    public void deleteElectionById(Long electionId) {
        checkIfElectionExists(electionId);
        electionRepository.deleteById(electionId);
    }

    Election findElectionById(Long electionId) {
        return electionRepository.findById(electionId).orElseThrow(
                () -> new ElectionNotFoundException(electionId));
    }

    void checkIfElectionExists(Long id) {
        boolean exists = electionRepository.existsById(id);
        if (!exists) {
            throw new ElectionResultNotFoundException(id);
        }
    }
}
