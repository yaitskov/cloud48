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
     * Cloud request finished client can get result
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
