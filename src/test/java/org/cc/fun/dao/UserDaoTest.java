package org.cc.fun.dao;

import junit.framework.Assert;
import org.cc.dao.UserDao;
import org.cc.ent.User;
import org.cc.util.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao.xml")
public class UserDaoTest extends BaseDao {

    @Resource
    UserDao userDao;

    @Resource(name = "ttReuse")
    TransactionTemplate ttReuse;

    @Test
    public void testFindByLogin() {
        ttReuse.execute(
                new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        User u = new User();
                        u.setLogin("u1");
                        u.setPass("p1");

                        userDao.save(u);

                        u = new User();

                        u.setLogin("u2");
                        u.setPass("p2");

                        userDao.save(u);
                    }
                }
        );

        Assert.assertNotNull(userDao.findByLogin("u1"));
        Assert.assertNotNull(userDao.findByLogin("u2"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testFindByLoginNotExistingUser() {
        userDao.findByLogin("ghost");
    }
}
