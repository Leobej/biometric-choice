package com.votemetric.biometricchoice.mqtt;

import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.voter.Voter;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistory;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistoryRepository;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import javax.websocket.MessageHandler;
import java.util.logging.Logger;

@Service
public class SendVoteSubscriber implements MessageHandler {

    private final VoterHistoryRepository voterHistoryRepository;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SendVoteSubscriber.class);
    public SendVoteSubscriber(VoterHistoryRepository voterHistoryRepository) {
        this.voterHistoryRepository = voterHistoryRepository;
    }

    public void sendVote(Message<?> message) throws MessagingException {
        String payload = message.getPayload().toString();
        JSONObject jsonObject = new JSONObject(payload);

        logger.info(jsonObject.toString());
        Voter voter = new Voter();
        voter.setVoterId(jsonObject.getLong("voterId"));
        Candidate candidate = new Candidate();
//        candidate.setCandidateId(jsonObject.get("selectedItem"));

        VoterHistory voterHistory = new VoterHistory();
        voterHistory.setVoter(voter);
//        voterHistory.setCandidate(jsonObject.getLong("candidateId"));

//        voterHistoryRepository.save(vote);

    }
}
