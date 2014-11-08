package io.betterlife.util.jpa;

import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */
public class OpenJPAUtil {
    private static OpenJPAUtil ourInstance = new OpenJPAUtil();

    public static OpenJPAUtil getInstance() {
        return ourInstance;
    }

    private OpenJPAUtil() {
    }

    public OpenJPAQuery getOpenJPAQuery(EntityManager em, String queryName) {
        OpenJPAQuery q = OpenJPAPersistence.cast(em.createNamedQuery(queryName));
        assert q != null;
        return q;
    }
}
