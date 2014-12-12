package io.betterlife.application;

import io.betterlife.domains.BaseObject;
import io.betterlife.persistence.MetaDataManager;
import io.betterlife.util.BLStringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class ServiceEntityManager {
    private boolean serviceEntityRegistered = false;
    private final Map<String, Class<? extends BaseObject>> classes = new HashMap<>();
    private static ServiceEntityManager ourInstance = new ServiceEntityManager();

    public static ServiceEntityManager getInstance() {
        return ourInstance;
    }

    private ServiceEntityManager() {}

    public Class<? extends BaseObject> getServiceEntityClass(String name) {
        if (!serviceEntityRegistered) {
            ApplicationConfig.registerEntities();
            serviceEntityRegistered = true;
        }
        return classes.get(name);
    }

    public synchronized void registerServiceEntity(String name, Class<? extends BaseObject> clazz) {
        classes.put(name, clazz);
    }

    public BaseObject entityObjectFromType(String objectType) throws InstantiationException, IllegalAccessException {
        Class<? extends BaseObject> clazz = ServiceEntityManager.getInstance().getServiceEntityClass(objectType);
        return clazz.newInstance();
    }

    public Map<String, Class> getMetaFromEntityType(EntityManager entityManager, String entityType) {
        entityType = BLStringUtils.uncapitalize(entityType);
        final Class<? extends BaseObject> entityClass = ServiceEntityManager.getInstance().getServiceEntityClass(entityType);
        return MetaDataManager.getInstance().getMetaDataOfClass(entityManager, entityClass);
    }

}
