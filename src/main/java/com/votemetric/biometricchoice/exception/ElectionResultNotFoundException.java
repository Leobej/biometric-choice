package com.votemetric.biometricchoice.exception;

public class ElectionResultNotFoundException extends RuntimeException {

    public ElectionResultNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}
