package org.cc.props;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Collects all instantiated {@link DynaPro} beans.
 * Daneel Yaitskov
 */
@Service
public class DynaProSet {

    private Map<String, DynaPro> dynaProperties = new HashMap<String, DynaPro>();

    public synchronized void add(DynaPro pro) {
        if (dynaProperties.containsKey(pro.getPropertyName())) {
            throw new IllegalArgumentException("property '"
                    + pro.getPropertyName() + "' is already registered");
        }
        dynaProperties.put(pro.getPropertyName(), pro);
    }

    public synchronized DynaPro getByName(String name) {
        DynaPro result = dynaProperties.get(name);
        if (result == null) {
            throw new IllegalArgumentException("property '" + name + "' not found");
        }
        return result;
    }
}
