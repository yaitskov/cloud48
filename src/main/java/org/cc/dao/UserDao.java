package org.cc.dao;

import org.cc.ent.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Daneel Yaitskov
 */
@Repository
public class UserDao extends Dao<User> {

    public User findByLogin(String login) {
        Query q = em().createQuery("select u from User u where u.login = :login");
        q.setParameter("login", login);
        return (User)q.getSingleResult();
    }
}
