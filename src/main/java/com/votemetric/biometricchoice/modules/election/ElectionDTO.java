package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.modules.candidate.CandidateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDTO {
    private Long electionId;
//    private Long voterId;
    private String description;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    private List<CandidateDTO> candidates = new ArrayList<>();
}
