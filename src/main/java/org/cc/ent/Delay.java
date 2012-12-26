package org.cc.ent;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Daneel Yaitskov
 */
@Embeddable
public class Delay {

    /**
     * in milliseconds
     */
    @Min(0)
    @Max(100000)
    @Column(nullable = true)
    private long delay;

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return delay + "ms";
    }
}
