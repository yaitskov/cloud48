package org.cc.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.util.Arrays;

/**
 * Daneel Yaitskov
 */
public class ReflectionUtil {

    /**
     * Returns distance in inheritance hops
     * @param subclass
     * @param parent
     * @return
     * @throws IllegalArgumentException first argument is not subclass of second one
     */
    public static int distance(Class subclass, Class parent) {
        if (subclass == parent)
            return 0;
        if (subclass == null || subclass == Object.class)
            throw new IllegalArgumentException("class " + subclass
                    + " is not subclass of " + parent);
        return 1 + distance(subclass.getSuperclass(), parent);
    }
}
