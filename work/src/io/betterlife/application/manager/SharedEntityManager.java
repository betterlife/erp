package io.betterlife.application.manager;

import io.betterlife.application.config.ApplicationConfig;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

    public synchronized void initEntityManager() {
        instance.factory = Persistence.createEntityManagerFactory(ApplicationConfig.PersistenceUnitName);
        instance.manager = instance.factory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return manager;
    }
}
