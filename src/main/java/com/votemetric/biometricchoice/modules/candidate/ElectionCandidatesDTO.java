package com.votemetric.biometricchoice.modules.candidate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ElectionCandidatesDTO {
    private List<Candidate> candidates;
    private long electionId;
    private long location;

    public ElectionCandidatesDTO(List<Candidate> candidates, long electionId) {
        this.candidates = candidates;
        this.electionId = electionId;
    }

    public ElectionCandidatesDTO(List<Candidate> candidates, long electionId, long location) {
        this.candidates = candidates;
        this.electionId = electionId;
        this.location = location;
    }
}