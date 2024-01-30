package com.votemetric.biometricchoice.modules.election;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionResultDTO {
    private Long candidateId;
    private String firstName;
    private String lastName;
    private String party;
    private Long votes;
}
