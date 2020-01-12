package com.lhmai.funnytube.exception;

public class PasswordConfirmationNotMatchException extends BussinessException {
    public PasswordConfirmationNotMatchException() {
        super("Password confirmation is not matched");
    }

    public PasswordConfirmationNotMatchException(String message) {
        super(message);
    }

    public PasswordConfirmationNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordConfirmationNotMatchException(Throwable cause) {
        super(cause);
    }

    public PasswordConfirmationNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
