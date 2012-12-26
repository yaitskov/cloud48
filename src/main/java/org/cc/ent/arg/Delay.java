package org.cc.ent.arg;

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
    // required cause generated DDL in functional tests
    // by default with not null for scalar types
    // but this field of subclass mapped to single table
    // so objects of other classes in the same hierarchy
    // cannot be persisted (this field doesn't exist for them and gets null)
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
