package io.betterlife.util.jpa;

import io.betterlife.application.EntityManagerConsumer;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */
public class OpenJPAUtil extends EntityManagerConsumer {
    private static OpenJPAUtil ourInstance = new OpenJPAUtil();

    public static OpenJPAUtil getInstance() {
        return ourInstance;
    }

    private OpenJPAUtil() {
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
