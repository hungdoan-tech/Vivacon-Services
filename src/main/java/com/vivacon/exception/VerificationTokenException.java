package com.vivacon.exception;

public class VerificationTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VerificationTokenException(String token, String message) {
        super(String.format("Verification token is not valid", token, message));
    }
}
