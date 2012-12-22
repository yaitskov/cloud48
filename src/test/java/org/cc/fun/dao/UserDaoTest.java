package org.cc.fun.dao;

import org.cc.dao.UserDao;
import org.cc.util.BaseDao;
import org.cc.util.DbCleaner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dao.xml")
public class UserDaoTest extends BaseDao {

    @Resource
    UserDao userDao;

    @Test
    public void testFindByLogin() {
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testFindByLoginNotExistingUser() {
        userDao.findByLogin("ghost");
    }
}
