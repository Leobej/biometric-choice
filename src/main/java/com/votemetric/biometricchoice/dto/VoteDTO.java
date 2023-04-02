package com.votemetric.biometricchoice.dto;

import com.votemetric.biometricchoice.entity.Candidate;
import com.votemetric.biometricchoice.entity.Election;
import com.votemetric.biometricchoice.entity.Voter;
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
