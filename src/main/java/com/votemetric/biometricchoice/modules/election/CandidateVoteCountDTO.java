package com.votemetric.biometricchoice.modules.election;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateVoteCountDTO {
    private Long candidateId;
    private Long voteCount;

}
