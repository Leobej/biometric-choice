package com.votemetric.biometricchoice.service;


import com.votemetric.biometricchoice.dto.ElectionDTO;
import com.votemetric.biometricchoice.dto.ElectionResultDTO;
import com.votemetric.biometricchoice.entity.Election;
import com.votemetric.biometricchoice.entity.ElectionResult;
import com.votemetric.biometricchoice.exception.ElectionNotFoundException;
import com.votemetric.biometricchoice.exception.ElectionResultNotFoundException;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.IElectionResultService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.ElectionResultRepository;
import org.springframework.stereotype.Service;

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
    public ElectionResultDTO getElectionResultById(Long electionResultId) {
        ElectionResult election = findElectionResultById(electionResultId);
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
    public ElectionResultDTO createElectionResult(ElectionResultDTO electionResultDTO) {
        ElectionResult election = mapper.convertToType(electionResultDTO, ElectionResult.class);
        ElectionResult savedElectionResult = electionResultRepository.save(election);
        return mapper.convertToType(savedElectionResult, ElectionResultDTO.class);
    }

    @Override
    public ElectionResultDTO updateElectionResult(ElectionResultDTO electionResultDTO) {
        checkIfElectionResultExists(electionResultDTO.getElectionId());
        ElectionResult electionResult = mapper.convertToType(electionResultDTO, ElectionResult.class);
        ElectionResult savedElectionResult = electionResultRepository.save(electionResult);
        return mapper.convertToType(savedElectionResult, ElectionResultDTO.class);
    }

    @Override
    public void deleteElectionResultById(Long electionResultId) {
        checkIfElectionResultExists(electionResultId);
        electionResultRepository.deleteById(electionResultId);
    }

    ElectionResult findElectionResultById(Long electionResultId) {
        return electionResultRepository.findById(electionResultId).orElseThrow(
                () -> new ElectionResultNotFoundException(electionResultId));
    }

    void checkIfElectionResultExists(Long id) {
        boolean exists = electionResultRepository.existsById(id);
        if (!exists) {
            throw new ElectionResultNotFoundException(id);
        }
    }
}
