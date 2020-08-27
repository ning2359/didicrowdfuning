package com.didi.exception;

public class UpdateAcctRepeatExcepyion extends RuntimeException {
    public UpdateAcctRepeatExcepyion() {
        super();
    }

    public UpdateAcctRepeatExcepyion(String message) {
        super(message);
    }

    public UpdateAcctRepeatExcepyion(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateAcctRepeatExcepyion(Throwable cause) {
        super(cause);
    }

    protected UpdateAcctRepeatExcepyion(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
