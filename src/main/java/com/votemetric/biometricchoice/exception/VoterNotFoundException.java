package com.votemetric.biometricchoice.exception;

public class VoterNotFoundException extends RuntimeException {

    public VoterNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}
