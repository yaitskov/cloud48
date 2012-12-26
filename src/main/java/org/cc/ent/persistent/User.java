package org.cc.ent.persistent;

import org.cc.ent.persistent.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Account sends commands to the cloud.
 *
 * Daneel Yaitskov
 */
@Entity
@Table(name = "t_user")
public class User extends AbstractEntity {

    @Column
    private String login;

    @Column
    private String pass;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
