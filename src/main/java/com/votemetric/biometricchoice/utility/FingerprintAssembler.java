package com.votemetric.biometricchoice.utility;

import com.votemetric.biometricchoice.interfaces.IFingerprintService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FingerprintAssembler {

    private final Map<Integer, String> partialFingerprints = new HashMap<>();

    private final IFingerprintService fingerprintService;

    public FingerprintAssembler(IFingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }

    public void assembleFingerprint(int fingerprintId, String partialFingerprint) {
        partialFingerprints.putIfAbsent(fingerprintId, "");
        String existingPartialFingerprint = partialFingerprints.get(fingerprintId);
        String concatenatedPartialFingerprint = existingPartialFingerprint + partialFingerprint;
        partialFingerprints.put(fingerprintId, concatenatedPartialFingerprint);

        if (isCompleteFingerprint(concatenatedPartialFingerprint)) {
            String completeFingerprint = concatenateFingerprintParts(partialFingerprints.remove(fingerprintId));
//            FingerprintDTO fingerprintDTO= new FingerprintDTO();
//            fingerprintDTO.setFingerprint(completeFingerprint);
//            fingerprintService.createFingerprint(completeFingerprint);
            System.out.println("Complete fingerprint: " + completeFingerprint);
            // TODO: store the complete fingerprint in the database
        }
    }

    private boolean isCompleteFingerprint(String partialFingerprint) {
        return true;
    }

    private String concatenateFingerprintParts(String partialFingerprint) {
        return partialFingerprint.replaceAll("\"Fingerprint\":\"", "").replaceAll("\"", "");
    }

}
