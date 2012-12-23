package org.cc.unit.exception;

import junit.framework.Assert;
import org.cc.exception.KeyExceptionClassMap;
import org.junit.Before;
import org.junit.Test;

import java.security.AccessControlException;

/**
 * Daneel Yaitskov
 */
public class KeyExceptionClassMapTest {

    KeyExceptionClassMap<String> tested;

    @Before
    public void setUp() {
        tested = new KeyExceptionClassMap<String>();
    }

    @Test
    public void testPutOk() {
        tested.put(Exception.class, "ex");
        tested.put(SecurityException.class, "sec");

        Assert.assertEquals(2, tested.size());
        Assert.assertEquals("ex", tested.get(Exception.class));
        Assert.assertEquals("sec", tested.get(SecurityException.class));
        Assert.assertEquals("sec", tested.get(AccessControlException.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutFail() {
        tested.put(Throwable.class, "ex");
    }
}
