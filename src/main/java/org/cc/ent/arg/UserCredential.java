package org.cc.ent.arg;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Daneel Yaitskov
 */
public class UserCredential {

    @NotEmpty
    private String login;

    @NotEmpty
    private String pass;

    public UserCredential(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public UserCredential() {
    }

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
