package com.votemetric.biometricchoice.controller;

import com.votemetric.biometricchoice.dto.VoteDTO;
import com.votemetric.biometricchoice.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping
    public ResponseEntity<List<VoteDTO>> getAllVotes() {
        List<VoteDTO> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDTO> getVoteById(@PathVariable("id") Long id) {
        VoteDTO vote = voteService.getVoteById(id);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VoteDTO> addVote(@RequestBody VoteDTO voteDTO) {
        VoteDTO vote = voteService.addVote(voteDTO);
        return new ResponseEntity<>(vote, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<VoteDTO> updateVote(@RequestBody VoteDTO voteDTO) {
        VoteDTO vote = voteService.updateVote(voteDTO);
        return new ResponseEntity<>(vote, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable("id") Long id) {
        voteService.deleteVote(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}