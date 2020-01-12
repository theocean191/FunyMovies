package com.lhmai.funnytube.exception;

public class InvalidMovieIdException extends BussinessException {
    public InvalidMovieIdException() {
    }

    public InvalidMovieIdException(String message) {
        super(message);
    }

    public InvalidMovieIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMovieIdException(Throwable cause) {
        super(cause);
    }

    public InvalidMovieIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
