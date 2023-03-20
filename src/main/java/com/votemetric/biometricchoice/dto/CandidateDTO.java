package com.votemetric.biometricchoice.dto;

import com.votemetric.biometricchoice.entity.Candidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDTO {
    private long candidateId;
    private String firstname;
    private String lastname;
    private String party;
    private String position;

}
