package com.votemetric.biometricchoice.modules.voter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoterDTO {
    private long voterId;
    private String firstname;
    private String lastname;
    private String password;
    private String cnp;
    private Long fingerprintId;
    private String createdAt;
}
