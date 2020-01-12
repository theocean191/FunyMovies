package com.lhmai.funnytube.exception;

public class YouTubeApiCallException extends InternalServerException {
    public YouTubeApiCallException() {
        super("Failed while calling YouTube api");
    }

    public YouTubeApiCallException(String message) {
        super(message);
    }

    public YouTubeApiCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public YouTubeApiCallException(Throwable cause) {
        super(cause);
    }

    public YouTubeApiCallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
