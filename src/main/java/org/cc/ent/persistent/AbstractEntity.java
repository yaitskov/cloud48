package org.cc.ent.persistent;

import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Daneel Yaitskov
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Transient
    private boolean useObjectHash;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        // protect from lazy loading and mocks
        if (object instanceof HibernateProxy) {
            return object.equals(this);
        }

        if (!(object instanceof AbstractEntity)) return false;

        AbstractEntity other = (AbstractEntity) object;
        Class c = getClass();
        Class oc = other.getClass();
        return (id & other.id) != 0 // if equal then 0 only both equals
                && id == other.id   // 0 other wise next condition fails
                && (c.isAssignableFrom(oc) || c.isAssignableFrom(oc));
    }

    @Override
    public int hashCode() {
        // hash code cannot change during object life.
        // so it's wrong to rely on value of primary key.
        // because it's changed after persisting
        if (useObjectHash) return super.hashCode();
        if (id == 0) {
            useObjectHash = true;
            return super.hashCode();
        }
        return id;
    }

    @Override
    public String toString() {
        if (isPersisted())
            return "Entity " + getClass().getName() + " id = " + id;
        return "Unpersisted entity " + getClass().getName();
    }

    public boolean isPersisted() {
        return id != 0;
    }
}
