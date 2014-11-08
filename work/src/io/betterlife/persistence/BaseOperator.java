package io.betterlife.persistence;

import io.betterlife.domains.BaseObject;
import io.betterlife.util.jpa.OpenJPAUtil;
import org.apache.openjpa.persistence.OpenJPAQuery;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
public class BaseOperator {
    private static BaseOperator instance = new BaseOperator();
    private OpenJPAUtil openJPAUtil = OpenJPAUtil.getInstance();

    public static BaseOperator getInstance() {
        return instance;
    }

    private BaseOperator() {
    }

    public <T> T getBaseObjectById(EntityManager em, long id, String queryName) {
        T obj = null;
        OpenJPAQuery q = openJPAUtil.getOpenJPAQuery(em, queryName);
        q.setParameter("id", id);
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
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

    public <T> T getBaseObject(EntityManager em, String queryName, Map<String, ?> queryParams) {
        OpenJPAQuery q = openJPAUtil.getOpenJPAQuery(em, queryName);
        for (String key : queryParams.keySet()){
            q.setParameter(key, queryParams.get(key));
        }
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
    }

    public <T> List<T> getBaseObjects(EntityManager em, String queryName) {
        List<T> result = new ArrayList<>();
        OpenJPAQuery q = openJPAUtil.getOpenJPAQuery(em, queryName);
        Collection coll = q.getResultList();
        if (coll != null) {
            @SuppressWarnings("unchecked")
            Iterator<T> it = (Iterator<T>) coll.iterator();
            while (it.hasNext()) {
                result.add(it.next());
            }
        }
        return result;
    }
}
