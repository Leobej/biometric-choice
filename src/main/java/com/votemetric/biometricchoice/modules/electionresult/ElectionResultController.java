package com.votemetric.biometricchoice.modules.electionresult;

import com.votemetric.biometricchoice.interfaces.IElectionResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/election-results")
public class ElectionResultController {

    private final IElectionResultService electionResultService;

    public ElectionResultController(IElectionResultService electionResultService) {
        this.electionResultService = electionResultService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionResultDTO> getElectionResultById(@PathVariable("id") Long id) {
        ElectionResultDTO electionResultDTO = electionResultService.getElectionResultById(id);
        return ResponseEntity.ok(electionResultDTO);
    }

    @GetMapping
    public ResponseEntity<List<ElectionResultDTO>> getAllElectionResults() {
        List<ElectionResultDTO> electionResultDTOList = electionResultService.getAllElectionResults();
        return ResponseEntity.ok(electionResultDTOList);
    }

    @PostMapping
    public ResponseEntity<ElectionResultDTO> createElectionResult(@RequestBody ElectionResultDTO electionResultDTO) {
        ElectionResultDTO createdElectionResultDTO = electionResultService.createElectionResult(electionResultDTO);
        return new ResponseEntity<>(createdElectionResultDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ElectionResultDTO> updateElectionResult(@RequestBody ElectionResultDTO electionResultDTO) {
        ElectionResultDTO updatedElectionResultDTO = electionResultService.updateElectionResult(electionResultDTO);
        return ResponseEntity.ok(updatedElectionResultDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElectionResultById(@PathVariable("id") Long id) {
        electionResultService.deleteElectionResultById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
