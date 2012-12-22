package org.cc.fun.security;

import junit.framework.Assert;
import org.cc.dao.UserDao;
import org.cc.dao.UserSessionDao;
import org.cc.ent.User;
import org.cc.ent.UserSession;
import org.cc.security.CloudSessionAuthenticateFilter;
import org.cc.util.DbCleaner;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:security.xml")
public class CloudSessionAuthenticateFilterTest {

    @Resource
    UserDao userDao;

    @Resource
    UserSessionDao sessionDao;

    @Resource(name = "ttReuse")
    TransactionTemplate ttReuse;

    @Resource
    DbCleaner cleaner;

    @Resource
    CloudSessionAuthenticateFilter filter;

    @Before
    public void setUp() {
        cleaner.clean();
    }

    @Test
    public void testAuthAsAnonymous() {
        Assert.assertEquals(0, sessionDao.findAll().size());
        Assert.assertEquals(0, userDao.findAll().size());

        ttReuse.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                HttpServletRequest req = new MockHttpServletRequest();
                filter.authAsAnonymous(req);
            }
        });

        Assert.assertEquals(0, sessionDao.findAll().size());
        List<User> found = userDao.findAll();
        Assert.assertEquals(1, found.size());
        Assert.assertEquals(CloudSessionAuthenticateFilter.NOBODY,
                found.get(0).getLogin());
    }
}
