package com.votemetric.biometricchoice.exception;

public class CandidateNotFoundException  extends RuntimeException {

    public CandidateNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}
