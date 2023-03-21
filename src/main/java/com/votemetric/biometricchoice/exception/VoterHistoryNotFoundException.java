package com.votemetric.biometricchoice.exception;

public class VoterHistoryNotFoundException extends RuntimeException {

    public VoterHistoryNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}
