package com.votemetric.biometricchoice.interfaces;

import com.votemetric.biometricchoice.modules.vote.VoteDTO;
import com.votemetric.biometricchoice.exception.CandidateNotFoundException;
import com.votemetric.biometricchoice.exception.VoterNotFoundException;

import java.util.List;

public interface IVoteService {
    List<VoteDTO> getAllVotes();

    VoteDTO getVoteById(Long id);

    VoteDTO addVote(VoteDTO voteDTO) throws CandidateNotFoundException, VoterNotFoundException;

    VoteDTO updateVote(VoteDTO voteDTO) throws CandidateNotFoundException, VoterNotFoundException;

    void deleteVote(Long id);
}