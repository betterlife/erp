package io.betterlife.rest;

import io.betterlife.application.ApplicationConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class ServiceEntityManager {
    private static boolean serviceEntityRegistered = false;
    private static final Map<String, Class> classes = new HashMap<>();
    private static ServiceEntityManager ourInstance = new ServiceEntityManager();

    public static ServiceEntityManager getInstance() {
        return ourInstance;
    }

    private ServiceEntityManager() {}

    public Class getServiceEntity(String name) {
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
        Class clazz = ServiceEntityManager.getInstance().getServiceEntity(objectType);
        return clazz.newInstance();
    }

}
