package com.votemetric.biometricchoice.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import javax.websocket.MessageHandler;

@Configuration
@EnableIntegration
public class MqttSubscriberConfig {

    @Autowired
    private RegisterSubscriber registerSubscriber;

    @Autowired
    private CNPSubscriber cnpSubscriber;

    @Autowired
    private VoteFingerprintSubscriber voteFingerprintSubscriber;

    @Autowired
    private SendVoteSubscriber sendVoteSubscriber;


    @Bean
    public MessageChannel fingerprintChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel cnpInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel voteFingerprintChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel sendVoteChannel() {
        return new DirectChannel();
    }



    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient", "test/topic");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(fingerprintChannel());
        return adapter;
    }


    @Bean
    public MqttPahoMessageDrivenChannelAdapter cnpInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient1", "cnp");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(cnpInputChannel());
        return adapter;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter voterFingerprintInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient2", "voteFingerprint");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(voteFingerprintChannel());
        return adapter;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter sendVote() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient2", "send/vote");
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(sendVoteChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "fingerprintChannel")
    public MessageHandler handler() {
        return registerSubscriber;
    }

    // New CNP topic handler
    @Bean
    @ServiceActivator(inputChannel = "cnpInputChannel")
    public CNPSubscriber cnpHandler() {
        return cnpSubscriber; // replace getCNP with the method of CNPSubscriber
    }

    @Bean
    @ServiceActivator(inputChannel = "voteFingerprintChannel")
    public VoteFingerprintSubscriber voterFingerprintHandler() {
        return voteFingerprintSubscriber; // replace getCNP with the method of CNPSubscriber
    }

    @Bean
    @ServiceActivator(inputChannel = "sendVoteChannel")
    public SendVoteSubscriber sendVoteHandler() {
        return sendVoteSubscriber; // replace getCNP with the method of CNPSubscriber
    }


}
