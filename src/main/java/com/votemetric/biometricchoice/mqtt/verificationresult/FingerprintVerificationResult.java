package com.votemetric.biometricchoice.mqtt.verificationresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FingerprintVerificationResult {
    private boolean isVerified;
    private long voterId;

}
