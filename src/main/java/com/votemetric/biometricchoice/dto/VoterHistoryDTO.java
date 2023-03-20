package com.votemetric.biometricchoice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoterHistoryDTO {
    private long voterId;
    private Long electionId;
    private Long candidateId;
    private LocalDateTime dateVoted;
    private String location;
    private boolean voteStatus;
}
