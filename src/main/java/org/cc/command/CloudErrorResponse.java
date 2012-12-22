package org.cc.command;

/**
 * Daneel Yaitskov
 */
public class CloudErrorResponse {

    private String error;
    private String message;

    public CloudErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public CloudErrorResponse(Throwable e) {
        this(e.getClass().getSimpleName(), e.getMessage());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
