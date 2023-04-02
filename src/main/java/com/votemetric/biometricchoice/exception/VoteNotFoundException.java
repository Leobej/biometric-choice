package com.votemetric.biometricchoice.exception;

public class VoteNotFoundException extends RuntimeException {

    public VoteNotFoundException(Long id) {
        super("The Vote with id '" + id + "' does not exist in our records");
    }
}
