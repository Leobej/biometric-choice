package com.votemetric.biometricchoice.exception;

public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}
