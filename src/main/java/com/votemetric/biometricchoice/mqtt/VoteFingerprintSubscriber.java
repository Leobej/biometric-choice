package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.fingerprint.Fingerprint;
import com.votemetric.biometricchoice.modules.fingerprint.FingerprintRepository;
import com.votemetric.biometricchoice.modules.vote.VoteRepository;
import com.votemetric.biometricchoice.modules.voter.VoterRepository;
import com.votemetric.biometricchoice.utility.FingerprintSimilaritiesVerifier;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteFingerprintSubscriber {

    private final Logger logger = LoggerFactory.getLogger(VoteFingerprintSubscriber.class);
    private final FingerprintRepository fingerprintRepository;
    private final VoterRepository voterRepository;
    private final VoteRepository voteRepository;
    private final MqttPublisher mqttPublisher;
    private String fingerprint = "";
    private String deviceId;
    private int totalChunks = 0;
    private int receivedChunks = 0;

    public VoteFingerprintSubscriber(FingerprintRepository fingerprintRepository,
                                     VoterRepository voterRepository,
                                     VoteRepository voteRepository, MqttPublisher mqttPublisher) {
        this.fingerprintRepository = fingerprintRepository;
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
        this.mqttPublisher = mqttPublisher;
    }

    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        logger.info("Message received: {}", payload);

        if (payload.equals("s-a reconectat")) {
            logger.info("Reconnect again");
            return;
        }

        JSONObject jsonObject = new JSONObject(payload);

        if (jsonObject.has("fingerprint")) {
            handleFingerprintMessage(jsonObject);
        } else if (jsonObject.has("selectedItem")) {
            handleVoteMessage(jsonObject);
        }
    }

    private void handleFingerprintMessage(JSONObject jsonObject) {
        String fingerprintChunk = jsonObject.getString("fingerprint");
        Long voterId = jsonObject.getLong("voterId");
        this.deviceId = jsonObject.getString("deviceId");
        totalChunks = 4;
        fingerprint += fingerprintChunk;
        receivedChunks++;

        if (receivedChunks == totalChunks) {
            boolean isVerified = verifyFingerprint(voterId, fingerprint);
            String verificationResult = isVerified ? "verified" : "not verified";
            mqttPublisher.publish("voteFingerprintTopic", verificationResult);
            resetFingerprintData();
        }
    }

    private void handleVoteMessage(JSONObject jsonObject) {
        Long voterId = jsonObject.getLong("voterId");
        String selectedItem = jsonObject.getString("selectedItem");

        //llogic to save the vote in the database
//        voteRepository.(new Vote(0,new Voter(), selectedItem));

        logger.info("Vote saved for Voter ID {}: {}", voterId, selectedItem);
    }

    private boolean verifyFingerprint(Long voterId, String receivedFingerprint) {
        Optional<Fingerprint> registeredFingerprintEntity = fingerprintRepository.findByVoter_VoterId(voterId);
        if (!registeredFingerprintEntity.isPresent()) {
            throw new RuntimeException("Registered fingerprint not found for Voter ID: " + voterId);
        }
        logger.info("Received Fingerprint: " + receivedFingerprint);
        String registeredFingerprint = registeredFingerprintEntity.get().getFingerprint();
//        double similarity = FingerprintSimilaritiesVerifier.jaroWinkler(registeredFingerprint, receivedFingerprint);
        double similarity = StringUtils.getJaroWinklerDistance(registeredFingerprint, receivedFingerprint);;
        logger.info("Similarity:" + similarity);
        return similarity >= 0.90;

//        String registeredBinaryFingerprint = FingerprintSimilaritiesVerifier.hexToBinary(registeredFingerprint);
//        String receivedBinaryFingerprint = FingerprintSimilaritiesVerifier.hexToBinary(receivedFingerprint);
//        double similarity = FingerprintSimilaritiesVerifier.binarySimilarity(registeredBinaryFingerprint, receivedBinaryFingerprint);
//        logger.info("Binary similarity: " + similarity);
//        return similarity >= 0.90;

    }

    private void resetFingerprintData() {
        fingerprint = "";
        deviceId = null;
        totalChunks = 0;
        receivedChunks = 0;
    }
}
