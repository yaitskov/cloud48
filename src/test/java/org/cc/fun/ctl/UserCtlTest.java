package org.cc.fun.ctl;

import junit.framework.Assert;
import org.cc.ctl.UserCtl;
import org.cc.dao.UserDao;
import org.cc.ent.User;
import org.cc.exception.UserAlreadyExistException;
import org.cc.util.DbCleaner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
        int id = userCtl.create("ll", "pp");
        List<User> found = userDao.findAll();
        Assert.assertEquals(1, found.size());
        Assert.assertEquals(id, found.get(0).getId());
        Assert.assertEquals("ll", found.get(0).getLogin());
    }

    @Test
    public void testCreateValidation() throws UserAlreadyExistException {
        Assert.assertEquals(0, userDao.findAll().size());
        try {
            userCtl.create("", "");
            Assert.fail("no exception");
        } catch (ValidationException e) {
            // ok
        }
        Assert.assertEquals(0, userDao.findAll().size());
    }

    @Test
    public void testCreateExisting() throws UserAlreadyExistException {
        Assert.assertEquals(0, userDao.findAll().size());
        userCtl.create("ll", "pp");
        try {
            userCtl.create("ll", "pp");
            Assert.fail("no exception");
        } catch (UserAlreadyExistException e) {
            // ok
        }
        Assert.assertEquals(1, userDao.findAll().size());
    }
}
