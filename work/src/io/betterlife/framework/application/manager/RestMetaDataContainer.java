package io.betterlife.framework.application.manager;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.util.BLStringUtils;

import javax.persistence.metamodel.ManagedType;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 */
public class RestMetaDataContainer extends MetaDataContainer {

    private final Map<String, Class<? extends BaseObject>> classes = new HashMap<>();

    @Override
    public void createMeta(ManagedType managedType, Object... additionalParams) throws Exception {
        Class<? extends BaseObject> clazz = (Class<? extends BaseObject>) additionalParams[0];
        classes.put(BLStringUtils.capitalize(clazz.getSimpleName()), clazz);
    }

    @Override
    public Class<? extends BaseObject> get(String name) {
        return classes.get(name);
    }
}
