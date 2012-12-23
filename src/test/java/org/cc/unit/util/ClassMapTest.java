package org.cc.unit.util;

import org.cc.util.ClassMap;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessControlException;

/**
 * Daneel Yaitskov
 */
public class ClassMapTest {

    @Test
    public void testExactMatch() {
        ClassMap<Integer> m = new ClassMap<Integer>();

        m.put(Throwable.class, 1);
        m.put(Exception.class, 2);
        m.put(IOException.class, 3);
        m.put(IllegalArgumentException.class, 4);
        m.put(RuntimeException.class, 5);

        testSuit(m);
        m.remove(SocketException.class);

        ClassMap<Integer>  clone = new ClassMap<Integer>(m);
        Assert.assertEquals(5, m.size());
        Assert.assertEquals(5, clone.size());
        Assert.assertEquals((Integer) 1, m.remove(Throwable.class));
        Assert.assertEquals(4, m.size());
        testSuit(clone);
    }

    public void testSuit(ClassMap<Integer> m) {
        Assert.assertEquals(5, m.size());

        Assert.assertEquals((Integer)5, m.get(RuntimeException.class));
        Assert.assertEquals((Integer)5, m.get(AccessControlException.class));
        Assert.assertEquals((Integer)1, m.get(Throwable.class));
        Assert.assertEquals((Integer)2, m.get(Exception.class));
        Assert.assertEquals((Integer)3, m.get(UnknownHostException.class));

        Assert.assertEquals(null, m.get(Object.class));
        Assert.assertEquals(null, m.get(Integer.class));
        Assert.assertEquals(null, m.get(String.class));

        Assert.assertTrue(m.containsKey(RuntimeException.class));
        Assert.assertTrue(m.containsKey(Exception.class));
        Assert.assertTrue(m.containsKey(UnknownHostException.class));
        Assert.assertTrue(m.containsKey(AccessControlException.class));
        Assert.assertTrue(m.containsKey(SecurityException.class));

        Assert.assertFalse(m.containsKey(Object.class));
        Assert.assertFalse(m.containsKey(Integer.class));
        Assert.assertFalse(m.containsKey(String.class));

        Assert.assertEquals(5, m.values().size());
        Assert.assertEquals(5, m.keySet().size());
    }
}
