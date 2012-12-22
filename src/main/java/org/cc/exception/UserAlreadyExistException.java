package org.cc.exception;

/**
 * Daneel Yaitskov
 */
public class UserAlreadyExistException extends CloudException {
    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
