package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.exception.CandidateNotFoundException;
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
        return elections.map(this::convertElectionToElectionDTO);
    }

    private ElectionDTO convertElectionToElectionDTO(Election election) {
        ElectionDTO dto = new ElectionDTO();
        dto.setElectionId(election.getElectionId());
//        if (election.getVoter() != null) {
//            dto.setVoterId(election.getVoter().getVoterId());
//        }
//        if (election.getCandidate() != null) {
//            dto.setCandidateId(election.getCandidate().getCandidateId());
//        }
        dto.setDescription(election.getDescription());
        dto.setLocation(election.getLocation());
        dto.setCreatedAt(election.getCreatedAt());
        dto.setStartDate(election.getStartDate());
        dto.setEndDate(election.getEndDate());
        dto.setActive(election.getActive());
        return dto;
    }


    public Page<ElectionDTO> getAllElections(Pageable pageable) {
        Page<Election> elections = electionRepository.findAll(pageable);
        return elections.map(election -> mapper.convertToType(election, ElectionDTO.class));
    }

    @Override
    public ElectionDTO createElection(ElectionDTO electionDto) {
        Election election = mapper.convertToType(electionDto, Election.class);
        election.setCreatedAt(LocalDateTime.now());
        List<Candidate> candidateEntities = new ArrayList<>();
        if (electionDto.getCandidates() != null) {
            candidateEntities = electionDto.getCandidates().stream()
                    .map(candidateDto -> candidateRepository.findById(candidateDto.getCandidateId())
                            .orElseThrow(() -> new CandidateNotFoundException(candidateDto.getCandidateId())))
                    .collect(Collectors.toList());
        }
        election.setCandidates(candidateEntities);

        Election savedElection = electionRepository.save(election);
        return convertElectionToElectionDTO(savedElection);
    }

    @Override
    public ElectionDTO updateElection(ElectionDTO electionDto) {
        checkIfElectionExists(electionDto.getElectionId());
        Election existingElection = electionRepository.findById(electionDto.getElectionId())
                .orElseThrow(() -> new ElectionNotFoundException(electionDto.getElectionId()));

        // Manually set fields to update
        existingElection.setDescription(electionDto.getDescription());
        existingElection.setLocation(electionDto.getLocation());
        existingElection.setStartDate(electionDto.getStartDate());
        existingElection.setEndDate(electionDto.getEndDate());
        existingElection.setActive(electionDto.getActive());
        // Set other fields that you need to update

        List<Candidate> candidateEntities = electionDto.getCandidates().stream()
                .map(candidateDto -> candidateRepository.findById(candidateDto.getCandidateId())
                        .orElseThrow(() -> new CandidateNotFoundException(candidateDto.getCandidateId())))
                .collect(Collectors.toList());

        existingElection.setCandidates(candidateEntities);

        Election savedElection = electionRepository.save(existingElection);
        return convertElectionToElectionDTO(savedElection);
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
        List<Voter> voters = voterHistoryRepository.findVotersByElectionId(electionId);
        return voters.stream()
                .map(voter -> mapper.convertToType(voter, VoterDTO.class))
                .collect(Collectors.toList());
    }

    private List<CandidateDTO> fetchCandidatesForElection(Long electionId) {
        List<Candidate> candidates = voterHistoryRepository.findCandidatesByElectionId(electionId);
        return candidates.stream()
                .map(candidate -> mapper.convertToType(candidate, CandidateDTO.class))
                .collect(Collectors.toList());
    }


    private List<ElectionResultDTO> fetchElectionResultsForElection(Long electionId) {
        List<Object[]> voteCounts = voterHistoryRepository.countVotesByCandidate(electionId);
        return voteCounts.stream()
                .map(result -> {
                    Candidate candidate = candidateRepository.findById((Long) result[0])
                            .orElseThrow(() -> new ElectionResultNotFoundException((Long) result[0]));
                    return new ElectionResultDTO(
                            candidate.getCandidateId(),
                            candidate.getFirstname(),
                            candidate.getLastname(),
                            candidate.getParty(),
                            ((Long) result[1]) // Assuming vote count fits into an integer
                    );
                })
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
            Long candidateIdResult = (Long) result[1]; // Extract the candidateId from the result
            Long totalVotes = (Long) result[2]; // Adjust the index for totalVotes
            trends.add(new DailyVotingTrendDTO(date, totalVotes, candidateIdResult)); // Pass the candidateId to the DTO
        }
        return trends;
    }


    @Override
    public List<CandidateVoteCountDTO> getAggregatedVotesByElectionId(Long electionId) {
        List<Object[]> voteCounts = voterHistoryRepository.countVotesByCandidate(electionId);
        return voteCounts.stream()
                .map(result -> new CandidateVoteCountDTO((Long) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

}
