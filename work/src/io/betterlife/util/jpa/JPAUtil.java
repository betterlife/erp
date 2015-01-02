package io.betterlife.util.jpa;

import io.betterlife.application.EntityManagerConsumer;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

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

    public OpenJPAQuery getOpenJPAQuery(String queryName) {
        return OpenJPAPersistence.cast(getEntityManager().createNamedQuery(queryName));
    }

    public <T> T getSingleResult(OpenJPAQuery q) {
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
    }
}
