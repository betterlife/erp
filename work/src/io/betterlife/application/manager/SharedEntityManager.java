package io.betterlife.application.manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/23/14
 */
public class SharedEntityManager {

    private static SharedEntityManager instance = new SharedEntityManager();
    private EntityManager manager;

    private EntityManagerFactory factory;

    public static SharedEntityManager getInstance() {
        return instance;
    }

    private SharedEntityManager() {}

    public synchronized void setFactory(EntityManagerFactory factory) {
        instance.factory = factory;
        instance.manager = getFactory().createEntityManager();
    }

    public EntityManagerFactory getFactory() {
        return instance.factory;
    }

    public EntityManager getEntityManager() {
        return manager;
    }
}
