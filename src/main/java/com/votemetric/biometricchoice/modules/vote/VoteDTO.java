package com.votemetric.biometricchoice.modules.vote;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.election.Election;
import com.votemetric.biometricchoice.modules.voter.Voter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
    private Long voteId;
    private Election election;
    private Voter voter;
    private Candidate candidate;
}
