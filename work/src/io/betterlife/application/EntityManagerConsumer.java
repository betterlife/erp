package io.betterlife.application;

import io.betterlife.application.manager.SharedEntityManager;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 12/17/14
 */
public class EntityManagerConsumer {

    private SharedEntityManager sharedEntityManager = SharedEntityManager.getInstance();

    public void setSharedEntityManager(SharedEntityManager manager) {
        sharedEntityManager = manager;
    }

    public EntityManager newEntityManager() {
        return sharedEntityManager.getEntityManager();
    }

    public void closeEntityManager() {
        getSharedEntityManager().close();
    }

    public SharedEntityManager getSharedEntityManager(){
        return this.sharedEntityManager;
    }

}
