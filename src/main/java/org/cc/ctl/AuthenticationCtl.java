package org.cc.ctl;

import org.cc.dao.UserDao;
import org.cc.dao.UserSessionDao;
import org.cc.ent.User;
import org.cc.ent.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import java.util.Date;

/**
 * Open new session.
 * <p/>
 * Daneel Yaitskov
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationCtl {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationCtl.class);

    @Resource
    private UserSessionDao sessionDao;

    @Resource
    private UserDao userDao;

    @Resource(name = "sessionEncoder")
    private PasswordEncoder sessionEncoder;

    /**
     * Opens new session. Old sessions are cleaned automatically
     * by date or by preemption newest for the same login
     *
     * @param login
     * @param pass
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("login") String login,
                        @RequestParam("pass") String pass)
            throws AuthenticationException {
        User u = userDao.findByLogin(login);
        if (u.getPass().equals(pass)) {
            //todo: remove oldest if too many
            return createNewSession(u).getKey();
        }
        throw new AuthenticationException("login/password is invalid");
    }

    public UserSession createNewSession(User u) {
        UserSession session = new UserSession();
        session.setOwner(u);
        session.setUpdated(new Date());
        String keySource = u.getLogin() + System.currentTimeMillis();
        session.setKey(sessionEncoder.encodePassword(keySource, null));
        sessionDao.save(session);
        return session;
    }
}
