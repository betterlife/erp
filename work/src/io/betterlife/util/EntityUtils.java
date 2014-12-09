package io.betterlife.util;

import io.betterlife.domains.BaseObject;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class EntityUtils {
    private static EntityUtils instance = new EntityUtils();

    public static EntityUtils getInstance() {
        return instance;
    }

    private EntityUtils() {
    }

    public void mapToBaseObject(EntityManager entityManager, Object obj, Map<String, String> parameters) {
        if (obj instanceof BaseObject) {
            ((BaseObject) obj).setValues(entityManager, parameters);
        }
    }


    public boolean isBaseObject(Class clazz) {
        return ClassUtils.isAssignable(clazz, BaseObject.class);
    }
}
