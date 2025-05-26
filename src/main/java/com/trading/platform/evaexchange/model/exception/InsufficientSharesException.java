package com.trading.platform.evaexchange.model.exception;

public class InsufficientSharesException extends RuntimeException {
    public InsufficientSharesException(String message) {
        super(message);
    }
}
