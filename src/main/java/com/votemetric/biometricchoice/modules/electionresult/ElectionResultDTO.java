package com.votemetric.biometricchoice.modules.electionresult;

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
    private Long associatedElectionId;
    private Long candidateId;
    private Long votes;
}
