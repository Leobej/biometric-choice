package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.exception.ElectionNotFoundException;
import com.votemetric.biometricchoice.exception.ElectionResultNotFoundException;
import com.votemetric.biometricchoice.interfaces.IElectionService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.modules.candidate.Candidate;
import com.votemetric.biometricchoice.modules.candidate.CandidateDTO;
import com.votemetric.biometricchoice.modules.candidate.CandidateRepository;
import com.votemetric.biometricchoice.modules.electionresult.ElectionResult;
import com.votemetric.biometricchoice.modules.electionresult.ElectionResultDTO;
import com.votemetric.biometricchoice.modules.electionresult.ElectionResultRepository;
import com.votemetric.biometricchoice.modules.voter.Voter;
import com.votemetric.biometricchoice.modules.voter.VoterDTO;
import com.votemetric.biometricchoice.modules.voter.VoterRepository;
import com.votemetric.biometricchoice.modules.voterhistory.VoterHistoryRepository;
import com.votemetric.biometricchoice.modules.votingtrend.DailyVotingTrendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService implements IElectionService {

    private final ElectionRepository electionRepository;
    private final VoterRepository voterRepository;
    private final VoterHistoryRepository voterHistoryRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionResultRepository electionResultRepository;
    private final Mapper mapper;

    public ElectionService(ElectionRepository electionRepository,
                           VoterRepository voterRepository,
                           VoterHistoryRepository voterHistoryRepository, CandidateRepository candidateRepository,
                           ElectionResultRepository electionResultRepository,
                           Mapper mapper) {
        this.electionRepository = electionRepository;
        this.voterRepository = voterRepository;
        this.voterHistoryRepository = voterHistoryRepository;
        this.candidateRepository = candidateRepository;
        this.electionResultRepository = electionResultRepository;
        this.mapper = mapper;
    }

    @Override
    public ElectionDTO getElectionById(Long electionId) {
        Election election = findElectionById(electionId);
        return mapper.convertToType(election, ElectionDTO.class);
    }

    public ElectionDetailDTO getElectionDetailsById(Long electionId) {
        Election election = findElectionById(electionId);

        ElectionDetailDTO electionDetailDTO = new ElectionDetailDTO();

        // Setting basic fields from the Election entity to ElectionDetailDTO
        electionDetailDTO.setElectionId(election.getElectionId());
        electionDetailDTO.setDescription(election.getDescription());
        electionDetailDTO.setLocation(election.getLocation());
        electionDetailDTO.setCreatedAt(election.getCreatedAt());
        electionDetailDTO.setStartDate(election.getStartDate());
        electionDetailDTO.setEndDate(election.getEndDate());
        electionDetailDTO.setActive(election.getActive());

        // Populate the lists from fetched data
        // Note: The lists are populated with separate method calls
        //       that retrieve and map the respective data.
        List<ElectionResultDTO> electionResults = fetchElectionResultsForElection(electionId);
        electionDetailDTO.setElectionResults(electionResults);

        List<VoterDTO> voters = fetchVotersForElection(electionId);
        electionDetailDTO.setVoters(voters);

        List<CandidateDTO> candidates = fetchCandidatesForElection(electionId);
        electionDetailDTO.setCandidates(candidates);

        return electionDetailDTO;
    }

    @Override
    public List<ElectionDTO> getAllElections() {
        List<Election> elections = electionRepository.findAll();
        return elections.stream()
                .map(election -> mapper.convertToType(election, ElectionDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ElectionDTO> getElectionsByDescription(String description, Pageable pageable) {
        Page<Election> elections = electionRepository.findByDescriptionContaining(description, pageable);
        return elections.map(election -> mapper.convertToType(election, ElectionDTO.class));
    }


    public Page<ElectionDTO> getAllElections(Pageable pageable) {
        Page<Election> elections = electionRepository.findAll(pageable);
        return elections.map(election -> mapper.convertToType(election, ElectionDTO.class));
    }

    @Override
    public ElectionDTO createElection(ElectionDTO electionDto) {
        Election election = mapper.convertToType(electionDto, Election.class);
        election.setCreatedAt(LocalDateTime.now());
        election = electionRepository.save(election);
        return mapper.convertToType(election, ElectionDTO.class);
    }

    @Override
    public ElectionDTO updateElection(ElectionDTO electionDto) {
        checkIfElectionExists(electionDto.getElectionId());
        electionRepository.save(mapper.convertToType(electionDto, Election.class));
        return electionDto;
    }

    @Override
    public void deleteElectionById(Long electionId) {
        checkIfElectionExists(electionId);
        electionRepository.deleteById(electionId);
    }

    Election findElectionById(Long electionId) {
        return electionRepository.findById(electionId).orElseThrow(
                () -> new ElectionNotFoundException(electionId));
    }

    void checkIfElectionExists(Long id) {
        boolean exists = electionRepository.existsById(id);
        if (!exists) {
            throw new ElectionResultNotFoundException(id);
        }
    }


    private List<VoterDTO> fetchVotersForElection(Long electionId) {
        List<Voter> voters = voterRepository.findByElections_ElectionId(electionId);
        return voters.stream()
                .map(voter -> mapper.convertToType(voter, VoterDTO.class))
                .collect(Collectors.toList());
    }

    private List<CandidateDTO> fetchCandidatesForElection(Long electionId) {
        List<Candidate> candidates = candidateRepository.findByElections_ElectionId(electionId);
        return candidates.stream()
                .map(candidate -> mapper.convertToType(candidate, CandidateDTO.class))
                .collect(Collectors.toList());
    }

    private List<ElectionResultDTO> fetchElectionResultsForElection(Long electionId) {
        List<ElectionResult> results = electionResultRepository.findByElection_ElectionId(electionId);
        return results.stream()
                .map(result -> mapper.convertToType(result, ElectionResultDTO.class))
                .collect(Collectors.toList());
    }

    public List<DailyVotingTrendDTO> getVotingTrends(Long electionId) {
        List<Object[]> results = voterHistoryRepository.findVotingTrendsByElectionId(electionId);
        List<DailyVotingTrendDTO> trends = new ArrayList<>();
        for (Object[] result : results) {
            LocalDateTime date = ((Timestamp) result[0]).toLocalDateTime(); // Assuming the first element is a Timestamp
            Long totalVotes = (Long) result[1]; // Assuming the second element is a Long
            trends.add(new DailyVotingTrendDTO(date, totalVotes));
        }
        return trends;
    }

    public List<DailyVotingTrendDTO> getVotingTrendsForCandidate(Long electionId, Long candidateId) {
        List<Object[]> results = voterHistoryRepository.findVotingTrendsByElectionIdAndCandidateId(electionId, candidateId);
        List<DailyVotingTrendDTO> trends = new ArrayList<>();
        for (Object[] result : results) {
            LocalDateTime date = ((Timestamp) result[0]).toLocalDateTime();
            Long totalVotes = (Long) result[1];
            trends.add(new DailyVotingTrendDTO(date, totalVotes));
        }
        return trends;
    }


}
