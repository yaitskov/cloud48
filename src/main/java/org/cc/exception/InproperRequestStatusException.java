package org.cc.exception;

import org.cc.ent.RequestStatus;

/**
 * Daneel Yaitskov
 */
public class InproperRequestStatusException extends CloudException {

    private RequestStatus expectedStatus;
    private RequestStatus gotStatus;
    private int requestId;

    public InproperRequestStatusException(RequestStatus expectedStatus,
                                          RequestStatus gotStatus,
                                          int requestId) {
        super("request " + requestId + " has status " + gotStatus + " but expected " + expectedStatus);
        this.expectedStatus = expectedStatus;
        this.gotStatus = gotStatus;
        this.requestId = requestId;
    }

    public RequestStatus getExpectedStatus() {
        return expectedStatus;
    }

    public RequestStatus getGotStatus() {
        return gotStatus;
    }

    public int getRequestId() {
        return requestId;
    }


}
