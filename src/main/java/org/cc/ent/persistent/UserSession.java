package org.cc.ent.persistent;

import javax.persistence.*;
import java.util.Date;

/**
 * Daneel Yaitskov
 */
@Entity
@Table(name = "t_user_session")
public class UserSession extends AbstractEntity {

    /**
     * Unique session key.
     */
    @Column(name = "skey", unique = true)
    private String key;

    /**
     * Last time when session was updated.
     * Last time when user called application method.
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;


    /**
     * User is identified by this session key.
     */
    @ManyToOne
    @JoinColumn(name = "sowner")
    private User owner;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
