package org.cc.exception;

/**
 * Daneel Yaitskov
 */
public class ContainerManagerException extends CloudException {

    public ContainerManagerException() {
    }

    public ContainerManagerException(String message) {
        super(message);
    }

    public ContainerManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContainerManagerException(Throwable cause) {
        super(cause);
    }
}
