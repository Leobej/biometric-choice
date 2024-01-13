package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.modules.candidate.CandidateDTO;
import com.votemetric.biometricchoice.modules.electionresult.ElectionResultDTO;
import com.votemetric.biometricchoice.modules.voter.VoterDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDetailDTO {
    private Long electionId;
    private String description;
    private Long locationId;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;

    // Detailed information
    private List<VoterDTO> voters;
    private List<CandidateDTO> candidates;
    private List<ElectionResultDTO> electionResults;
}

