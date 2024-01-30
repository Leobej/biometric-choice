package com.votemetric.biometricchoice.modules.election;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgeDistributionDTO {
    private String ageInterval;
    private Long count;
}
