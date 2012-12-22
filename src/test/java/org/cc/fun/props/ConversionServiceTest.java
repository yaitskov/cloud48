package org.cc.fun.props;

import junit.framework.Assert;
import org.cc.props.PropertyExposer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Daneel Yaitskov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:property.xml")
public class ConversionServiceTest {

    @Resource
    DefaultConversionService conversionService;


    @Test
    public void testDefaultConversionService() {
        Assert.assertTrue(conversionService.convert("true", Boolean.class));
        Assert.assertFalse(conversionService.convert("false", Boolean.class));
        Assert.assertEquals(234, conversionService.convert("234", Integer.class).intValue());
        Assert.assertFalse(conversionService.convert("0", Boolean.class));
        //Assert.assertFalse(conversionService.convert("d!0ddd", Boolean.class));

    }
}
