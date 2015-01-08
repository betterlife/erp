package io.betterlife.application.manager;

import io.betterlife.application.config.ApplicationConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/23/14
 */
public class SharedEntityManager {
    private static final Logger logger = LogManager.getLogger(SharedEntityManager.class.getName());
    private static SharedEntityManager instance = new SharedEntityManager();
    private EntityManager manager;
    private EntityManagerFactory factory;
    private static volatile boolean initialized = false;
    private static final Boolean lock = true;

    public static SharedEntityManager getInstance() {
        return instance;
    }

    private SharedEntityManager() {}

    public synchronized void initEntityManager() {
        if (initialized) {
            return;
        }
        synchronized(lock) {
            if(initialized){
                return;
            }
            initialized = true;
            try {
                instance.factory = Persistence.createEntityManagerFactory(ApplicationConfig.PersistenceUnitName);
                instance.manager = instance.factory.createEntityManager();
                instance.manager.setFlushMode(FlushModeType.COMMIT);
            } catch (Throwable t) {
                logger.error("Error init Entity manager", t);
            }
        }
    }

    public EntityManager getEntityManager() {
        return manager;
    }
}
