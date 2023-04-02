package com.votemetric.biometricchoice;

import com.votemetric.biometricchoice.utility.FingerprintSimilaritiesVerifier;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.websocket.MessageHandler;

@SpringBootApplication
@IntegrationComponentScan
public class BiometricChoiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiometricChoiceApplication.class, args);
FingerprintSimilaritiesVerifier fingerprintSimilaritiesVerifier= new FingerprintSimilaritiesVerifier();
double percentage= fingerprintSimilaritiesVerifier.jaro_Winkler("\"03035B236C013D017D0000000000000000000000000000000000000000000000000000000000000000000000000000001000030093000000000003333F3FCCCFFFEFFBBBEEAFAEBA9A9AEEAA6AA699A655599551454511101144400101010101010101010101010101010101010101010101010101010101010101010101010113E2EF01FFFFFFFF0200828211A4FE8B99A55EA524E45E9CA50E9E6526533E790DCF7F5D0ED33F668F507F6D1525FF6C9B917F829BCF7F491F599F5621699F47AA805F472FD8DF5230ABDF8E324F9F42B4D4FF73B5133F5B39EB1F4310837D5394173A4A8F9CDB5815A77B81A1909852B52BF8528BDBB9518E68F97E2125D9\"","03036609000120019D0000000000000000000000000000000000000000000000000000000000000000000000000000000B000600690000000CF333CCFCFFFBBBBFBAEEE9AAA6A66665595551544511040000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000F9FEF01FFFFFFFF0200822A9BC5BE33B844BE26BA5D9E3E93855F5596823F5C1D00FF1725C71F1C31869F2631857F0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        System.out.println(percentage);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/").allowedOrigins("http://localhost:8080");
            }
        };
    }



//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    @Bean
//    public MessageProducer inbound() {
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient",
//                                                 "test/topic" );
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler handler() {
//        return new MessageHandler() {
//
//
//            public void handleMessage(Message<?> message) throws MessagingException {
//                System.out.println(message.getPayload());
//            }
//
//        };
//    }

}
