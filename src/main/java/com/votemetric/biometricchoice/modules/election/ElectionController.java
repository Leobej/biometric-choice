package com.votemetric.biometricchoice.modules.election;

import com.votemetric.biometricchoice.interfaces.IElectionService;
import com.votemetric.biometricchoice.modules.votingtrend.DailyVotingTrendDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/elections")
public class ElectionController {
    private final IElectionService electionService;

    public ElectionController(IElectionService electionService) {
        this.electionService = electionService;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ElectionDetailDTO> getElectionDetailsById(@PathVariable("id") Long id) {
        ElectionDetailDTO electionDetailDTO = electionService.getElectionDetailsById(id);
        return ResponseEntity.ok(electionDetailDTO);
    }

    @GetMapping("/{id}/voting-trends/{candidateId}")
    public ResponseEntity<List<DailyVotingTrendDTO>> getCandidateVotingTrends(@PathVariable("id") Long electionId, @PathVariable("candidateId") Long candidateId) {
        List<DailyVotingTrendDTO> trends = electionService.getVotingTrendsForCandidate(electionId, candidateId);
        return ResponseEntity.ok(trends);
    }

    @GetMapping("/{id}/voting-trends")
    public ResponseEntity<List<DailyVotingTrendDTO>> getAllVotingTrends(@PathVariable("id") Long electionId) {
        List<DailyVotingTrendDTO> trends = electionService.getVotingTrends(electionId);
        return ResponseEntity.ok(trends);
    }

    @GetMapping("/{id}/aggregated-votes")
    public ResponseEntity<List<CandidateVoteCountDTO>> getAggregatedVotes(@PathVariable("id") Long electionId) {
        List<CandidateVoteCountDTO> aggregatedVotes = electionService.getAggregatedVotesByElectionId(electionId);
        return ResponseEntity.ok(aggregatedVotes);
    }

    @GetMapping("")
    public ResponseEntity<Page<ElectionDTO>> getAllElections(
            @RequestParam(required = false) String description,
            Pageable pageable) {
        Page<ElectionDTO> page;
        if (description != null && description.trim().length() > 0) {
            page = electionService.getElectionsByDescription(description, pageable);
        } else {
            page = electionService.getAllElections(pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ElectionDTO> getElectionById(@PathVariable("id") Long id) {
        ElectionDTO electionDTO = electionService.getElectionById(id);
        return ResponseEntity.ok(electionDTO);
    }


    @PostMapping
    public ResponseEntity<ElectionDTO> createElection(@RequestBody ElectionDTO electionDto) {
        System.out.println("Received electionDto: " + electionDto); // Add this log
        ElectionDTO savedElectionDto = electionService.createElection(electionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedElectionDto);
    }

    @PutMapping
    public ResponseEntity<ElectionDTO> updateElection(@RequestBody ElectionDTO electionDto) {
        ElectionDTO updatedElectionDto = electionService.updateElection(electionDto);
        return ResponseEntity.ok(updatedElectionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable("id") Long id) {
        electionService.deleteElectionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history")
    public ResponseEntity<Page<ElectionDTO>> getPastElections(Pageable pageable) {
        Page<ElectionDTO> page = electionService.getElectionsByDateRange(pageable, null, LocalDateTime.now(), false);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Page<ElectionDTO>> getUpcomingElections(Pageable pageable) {
        Page<ElectionDTO> page = electionService.getElectionsByDateRange(pageable, LocalDateTime.now(), null, true);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}/age-distribution")
    public ResponseEntity<List<AgeDistributionDTO>> getAgeDistribution(@PathVariable("id") Long electionId) {
        List<AgeDistributionDTO> ageDistribution = electionService.getAgeDistributionByElectionId(electionId);
        return ResponseEntity.ok(ageDistribution);
    }
}

