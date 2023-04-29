package com.votemetric.biometricchoice.config;

import com.votemetric.biometricchoice.utility.Utility;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansConfiguration {


    @Value("${spring.mqtt.url}")
    private String mqttBrokerUrl;

    @Value("${spring.mqtt.username}")
    private String mqttUsername;

    @Value("${spring.mqtt.password}")
    private String mqttPassword;

    @Value("${spring.mqtt.client-id}")
    private String mqttClientId;
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Utility utility() {
        return new Utility();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MqttPahoMessageHandler mqttPahoMessageHandler() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler("clientId", mqttClientFactory());
        handler.setAsync(true);
        handler.setDefaultTopic("inTopic");
        return handler;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(mqttConnectOptions());

        return factory;
    }

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttUsername);
        options.setPassword(mqttPassword.toCharArray());
        options.setServerURIs(new String[]{mqttBrokerUrl});
        return options;
    }

    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttClientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(1);
        messageHandler.setAsyncEvents(false);
        messageHandler.setDefaultRetained(false);
        messageHandler.setDefaultTopic("inTopic");
        return messageHandler;
    }
}
