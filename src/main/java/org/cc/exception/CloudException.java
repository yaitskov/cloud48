package org.cc.exception;

/**
 * Daneel Yaitskov
 */
public class CloudException extends Exception {

    public CloudException() {
    }

    public CloudException(String message) {
        super(message);
    }

    public CloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudException(Throwable cause) {
        super(cause);
    }

    public CloudException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
