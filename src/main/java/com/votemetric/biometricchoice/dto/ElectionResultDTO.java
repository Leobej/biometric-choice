package com.votemetric.biometricchoice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionResultDTO {
    private Long electionResultsId;
    private Long electionId;
    private Long candidateId;
    private Long votes;
    private Double percentage;
}
