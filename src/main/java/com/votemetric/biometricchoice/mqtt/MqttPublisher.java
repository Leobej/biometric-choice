package com.votemetric.biometricchoice.mqtt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisher {

    private final MqttPahoMessageHandler mqttHandler;

    @Autowired
    public MqttPublisher(MqttPahoMessageHandler mqttHandler) {
        this.mqttHandler = mqttHandler;
    }

    public void publish(String topic, String payload) {
        Message<String> mqttMessage = MessageBuilder
                .withPayload(payload)
                .setHeader(MessageHeaders.CONTENT_TYPE, "text/plain")
                .setHeader(MqttHeaders.TOPIC, topic)
                .build();
        mqttHandler.handleMessage(mqttMessage);
    }
}
