package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.repository.VoterRepository;
import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

@Service
public class CNPSubscriber {

    private final VoterRepository voterRepository;
    private final MqttPublisher mqttPublisher;
    private String cnp = "";

    public CNPSubscriber(VoterRepository voterRepository, MqttPublisher mqttPublisher) {
        this.voterRepository = voterRepository;
        this.mqttPublisher = mqttPublisher;
    }
    public void getCnp(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
//        System.out.println(payload);
        if (payload == "s-a reconectat") {
            System.out.println("Reconnect");
            return;
        }
//        JSONObject jsonObject = new JSONObject(payload);

        String sentCnp = message.getPayload().toString();

        if (voterRepository.findByCnp(sentCnp).isPresent()) {
            System.out.println(sentCnp);
            mqttPublisher.publish("voteFingerprintTopic", "nextFingerprint");
        }
//        System.out.println(sentCnp);
    }
}
