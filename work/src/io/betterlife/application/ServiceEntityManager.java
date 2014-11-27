package io.betterlife.application;

import io.betterlife.persistence.MetaDataManager;
import io.betterlife.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class ServiceEntityManager {
    private boolean serviceEntityRegistered = false;
    private final Map<String, Class> classes = new HashMap<>();
    private static ServiceEntityManager ourInstance = new ServiceEntityManager();

    public static ServiceEntityManager getInstance() {
        return ourInstance;
    }

    private ServiceEntityManager() {}

    public Class getServiceEntityClass(String name) {
        if (!serviceEntityRegistered) {
            ApplicationConfig.registerEntities();
            serviceEntityRegistered = true;
        }
        return classes.get(name);
    }

    public synchronized void registerServiceEntity(String name, Class clazz) {
        classes.put(name, clazz);
    }

    public Object entityObjectFromType(String objectType) throws InstantiationException, IllegalAccessException {
        Class clazz = ServiceEntityManager.getInstance().getServiceEntityClass(objectType);
        return clazz.newInstance();
    }

    public Map<String, Class> getMetaFromEntityType(EntityManager entityManager, String entityType) {
        entityType = StringUtils.uncapitalize(entityType);
        final Class entityClass = ServiceEntityManager.getInstance().getServiceEntityClass(entityType);
        return MetaDataManager.getInstance().getMetaDataOfClass(entityManager, entityClass);
    }

}
