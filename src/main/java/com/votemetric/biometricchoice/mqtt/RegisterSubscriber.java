package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.device.DeviceRepository;
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
public class RegisterSubscriber implements MessageHandler {
    private final Logger logger = LoggerFactory.getLogger(RegisterSubscriber.class);
    private final FingerprintRepository fingerprintRepository;
    private final DeviceRepository deviceRepository;

    public RegisterSubscriber(FingerprintRepository fingerprintRepository, DeviceRepository deviceRepository) {
        this.fingerprintRepository = fingerprintRepository;
        this.deviceRepository = deviceRepository;
    }

    public void getFingerprint(Message<?> message) throws MessagingException {
        // here to modify the logic for registration of a voter into system
        // it is not necessary to use the fingerprint for registration
        // fingerprint is no more used, instead it will be used the id of the fingerprint from the sensor, also there will be the match done
        // between the fingerprint from the sensor and the fingerprint from the database
        // if the match is successful then the voter will be verified and will be able to vote
        // if the match is not successful then the voter will not be able to vote
        String payload = message.getPayload().toString();
        logger.debug(payload);

        if (Objects.equals(payload, "s-a reconectat")) {
            logger.debug("Reconnect");
            return;
        }

        JSONObject jsonObject = new JSONObject(payload);
        Integer fingerprint = jsonObject.getInt("fingerprint");
        String deviceName = jsonObject.getString("deviceId");

        Fingerprint fingerprintEntity = new Fingerprint();
        fingerprintEntity.setFingerprint(fingerprint.toString());
        fingerprintEntity.setDeviceId(deviceRepository.findByName(deviceName).getId());

        logger.info("DeviceId: " + fingerprintEntity.getDeviceId() + " : " + fingerprintEntity.getFingerprint());
        fingerprintRepository.save(fingerprintEntity);
        logger.info("Fingerprint saved: " + fingerprint);

    }
}
