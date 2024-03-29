package com.votemetric.biometricchoice.modules.voter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<VoterDTO>> getAllVoters(
            @RequestParam(required = false) String search,
            Pageable pageable) {

        Page<VoterDTO> page;
        if (search != null && !search.trim().isEmpty()) {
            page = voterService.getVotersByName(search, pageable);
        } else {
            page = voterService.getAllVoters(pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }


//        @GetMapping
//    public ResponseEntity<Page<CandidateDTO>> getAllCandidates(
//            @RequestParam(required = false) String description,
//            Pageable pageable)  {
//        Page<CandidateDTO> page;
//        if (description != null) {
//            page = candidateService.getCandidateByName(description, pageable);
//        } else {
//            page = candidateService.getAllCandidates(pageable);
//        }
//        return new ResponseEntity<>(page, HttpStatus.OK);
//    }

    @PostMapping("/register")
    public ResponseEntity<VoterDTO> createVoter(@RequestBody VoterDTO voterDTO) {
        VoterDTO newVoter = voterService.saveVoter(voterDTO);
        return new ResponseEntity<>(newVoter, HttpStatus.CREATED);
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
