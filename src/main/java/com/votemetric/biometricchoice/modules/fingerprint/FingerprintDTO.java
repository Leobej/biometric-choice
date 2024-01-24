package com.votemetric.biometricchoice.modules.fingerprint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FingerprintDTO {
    private Long id;
    private String fingerprint;
    private Long deviceId;
}
