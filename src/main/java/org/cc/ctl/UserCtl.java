package org.cc.ctl;

import org.cc.dao.UserDao;
import org.cc.ent.User;
import org.cc.ent.UserCredential;
import org.cc.exception.UserAlreadyExistException;
import org.cc.response.CloudErrorResponse;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;

/**
 * Daneel Yaitskov
 */
@Controller
@RequestMapping("/user")
public class UserCtl {

    @Resource
    private UserDao userDao;

    /**
     * Creates new user
     *
     * @return user id
     */
    @Transactional //(propagation = Propagation.REQUIRES_NEW)
    @RequestMapping("/create")
    @ResponseBody
    public int create(@Valid UserCredential creds)
            throws UserAlreadyExistException {
        try {
            userDao.findByLogin(creds.getLogin());
            throw new UserAlreadyExistException("user '" + creds.getLogin() + "' is already exist");
        } catch (EmptyResultDataAccessException e) {
            User u = new User();
            u.setLogin(creds.getLogin());
            u.setPass(creds.getPass());
            userDao.save(u);
            return u.getId();
        }
    }
//
//    @ExceptionHandler({
//            UserAlreadyExistException.class,
//            ValidationException.class})
//    @ResponseBody
//    public CloudErrorResponse handleAuthException(Exception e, HttpServletResponse resp) {
//        return new CloudErrorResponse(e);
//    }
}
