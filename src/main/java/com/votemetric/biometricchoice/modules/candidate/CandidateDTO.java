package com.votemetric.biometricchoice.modules.candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private byte[] image;

    public CandidateDTO(String firstname, String lastname, String party, String position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.party = party;
        this.position = position;
    }
}
