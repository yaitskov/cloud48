package org.cc.exception;

import mockit.internal.expectations.argumentMatching.ClassMatcher;
import org.apache.commons.lang3.ClassUtils;
import org.cc.util.ClassMap;

/**
 * Constrains key class to be subclass of exception.
 * <p/>
 * Daneel Yaitskov
 */
public class KeyExceptionClassMap<V> extends ClassMap<V> {

    public KeyExceptionClassMap() {
    }

    public KeyExceptionClassMap(ClassMap source) {
        super(source);
    }

    @Override
    public V put(Class key, V value) {
        if (ClassUtils.isAssignable(key, Exception.class)) {
            return super.put(key, value);
        }
        throw new IllegalArgumentException("key class " + key.getCanonicalName()
                + " is not assignable to java.lang.Exception");
    }

    // put all reuses put
}
