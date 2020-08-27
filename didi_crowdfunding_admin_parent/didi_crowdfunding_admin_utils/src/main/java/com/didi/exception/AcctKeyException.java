package com.didi.exception;

public class AcctKeyException  extends RuntimeException{

    public AcctKeyException() {
        super();
    }

    public AcctKeyException(String message) {
        super(message);
    }

    public AcctKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public AcctKeyException(Throwable cause) {
        super(cause);
    }

    protected AcctKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
