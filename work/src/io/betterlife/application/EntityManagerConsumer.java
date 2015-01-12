package io.betterlife.application;

import io.betterlife.application.manager.SharedEntityManager;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 12/17/14
 */
public class EntityManagerConsumer {
    private EntityManager dummyEntityManager;

    public EntityManager newEntityManager() {
        if (null != dummyEntityManager) {
            return dummyEntityManager;
        }
        return SharedEntityManager.getInstance().getEntityManager();
    }

    public void closeEntityManager() {
        SharedEntityManager.getInstance().close();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.dummyEntityManager = entityManager;
    }

}
