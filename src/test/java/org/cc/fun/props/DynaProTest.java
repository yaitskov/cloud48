package org.cc.fun.props;

import junit.framework.Assert;
import org.cc.props.DynaBoolPro;
import org.cc.props.DynaStrPro;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:property.xml")
public class DynaProTest {

    @Resource(name = "test.boolvalt")
    DynaBoolPro p1;

    @Resource(name = "test.boolvalf")
    DynaBoolPro p2;

    @Resource(name = "str.test")
    DynaStrPro p3;

    @Test
    public void test() {
        Assert.assertTrue(p1.get());
        Assert.assertFalse(p2.get());
        Assert.assertEquals("hello", p3.get());
    }
}
