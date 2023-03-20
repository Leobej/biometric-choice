package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.VoterHistoryDTO;
import com.votemetric.biometricchoice.interfaces.IVoterHistoryService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votershistory")
public class VoterHistoryController {
    private final IVoterHistoryService voterHistoryService;
    private final Mapper mapper;

    public VoterHistoryController(IVoterHistoryService voterHistoryService, Mapper mapper) {
        this.voterHistoryService = voterHistoryService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoterHistoryDTO> getVoterHistoryById(@PathVariable("id") Long id) {
        VoterHistoryDTO voterHistoryDTO = mapper.convertToType(voterHistoryService.getVoterHistoryById(id), VoterHistoryDTO.class);
        return ResponseEntity.ok(voterHistoryDTO);
    }

    @PostMapping
    public ResponseEntity<VoterHistoryDTO> createVoterHistory(@RequestBody VoterHistoryDTO voterHistoryDTO) {
        VoterHistoryDTO savedVoterHistoryDTO = mapper.convertToType(voterHistoryService.saveVoterHistory(voterHistoryDTO), VoterHistoryDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVoterHistoryDTO);
    }

    @PutMapping
    public ResponseEntity<VoterHistoryDTO> updateVoterHistory(@RequestBody VoterHistoryDTO voterHistoryDTO) {
        VoterHistoryDTO updatedVoterHistoryDTO = mapper.convertToType(voterHistoryService.updateVoterHistory(voterHistoryDTO), VoterHistoryDTO.class);
        return ResponseEntity.ok(updatedVoterHistoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoterHistory(@PathVariable("id") Long id) {
        voterHistoryService.deleteVoterHistoryById(id);
        return ResponseEntity.noContent().build();
    }
}
