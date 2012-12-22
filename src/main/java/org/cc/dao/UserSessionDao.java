package org.cc.dao;

import org.cc.ent.UserSession;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Daneel Yaitskov
 */
@Repository
public class UserSessionDao extends Dao<UserSession> {

    /**
     * Returns valid user session.
     * @param key unique session key
     * @return valid user session
     * @throws javax.persistence.PersistenceException
     * @throws IllegalStateException
     */
    public UserSession findByKey(String key) {
        Query q = em().createQuery("select s from UserSession s where s.key = :key");
        q.setParameter(":key", key);
        return (UserSession) q.getSingleResult();
    }
}
