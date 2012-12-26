package org.cc.ent;

/**
 * Daneel Yaitskov
 */
public enum RequestStatus {

    /**
     * HTTP Request is processed successfully
     * and cloud request created and pushed to the command queue.
     */
    IN_QUEUE,
    /**
     * Cloud request is finished and its client can get result.
     * Command should place answer to response queue.
     * Request object is automatically deleted.
     */
    OK,
    /**
     * Cloud request finished with error
     */
    ERROR,

    /**
     * synchronous operation is executing
     * some thread from cloud thread pool works
     */
    RUNNING,
    /**
     * wait hypervisor and/or CIS
     */
    WAIT_;

}
