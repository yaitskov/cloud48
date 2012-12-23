package org.cc.unit.exception;

import junit.framework.Assert;
import org.cc.exception.ValueClassTakesException;
import org.junit.Before;
import org.junit.Test;

/**
 * Daneel Yaitskov
 */
public class ValueClassTakesExceptionTest {

    ValueClassTakesException tested;

    @Before
    public void setUp() {
        tested = new ValueClassTakesException();
    }

    public static class ConException {
        public ConException(Exception e) {
        }
    }

    public static class ConString {
        public ConString(String s) {
        }
    }

    @Test
    public void testPutOk() {
        tested.put(Exception.class, ConException.class);
        Assert.assertEquals(1, tested.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutFailed() {
        tested.put(Exception.class, ConString.class);
    }
}
