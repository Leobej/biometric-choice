package com.votemetric.biometricchoice.utility;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FingerprintAssembler {

    private final Map<Integer, String> partialFingerprints = new HashMap<>();

    public void assembleFingerprint(int fingerprintId, String partialFingerprint) {
        partialFingerprints.putIfAbsent(fingerprintId, "");
        String existingPartialFingerprint = partialFingerprints.get(fingerprintId);
        String concatenatedPartialFingerprint = existingPartialFingerprint + partialFingerprint;
        partialFingerprints.put(fingerprintId, concatenatedPartialFingerprint);

        if (isCompleteFingerprint(concatenatedPartialFingerprint)) {
            String completeFingerprint = concatenateFingerprintParts(partialFingerprints.remove(fingerprintId));
            System.out.println("Complete fingerprint: " + completeFingerprint);
            // TODO: store the complete fingerprint in the database
        }
    }

    private boolean isCompleteFingerprint(String partialFingerprint) {
        // TODO: implement the logic to determine if this is the last part of the fingerprint
        // For example, you could check if the partial fingerprint ends with a specific delimiter.
        return true;
    }

    private String concatenateFingerprintParts(String partialFingerprint) {
        // TODO: implement the logic to concatenate the partial fingerprint parts into a complete fingerprint
        // For example, you could remove the "Fingerprint" key from each part and concatenate the values.
        return partialFingerprint.replaceAll("\"Fingerprint\":\"", "").replaceAll("\"", "");
    }

}
