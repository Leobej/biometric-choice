package com.votemetric.biometricchoice.modules.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CandidateNameDTO {
    private Long candidateId;
    private String firstname;
    private String lastname;

}
