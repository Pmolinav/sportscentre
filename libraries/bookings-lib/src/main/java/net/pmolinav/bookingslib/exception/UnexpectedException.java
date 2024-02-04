package net.pmolinav.bookingslib.exception;

import lombok.Getter;

@Getter
public class UnexpectedException extends RuntimeException {
    private final int statusCode;

    public UnexpectedException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
