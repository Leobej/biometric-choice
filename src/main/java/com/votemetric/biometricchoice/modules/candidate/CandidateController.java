package com.votemetric.biometricchoice.modules.candidate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/candidates")

public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<Page<CandidateDTO>> getAllCandidates(
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            Pageable pageable) {
        Page<CandidateDTO> page;
        if (firstname != null || lastname != null) {
            page = candidateService.getCandidatesByName(firstname, lastname, pageable);
        } else {
            page = candidateService.getAllCandidates(pageable);
        }
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateDTO> getCandidateById(@PathVariable("id") Long id) {
        CandidateDTO candidate = candidateService.getCandidateById(id);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidateDTO> addCandidate(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("party") String party,
            @RequestParam("position") String position,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        byte[] imageBytes = null;
        if (image != null && !image.isEmpty()) {
            try {
                imageBytes = image.getBytes();
            } catch (IOException e) {
                // Handle the exception appropriately
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        CandidateDTO c = new CandidateDTO(0, firstName, lastName, party, position, imageBytes);
        CandidateDTO candidate = candidateService.addCandidate(c);
        return new ResponseEntity<>(candidate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        // Check if the candidate with the given ID exists
        candidateService.checkIfCandidateExist(id);
        // Update the candidate
        CandidateDTO updatedCandidate = candidateService.updateCandidate(id, candidateDTO);
        return ResponseEntity.ok(updatedCandidate);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable("id") Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CandidateDTO>> searchCandidates(@RequestParam("query") String query) {
        List<CandidateDTO> candidates = candidateService.searchCandidates(query);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }

    @GetMapping("/searchName")
    public ResponseEntity<List<CandidateNameDTO>> findCandidatesByFirstnameOrLastname(@RequestParam("query") String query) {
        List<CandidateNameDTO> candidates = candidateService.findCandidatesByFirstnameOrLastname(query);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }
}
