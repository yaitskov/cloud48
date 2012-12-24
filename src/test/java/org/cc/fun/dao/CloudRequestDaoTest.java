package org.cc.fun.dao;

import junit.framework.Assert;
import org.cc.dao.CloudRequestDao;
import org.cc.dao.UserDao;
import org.cc.ent.CreateVmRequest;
import org.cc.ent.NewVmSpec;
import org.cc.ent.RequestStatus;
import org.cc.ent.User;
import org.cc.util.BaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
public class CloudRequestDaoTest extends BaseDao {


    @Resource
    UserDao userDao;

    @Resource
    CloudRequestDao requestDao;

    @Resource(name = "ttReuse")
    TransactionTemplate ttReuse;


    @Test
    public void testCreateVm() {

        final User u = new User();
        ttReuse.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                u.setLogin("l");
                u.setPass("p");
                userDao.save(u);
            }
        });

        final CreateVmRequest cvm = new CreateVmRequest();
        NewVmSpec spec = new NewVmSpec();
        spec.setCore(1);
        spec.setDisk(1);
        spec.setFrequency(1);
        spec.setMemory(100);
        spec.setNetwork(32);
        spec.setType("ubuntu");
        cvm.setAuthor(u);
        cvm.setSpec(spec);
        cvm.setCreated(new Date());
        cvm.setStatus(RequestStatus.IN_QUEUE);

        ttReuse.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                requestDao.save(cvm);
            }
        });

        CreateVmRequest request = (CreateVmRequest)requestDao.find(cvm.getId());
        Assert.assertEquals(100, request.getSpec().getMemory());

    }

}
