package com.votemetric.biometricchoice.modules.election;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDTO {
    private Long electionId;
    private Long voterId;
    private Long candidateId;
    private String description;
    private String location;
    private LocalDateTime createdAt;

    private Boolean active;
}
