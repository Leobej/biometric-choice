package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.ElectionDTO;
import com.votemetric.biometricchoice.interfaces.IElectionService;
import com.votemetric.biometricchoice.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elections")
public class ElectionController {
    private final IElectionService electionService;
    private final Mapper mapper;

    public ElectionController(IElectionService electionService, Mapper mapper) {
        this.electionService = electionService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<ElectionDTO>> getAllElections() {
        List<ElectionDTO> elections = electionService.getAllElections();
        return ResponseEntity.ok(elections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionDTO> getElectionById(@PathVariable("id") Long id) {
        ElectionDTO electionDTO = electionService.getElectionById(id);
        return ResponseEntity.ok(electionDTO);
    }

    @PostMapping
    public ResponseEntity<ElectionDTO> createElection(@RequestBody ElectionDTO electionDto) {
        ElectionDTO savedElectionDto = electionService.createElection(electionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedElectionDto);
    }

    @PutMapping
    public ResponseEntity<ElectionDTO> updateElection(@RequestBody ElectionDTO electionDto) {
        ElectionDTO updatedElectionDto =  electionService.updateElection(electionDto);
        return ResponseEntity.ok(updatedElectionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable("id") Long id) {
        electionService.deleteElectionById(id);
        return ResponseEntity.noContent().build();
    }
}

