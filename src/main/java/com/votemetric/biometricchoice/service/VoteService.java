package com.votemetric.biometricchoice.service;

import com.votemetric.biometricchoice.dto.VoteDTO;
import com.votemetric.biometricchoice.entity.Vote;
import com.votemetric.biometricchoice.exception.VoteNotFoundException;
import com.votemetric.biometricchoice.interfaces.IVoteService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService implements IVoteService {

    private final VoteRepository voteRepository;
    private final Mapper mapper;

    @Autowired
    public VoteService(VoteRepository voteRepository, Mapper mapper) {
        this.voteRepository = voteRepository;
        this.mapper = mapper;
    }

    @Override
    public List<VoteDTO> getAllVotes() {
        List<Vote> votes = voteRepository.findAll();
        return votes.stream()
                .map(vote -> mapper.convertToType(vote, VoteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VoteDTO getVoteById(Long id) throws VoteNotFoundException {
        Vote vote = findVoteById(id);
        return mapper.convertToType(vote, VoteDTO.class);
    }

    @Override
    public VoteDTO addVote(VoteDTO voteDTO) {
        Vote vote = mapper.convertToType(voteDTO, Vote.class);
        Vote savedVote = voteRepository.save(vote);
        return mapper.convertToType(savedVote, VoteDTO.class);
    }

    @Override
    public VoteDTO updateVote(VoteDTO voteDTO) {
        checkIfVoteExist(voteDTO.getVoteId());
        Vote voteSave = mapper.convertToType(voteDTO, Vote.class);
        voteRepository.save(voteSave);
        return voteDTO;
    }

    @Override
    public void deleteVote(Long id) {
        checkIfVoteExist(id);
        voteRepository.deleteById(id);
    }

    Vote findVoteById(Long id) {
        return voteRepository.findById(id)
                .orElseThrow(() -> new VoteNotFoundException(id));
    }

    void checkIfVoteExist(Long id) {
        boolean exists = voteRepository.existsById(id);
        if (!exists) {
            throw new VoteNotFoundException(id);
        }
    }
}

