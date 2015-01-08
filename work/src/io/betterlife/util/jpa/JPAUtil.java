package io.betterlife.util.jpa;

import io.betterlife.application.EntityManagerConsumer;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */
public class JPAUtil extends EntityManagerConsumer {
    private static JPAUtil ourInstance = new JPAUtil();

    public static JPAUtil getInstance() {
        return ourInstance;
    }

    private JPAUtil() {
    }

    public Query getQuery(String queryName) {
        return getEntityManager().createNamedQuery(queryName);
    }

    public <T> T getSingleResult(Query q) {
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
    }
}
