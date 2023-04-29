package com.votemetric.biometricchoice.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


//@Configuration
public class MqttConfig {
//
//    @Value("${spring.mqtt.url}")
//    private String mqttBrokerUrl;
//
//    @Value("${spring.mqtt.username}")
//    private String mqttUsername;
//
//    @Value("${spring.mqtt.password}")
//    private String mqttPassword;
//
//    @Value("${spring.mqtt.client-id}")
//    private String mqttClientId;
//
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        factory.setConnectionOptions(mqttConnectOptions());
//        return factory;
//    }
//
//    @Bean
//    public MqttConnectOptions mqttConnectOptions() {
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setUserName(mqttUsername);
//        options.setPassword(mqttPassword.toCharArray());
//        return options;
//    }
//
//    @Bean
//    public MessageChannel mqttOutputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageHandler mqttOutbound() {
//        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttClientId, mqttClientFactory());
//        messageHandler.setAsync(true);
//        messageHandler.setDefaultQos(1);
//        messageHandler.setAsyncEvents(false);
//        messageHandler.setDefaultRetained(false);
//        messageHandler.setDefaultTopic("test/topic");
//        return messageHandler;
//    }

}
