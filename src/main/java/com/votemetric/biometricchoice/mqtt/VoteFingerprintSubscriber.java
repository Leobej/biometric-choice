package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.entity.Fingerprint;
import com.votemetric.biometricchoice.repository.FingerprintRepository;
import com.votemetric.biometricchoice.repository.VoterRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class VoteFingerprintSubscriber {

    private final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);
    private final FingerprintRepository fingerprintRepository;
    private final VoterRepository voterRepository;
    private String fingerprint = "";
    private int totalChunks = 0;
    private int receivedChunks = 0;

    public VoteFingerprintSubscriber(FingerprintRepository fingerprintRepository, VoterRepository voterRepository) {
        this.fingerprintRepository = fingerprintRepository;
        this.voterRepository = voterRepository;
    }

    public void getFingerprint(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        System.out.println(payload);
        if (payload == "s-a reconectat") {
            System.out.println("Reconnect again");
            return;
        }
        JSONObject jsonObject = new JSONObject(payload);

        String fingerprintChunk = jsonObject.getString("fingerprint");
        totalChunks = 4;
        fingerprint = fingerprint + fingerprintChunk;
        receivedChunks++;

        if (receivedChunks == totalChunks) {
            Fingerprint fingerprintEntity = new Fingerprint();
            fingerprintEntity.setFingerprint(fingerprint);
            fingerprintEntity.setDeviceId(jsonObject.getString("deviceId"));
            System.out.println(fingerprintEntity);
//            fingerprintRepository.save(fingerprintEntity);
            logger.info("Fingerprint for Vote saved: {}", fingerprint);
            fingerprint = "";
            totalChunks = 0;
            receivedChunks = 0;
        }
    }
}
