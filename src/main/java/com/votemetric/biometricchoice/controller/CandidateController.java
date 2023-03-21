package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.CandidateDTO;
import com.votemetric.biometricchoice.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateDTO>> getAllCandidates() {
        List<CandidateDTO> candidates = candidateService.getAllCandidates();
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable("id") Long id) {
        CandidateDTO candidate = candidateService.getCandidateById(id);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> addCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO candidate = candidateService.addCandidate(candidateDTO);
        return new ResponseEntity<>(candidate, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CandidateDTO> updateCandidate(@RequestBody CandidateDTO candidateDTO) {
        CandidateDTO candidate = candidateService.updateCandidate(candidateDTO);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
