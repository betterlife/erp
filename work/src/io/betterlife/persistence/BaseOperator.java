package io.betterlife.persistence;

import io.betterlife.domains.BaseObject;
import org.apache.openjpa.persistence.OpenJPAPersistence;
import org.apache.openjpa.persistence.OpenJPAQuery;

import javax.persistence.EntityManager;
import java.util.*;

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
            @SuppressWarnings("unchecked")
            Iterator<T> it = (Iterator<T>) coll.iterator();
            if (it.hasNext()) {
                obj = it.next();
            }
        }
        return obj;
    }

    public <T> void save(EntityManager em, T obj) {
        if (obj instanceof BaseObject) {
            final BaseObject bo = (BaseObject) obj;
            if (bo.getId() == 0) {
                bo.setCreateDate(new Date());
            }
            bo.setLastModifyDate(new Date());
            em.persist(obj);
        }
    }

    public <T> List<T> getBaseObjects(EntityManager em, String queryName) {
        List<T> result = new ArrayList<>();
        OpenJPAQuery q = OpenJPAPersistence.cast(em.createNamedQuery(queryName));
        assert q != null;
        Collection coll = q.getResultList();
        if (coll != null) {
            @SuppressWarnings("unchecked")
            Iterator<T> it = (Iterator<T>) coll.iterator();
            if (it.hasNext()) {
                result.add(it.next());
            }
        }
        return result;
    }
}
