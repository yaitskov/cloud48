package org.cc.fun.ctl;

import junit.framework.Assert;
import org.cc.ctl.UserCtl;
import org.cc.dao.UserDao;
import org.cc.ent.persistent.User;
import org.cc.ent.arg.UserCredential;
import org.cc.exception.UserAlreadyExistException;
import org.cc.util.DbCleaner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.validation.ValidationException;
import java.util.List;

/**
 * Daneel Yaitskov
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:ctl.xml")
public class UserCtlTest {

    @Resource
    UserCtl userCtl;

    @Resource
    DbCleaner cleaner;

    @Resource
    UserDao userDao;

    @Before
    public void setUp() {
        cleaner.clean();
    }

    @Test
    public void testCreateOk() throws UserAlreadyExistException {
        Assert.assertEquals(0, userDao.findAll().size());
        int id = userCtl.create(new UserCredential("ll", "pp"));
        List<User> found = userDao.findAll();
        Assert.assertEquals(1, found.size());
        Assert.assertEquals(id, found.get(0).getId());
        Assert.assertEquals("ll", found.get(0).getLogin());
    }


    @Ignore("wait release 3.2 for mvc validation test")
    @Test
    public void testCreateValidation() throws Exception {

        try {
            MockHttpServletRequest request = new MockHttpServletRequest("POST","/browser/create");
            MockHttpServletResponse response = new MockHttpServletResponse();
            request.addParameter("login", "");
            request.addParameter("pass", "1");
                        //userCtl.create(new UserCredential("", ""));
            Assert.fail("no exception");
        } catch (ValidationException e) {
            // ok
        }
    }

    @Test
    public void testCreateExisting() throws UserAlreadyExistException {
        Assert.assertEquals(0, userDao.findAll().size());
        userCtl.create(new UserCredential("ll", "pp"));
        try {
            userCtl.create(new UserCredential("ll", "pp"));
            Assert.fail("no exception");
        } catch (UserAlreadyExistException e) {
            // ok
        }
        Assert.assertEquals(1, userDao.findAll().size());
    }
}
