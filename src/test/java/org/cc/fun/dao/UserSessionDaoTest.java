package org.cc.fun.dao;

import junit.framework.Assert;
import org.cc.dao.UserSessionDao;
import org.cc.ent.persistent.UserSession;
import org.cc.util.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao.xml")
public class UserSessionDaoTest extends BaseDao {

    @Resource
    UserSessionDao sessionDao;

    @Resource(name = "ttReuse")
    TransactionTemplate ttReuse;

    @Test
    public void test() {
        Assert.assertEquals(0, sessionDao.findAll().size());
        ttReuse.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                UserSession session = new UserSession();
                session.setKey("123");
                session.setUpdated(new Date());

                sessionDao.save(session);
            }
        });
        Assert.assertEquals(1, sessionDao.findAll().size());
        Assert.assertNotNull(sessionDao.findByKey("123"));
    }
}
