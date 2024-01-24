package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.candidate.CandidateRepository;
import com.votemetric.biometricchoice.modules.fingerprint.FingerprintRepository;
import com.votemetric.biometricchoice.modules.vote.VoteRepository;
import com.votemetric.biometricchoice.modules.voter.Voter;
import com.votemetric.biometricchoice.modules.voter.VoterRepository;
import com.votemetric.biometricchoice.mqtt.verificationresult.FingerprintVerificationResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VoteFingerprintSubscriber {

    private final Logger logger = LoggerFactory.getLogger(VoteFingerprintSubscriber.class);
    private final FingerprintRepository fingerprintRepository;
    private final VoterRepository voterRepository;
    private final VoteRepository voteRepository;
    private final CandidateRepository candidateRepository;
    private final MqttPublisher mqttPublisher;
    private String deviceId;

    public VoteFingerprintSubscriber(FingerprintRepository fingerprintRepository,
                                     VoterRepository voterRepository,
                                     VoteRepository voteRepository, CandidateRepository candidateRepository, MqttPublisher mqttPublisher) {
        this.fingerprintRepository = fingerprintRepository;
        this.voterRepository = voterRepository;
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
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
        }
    }

    private void handleFingerprintMessage(JSONObject jsonObject) {
        Long fingerprint = jsonObject.getLong("fingerprint");
        this.deviceId = jsonObject.getString("deviceId");
        FingerprintVerificationResult verificationResult = verifyFingerprint(fingerprint);
        String verificationStatus = verificationResult.isVerified() ? "verified" : "not verified";

        JSONObject messageJson = new JSONObject();
        messageJson.put("status", verificationStatus);

        if (verificationResult.isVerified()) {
            messageJson.put("voterId", verificationResult.getVoterId());
            List<Candidate> candidates = getCandidates(deviceId);
            JSONArray candidatesJsonArray = new JSONArray();
            for (Candidate candidate : candidates) {
                JSONObject candidateJson = new JSONObject();
                candidateJson.put("id", candidate.getCandidateId());
                candidateJson.put("name", candidate.getLastname() + candidate.getFirstname());
                candidatesJsonArray.put(candidateJson);
            }
            messageJson.put("candidates", candidatesJsonArray);
        }

        String message = messageJson.toString();
        mqttPublisher.publish("voteFingerprintTopic", message);
        logger.info("Message published: {}", message);
        resetFingerprintData();
    }

    private List<Candidate> getCandidates(String deviceName) {
        try {
            return candidateRepository.findCandidatesByDeviceName(deviceName);
        } catch (NumberFormatException e) {
            logger.error("Invalid device ID format: {}", deviceName, e);
            return Collections.emptyList();
        }
    }

    private FingerprintVerificationResult verifyFingerprint(Long receivedFingerprint) {
        //here we verify if the fingerprint from the sensor is the same as the fingerprint from the database
        logger.info("Received Fingerprint: " + receivedFingerprint);
        Optional<Voter> registeredFingerprintEntity = voterRepository.findByFingerprintId(receivedFingerprint);
        if (!registeredFingerprintEntity.isPresent())
            throw new RuntimeException("Registered fingerprint not found for Voter ID: " + registeredFingerprintEntity.get().getVoterId());

        logger.info("The voter with id {} has the fingerprint {}", registeredFingerprintEntity.get().getVoterId(), registeredFingerprintEntity.get().getFingerprintId());
        long voterId = registeredFingerprintEntity.get().getVoterId();
        logger.info("The voter with id {} has the fingerprint {}", voterId, registeredFingerprintEntity.get().getFingerprintId());
        boolean isVerified = registeredFingerprintEntity.get().getFingerprintId().equals(receivedFingerprint);

        return new FingerprintVerificationResult(isVerified, voterId);
    }


    private void resetFingerprintData() {
        deviceId = null;
    }
}
