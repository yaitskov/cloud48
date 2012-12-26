package org.cc.ent;

import org.cc.exception.CloudException;
import org.cc.util.LogUtil;
import org.hibernate.annotations.Columns;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Null;

/**
 * Daneel Yaitskov
 */
@Entity
@DiscriminatorValue("nop")
public class Nop extends CloudRequest {

    private static final Logger logger = LogUtil.get();

    /**
     * How many time thread will sleep.
     */
    @Embedded
    private Delay delay;

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    @Override
    public void execute() throws CloudException {
        logger.debug("start sleep {} ms", delay);
        try {
            Thread.sleep(delay.getDelay());
            logger.debug("woken up");
            setStatus(RequestStatus.OK);
        } catch (InterruptedException e) {
            logger.warn("interrupted");
        }
    }
}
