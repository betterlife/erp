package io.betterlife.application;

import io.betterlife.application.manager.SharedEntityManager;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 12/17/14
 */
public class EntityManagerConsumer {
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if (null == entityManager) {
            entityManager = SharedEntityManager.getInstance().getEntityManager();
        }
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
