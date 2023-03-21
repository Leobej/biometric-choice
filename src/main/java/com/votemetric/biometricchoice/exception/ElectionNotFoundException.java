package com.votemetric.biometricchoice.exception;

public class ElectionNotFoundException extends RuntimeException {

    public ElectionNotFoundException(Long id) {
        super("The Candidate with id '" + id + "' does not exist in our records");
    }
}