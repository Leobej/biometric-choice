package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.fingerprint.Fingerprint;
import com.votemetric.biometricchoice.modules.fingerprint.FingerprintRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.websocket.MessageHandler;
import java.util.Objects;

@Service
public class MqttSubscriber implements MessageHandler {
    private final Logger logger = LoggerFactory.getLogger(MqttSubscriber.class);
    private final FingerprintRepository fingerprintRepository;
    private String fingerprint = "";
    private int totalChunks = 0;
    private int receivedChunks = 0;

    public MqttSubscriber(FingerprintRepository fingerprintRepository) {
        this.fingerprintRepository = fingerprintRepository;
    }

    public void getFingerprint(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        logger.debug(payload);
        if (Objects.equals(payload, "s-a reconectat")) {
            logger.debug("Reconnect");
            return;
        }
        JSONObject jsonObject = new JSONObject(payload);
        String fingerprintChunk = jsonObject.getString("fingerprint");
        totalChunks = 5;
        fingerprint = fingerprint + fingerprintChunk;
        receivedChunks++;

        if (receivedChunks == totalChunks) {
            Fingerprint fingerprintEntity = new Fingerprint();
            fingerprintEntity.setFingerprint(fingerprint);
            fingerprintEntity.setDeviceId(jsonObject.getString("deviceId"));
            logger.debug("DeviceId: " + fingerprintEntity.getDeviceId() + " : " + fingerprintEntity.getFingerprint());
            fingerprintRepository.save(fingerprintEntity);
            logger.debug("Fingerprint saved: " + fingerprint);
            fingerprint = "";
            totalChunks = 0;
            receivedChunks = 0;
        }
    }
}
