package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.VoterHistoryDTO;
import com.votemetric.biometricchoice.interfaces.IVoterHistoryService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voters-history")
public class VoterHistoryController {
    private final IVoterHistoryService voterHistoryService;
    private final Mapper mapper;

    public VoterHistoryController(IVoterHistoryService voterHistoryService, Mapper mapper) {
        this.voterHistoryService = voterHistoryService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoterHistoryDTO> getVoterHistoryById(@PathVariable("id") Long id) {
        VoterHistoryDTO voterHistoryDTO = voterHistoryService.getVoterHistoryById(id);
        return ResponseEntity.ok(voterHistoryDTO);
    }

    @GetMapping
    public ResponseEntity<List<VoterHistoryDTO>> getAllVoterHistory() {
        return ResponseEntity.ok(voterHistoryService.getAllVoterHistory());
    }

    @PostMapping
    public ResponseEntity<VoterHistoryDTO> createVoterHistory(@RequestBody VoterHistoryDTO voterHistoryDTO) {
        VoterHistoryDTO savedVoterHistoryDTO = voterHistoryService.saveVoterHistory(voterHistoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVoterHistoryDTO);
    }

    @PutMapping
    public ResponseEntity<VoterHistoryDTO> updateVoterHistory(@RequestBody VoterHistoryDTO voterHistoryDTO) {
        VoterHistoryDTO updatedVoterHistoryDTO = voterHistoryService.updateVoterHistory(voterHistoryDTO);
        return ResponseEntity.ok(updatedVoterHistoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterHistory(@PathVariable("id") Long id) {
        voterHistoryService.deleteVoterHistoryById(id);
        return ResponseEntity.noContent().build();
    }
}
