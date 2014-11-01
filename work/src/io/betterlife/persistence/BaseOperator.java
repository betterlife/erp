package io.betterlife.persistence;

import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class BaseOperator {
    private static BaseOperator ourInstance = new BaseOperator();

    public static BaseOperator getInstance() {
        return ourInstance;
    }

    private BaseOperator() {
    }

    public <T> T getBaseObject(EntityManager em, long id, String queryName) {
        T obj = null;
        OpenJPAQuery q = OpenJPAPersistence.cast(em.createNamedQuery(queryName));
        assert q != null;
        q.setParameter("id", id);
        Collection coll = q.getResultList();
        if (coll != null) {
            Iterator it = coll.iterator();
            if (it.hasNext()) {
                obj = (T) it.next();
            }
        }
        return obj;
    }
}
