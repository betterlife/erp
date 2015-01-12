package io.betterlife.util.jpa;

import io.betterlife.application.EntityManagerConsumer;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */
public class JPAUtil {
    private static JPAUtil ourInstance = new JPAUtil();

    private EntityManager entityManager;

    public static JPAUtil getInstance() {
        if (null == ourInstance) {
            ourInstance = new JPAUtil();
        }
        return ourInstance;
    }

    private JPAUtil() {
    }

    public Query getQuery(String queryName) {
        return entityManager.createNamedQuery(queryName);
    }

    public <T> T getSingleResult(Query q) {
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
