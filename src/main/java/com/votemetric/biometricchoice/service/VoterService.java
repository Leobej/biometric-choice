package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.VoterDTO;

import com.votemetric.biometricchoice.entity.Voter;
import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.interfaces.IVoterService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.VoterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoterService implements IVoterService {
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public List<VoterDTO> getAllVoters() {
        List<Voter> voters = voterRepository.findAll();
        return voters.stream()
                .map(voter -> mapper.convertToType(voter, VoterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VoterDTO getVoterById(long voterId) {
        Voter voter = voterRepository.findById(voterId).orElseThrow(
                () -> new EntityNotFoundException(""));
        return mapper.convertToType(voter, VoterDTO.class);
    }

    @Override
    public VoterDTO saveVoter(VoterDTO voterDTO) {
        Voter voter = mapper.convertToType(voterDTO, Voter.class);
        Voter savedVoter = voterRepository.save(voter);
        return mapper.convertToType(savedVoter, VoterDTO.class);
    }

    @Override
    public VoterDTO updateVoter(VoterDTO voterDTO) {
        Voter voter = voterRepository.findById(voterDTO.getVoterId()).orElseThrow(
                () -> new EntityNotFoundException("")
        );
        mapper.convertToType(voterDTO, Voter.class);
        Voter updatedVoter = voterRepository.save(voter);
        return mapper.convertToType(updatedVoter, VoterDTO.class);
    }

    @Override
    public void deleteVoterById(long voterId) {
        voterRepository.deleteById(voterId);
    }
}
