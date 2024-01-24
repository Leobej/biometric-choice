package com.votemetric.biometricchoice.modules.voter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoterDTO {
    private long voterId;
    private String firstname;
    private String lastname;
    private String cnp;
    private Long fingerprintId;
    private LocalDate createdAt;
    private LocalDate birthdate;
}
