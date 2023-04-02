package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.entity.Fingerprint;
import com.votemetric.biometricchoice.repository.FingerprintRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.websocket.MessageHandler;

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


    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        System.out.println(payload);
        if (payload == "s-a reconectat") {
            System.out.println("Reconnect");
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
            fingerprintRepository.save(fingerprintEntity);
            logger.info("Fingerprint saved: {}", fingerprint);
            fingerprint = "";
            totalChunks = 0;
            receivedChunks = 0;
        }
    }
}
