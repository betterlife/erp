package io.betterlife.util;

import io.betterlife.framework.domains.BaseObject;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import static org.junit.Assert.*;

/**
 * Author: Lawrence Liu
 * Date: 12/11/14
 */
public class EntityVerifyUtil {
    private static EntityVerifyUtil instance = new EntityVerifyUtil();

    public static EntityVerifyUtil getInstance() {
        return instance;
    }

    private EntityVerifyUtil() {
    }

    public void verifyQueryDefined(final Class<? extends BaseObject> clazz, final String queryName) {
        NamedQueries namedQueries = clazz.getAnnotation(NamedQueries.class);
        assertNotNull(namedQueries);
        NamedQuery[] queries = namedQueries.value();
        boolean found = false;
        for (NamedQuery query : queries) {
            if (query.name().equals(queryName)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

}
