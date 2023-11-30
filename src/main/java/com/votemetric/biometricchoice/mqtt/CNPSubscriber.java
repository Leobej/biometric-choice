package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.voter.VoterRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class CNPSubscriber {
    private final Logger logger = LoggerFactory.getLogger(CNPSubscriber.class);
    private final VoterRepository voterRepository;
    private final MqttPublisher mqttPublisher;
    private String cnp = "";

    public CNPSubscriber(VoterRepository voterRepository, MqttPublisher mqttPublisher) {
        this.voterRepository = voterRepository;
        this.mqttPublisher = mqttPublisher;
    }
    public void getCnp(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        logger.info("CNP Infos received: {}", payload);

        if ("s-a reconectat".equals(payload)) {
            System.out.println("Reconnect");
            return;
        }

        JSONObject jsonObject = new JSONObject(payload);
        String sentCnp = jsonObject.getString("cnp");

        if (voterRepository.findByCnp(sentCnp).isPresent()) {
            logger.info("CNP exists: {}", sentCnp);
            //this is the topic that the fingerprint device will subscribe to and that payload is the one that will activate
            // the read fingerprint feature on the fingerprint device
            mqttPublisher.publish("voteFingerprintTopic", "nextFingerprint");
        } else {
            logger.info("CNP does not exist: {}", sentCnp);
            // Handle the case where CNP does not exist
        }
    }
}
