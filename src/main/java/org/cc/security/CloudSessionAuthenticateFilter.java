package org.cc.security;

import com.google.common.collect.Lists;
import org.cc.dao.UserDao;
import org.cc.dao.UserSessionDao;
import org.cc.ent.User;
import org.cc.ent.UserSession;
import org.cc.props.DynaBoolPro;
import org.cc.props.DynaPro;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * daneel yaitskov
 */
public class CloudSessionAuthenticateFilter extends GenericFilterBean {

    public static final String NOBODY = "nobody";
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource
            = new WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private UserSessionDao sessionDao;
    @Resource
    private UserDao userDao;
    @Resource(name = "security.anonymous.allow")
    private DynaBoolPro allowAnonymous;
    private String anonymousLogin = NOBODY;

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(sessionDao, "sessionDao is required");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        try {
            tryAuthenticate((HttpServletRequest) req, (HttpServletResponse) res);
        } catch (AuthenticationException failed) {
            if (allowAnonymous.get()) {
                authAsAnonymous((HttpServletRequest) req);
            } else {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Transactional
    public void tryAuthenticate(HttpServletRequest req, HttpServletResponse res) {
        String session = req.getParameter("session");
        if (session == null) {
            throw new SessionAuthenticationException("HTTP session attribute is null");
        }
        try {
            // session is valid while it exists
            UserSession usrSes = sessionDao.findByKey(session);
            List<? extends GrantedAuthority> grants =
                    Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"));

            CloudAuthenticationToken token = new CloudAuthenticationToken(usrSes, grants);

            token.setDetails(authenticationDetailsSource.buildDetails(req));
            logger.debug("Authentication success: " + token);
            usrSes.setUpdated(new Date());
            sessionDao.merge(usrSes);
            SecurityContextHolder.getContext().setAuthentication(token);
        } catch (IllegalStateException e) {
            logger.error("fail to authenticate by session " + session, e);
            throw new SessionAuthenticationException("fail to authenticate request with session "
                    + session + " casue: " + e.getMessage());
        } catch (PersistenceException e) {
            logger.error("fail to authenticate request with session " + session, e);
            throw new SessionAuthenticationException("fail to authenticate request with session "
                    + session + " casue: " + e.getMessage());
        }
    }

    public void authAsAnonymous(HttpServletRequest req) {
        // session is valid while it exists
        User anonymous;
        try {
            anonymous = userDao.findByLogin(anonymousLogin);
        } catch (EmptyResultDataAccessException e) {
            anonymous = createAndSaveAnonymous();
        }
        UserSession usrSes = new UserSession(); // no persist
        usrSes.setOwner(anonymous);
        usrSes.setUpdated(new Date());

        List<? extends GrantedAuthority> grants =
                Lists.newArrayList(new SimpleGrantedAuthority("ROLE_USER"));

        CloudAuthenticationToken token = new CloudAuthenticationToken(usrSes, grants);

        token.setDetails(authenticationDetailsSource.buildDetails(req));
        logger.debug("Authentication success: " + token);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User createAndSaveAnonymous() {
        User ano = new User();
        ano.setLogin(anonymousLogin);
        ano.setPass("1");
        userDao.save(ano);
        return ano;
    }
}
