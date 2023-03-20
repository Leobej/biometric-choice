package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.VoterDTO;
import com.votemetric.biometricchoice.service.VoterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters")
public class VoterController {

    private final VoterService voterService;

    @Autowired
    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoterDTO> getVoterById(@PathVariable("id") Long id) {
        VoterDTO voterDTO = voterService.getVoterById(id);
        return ResponseEntity.ok(voterDTO);
    }

    @GetMapping
    public ResponseEntity<List<VoterDTO>> getAllVoters() {
        List<VoterDTO> voterDTOList = voterService.getAllVoters();
        return ResponseEntity.ok(voterDTOList);
    }

    @PostMapping
    public ResponseEntity<VoterDTO> createVoter(@RequestBody VoterDTO voterDTO) {
        VoterDTO createdVoterDTO = voterService.saveVoter(voterDTO);
        return new ResponseEntity<>(createdVoterDTO, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<VoterDTO> updateVoter(@RequestBody VoterDTO voterDTO) {
        VoterDTO updatedVoterDTO = voterService.updateVoter(voterDTO);
        return ResponseEntity.ok(updatedVoterDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterById(@PathVariable("id") Long id) {
        voterService.deleteVoterById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
