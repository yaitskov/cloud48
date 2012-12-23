package org.cc.unit.util;

import junit.framework.Assert;
import org.cc.util.ReflectionUtil;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * Daneel Yaitskov
 */
public class ReflectionUtilTest {

    @Test
    public void testDistance() {

        Assert.assertEquals(0, ReflectionUtil.distance(Integer.class, Integer.class));
        Assert.assertEquals(0, ReflectionUtil.distance(Class.class, Class.class));
        Assert.assertEquals(0, ReflectionUtil.distance(Object.class, Object.class));

        Assert.assertEquals(2, ReflectionUtil.distance(Integer.class, Object.class));
        Assert.assertEquals(1, ReflectionUtil.distance(Class.class, Object.class));

        Assert.assertEquals(1, ReflectionUtil.distance(Integer.class, Number.class));
        Assert.assertEquals(4, ReflectionUtil.distance(UnknownHostException.class, Object.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceNotInTheSameBranch() {
        ReflectionUtil.distance(Integer.class, Exception.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceNullParent() {
        ReflectionUtil.distance(Integer.class, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDistanceNullSubClass() {
        ReflectionUtil.distance(null, Integer.class);
    }
}
