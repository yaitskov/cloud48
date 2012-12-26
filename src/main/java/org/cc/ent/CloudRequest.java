package org.cc.ent;

import org.cc.exception.CloudException;
import org.cc.util.LogUtil;
import org.slf4j.Logger;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity is an asynchronous command.
 *
 * Daneel Yaitskov
 */
@Entity
@Table(name = "t_cloud_request")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "cmd", discriminatorType = DiscriminatorType.STRING)
public class CloudRequest extends AbstractEntity {

    private static final Logger logger = LogUtil.get();

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    /**
     * Used for filling request queue at startup.
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Column
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    /**
     * How many milliseconds the system spent thread time.
     */
    @Column
    private long spentTime;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public long getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(long spentTime) {
        this.spentTime = spentTime;
    }

    public void execute() throws CloudException {
        logger.debug("do nothing");
    }
}
