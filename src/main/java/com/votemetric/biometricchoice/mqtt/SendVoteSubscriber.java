package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.exception.EntityNotFoundException;
import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.candidate.CandidateRepository;
import com.votemetric.biometricchoice.modules.election.Election;
import com.votemetric.biometricchoice.modules.election.ElectionRepository;
import com.votemetric.biometricchoice.modules.location.Location;
import com.votemetric.biometricchoice.modules.location.LocationRepository;
import com.votemetric.biometricchoice.modules.voter.Voter;
import com.votemetric.biometricchoice.modules.voter.VoterRepository;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistoryRepository;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class SendVoteSubscriber implements MessageHandler {

    private final VoterRepository voterRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;
    private final LocationRepository locationRepository;
    private final VoterHistoryRepository voterHistoryRepository;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SendVoteSubscriber.class);

    public SendVoteSubscriber(VoterRepository voterRepository, CandidateRepository candidateRepository, ElectionRepository electionRepository, LocationRepository locationRepository, VoterHistoryRepository voterHistoryRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
        this.electionRepository = electionRepository;
        this.locationRepository = locationRepository;
        this.voterHistoryRepository = voterHistoryRepository;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        JSONObject jsonObject = new JSONObject(payload);

        logger.info(jsonObject.toString());

        Voter voter = voterRepository.findById(jsonObject.getLong("voterId")).orElseThrow(() -> new EntityNotFoundException("Voter not found"));
        Candidate candidate = candidateRepository.findById(jsonObject.getLong("candidateId")).orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        Election election = electionRepository.findById(jsonObject.getLong("electionId")).orElseThrow(() -> new EntityNotFoundException("Election not found"));
        Location location = locationRepository.findById(jsonObject.getLong("locationId")).orElseThrow(() -> new EntityNotFoundException("Location not found"));

        VoterHistory voterHistory = new VoterHistory();
        voterHistory.setVoter(voter);
        voterHistory.setCandidate(candidate);
        voterHistory.setElection(election);
        voterHistory.setDateVoted(LocalDateTime.now());
        voterHistory.setLocation(location);
        voterHistory.setVoteStatus(true);

        voterHistoryRepository.save(voterHistory);

    }
}
