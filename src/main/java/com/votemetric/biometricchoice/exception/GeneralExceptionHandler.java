package com.votemetric.biometricchoice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GeneralExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(value = {ApiException.class})
    public static ResponseEntity<Object> handleExceptions(ApiException e) {
        logger.debug("Exception handled:" + e.getMessage() + " with http status: " + e.getHttpStatus());
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public static ResponseEntity<Object> handleConstraintExceptions(ConstraintViolationException e) {
        logger.debug("Exception handled:" + e.getMessage() + " with http status: ");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value={EntityNotFoundException.class})
    public static ResponseEntity<Object> handleEntityNotFoundException(ConstraintViolationException e) {
        logger.debug("Exception handled:" + e.getMessage() + " with http status: ");
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
