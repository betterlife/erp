package io.betterlife.persistence;

import io.betterlife.application.manager.SharedEntityManager;
import io.betterlife.domains.BaseObject;
import io.betterlife.util.jpa.OpenJPAUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.openjpa.persistence.OpenJPAQuery;

import javax.persistence.EntityManager;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
public class BaseOperator {
    private static BaseOperator instance = new BaseOperator();
    private OpenJPAUtil openJPAUtil;

    private static final Logger logger = LogManager.getLogger(BaseOperator.class.getName());

    public void setOpenJPAUtil(OpenJPAUtil util) {
        this.openJPAUtil = util;
    }

    public OpenJPAUtil getOpenJPAUtil() {
        if (null == openJPAUtil) {
            this.openJPAUtil = OpenJPAUtil.getInstance();
        }
        return this.openJPAUtil;
    }

    public static BaseOperator getInstance() {
        return instance;
    }

    private BaseOperator() {}

    public <T> T getBaseObjectById(long id, String queryName) {
        T obj = null;
        OpenJPAQuery q = getOpenJPAUtil().getOpenJPAQuery(queryName);
        q.setParameter("id", id);
        return getOpenJPAUtil().getSingleResult(q);
    }

    public <T> void save(T obj) {
        if (obj instanceof BaseObject) {
            final BaseObject bo = (BaseObject) obj;
            if (bo.getId() == 0) {
                bo.setCreateDate(new Date());
            }
            bo.setLastModifyDate(new Date());
            saveBaseObjectWithTransaction(obj);
        }
    }

    public <T> void saveBaseObjectWithTransaction(T obj) {
        EntityManager em = SharedEntityManager.getInstance().getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(obj);
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("Saving Object: [%s]", obj));
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public <T> T getBaseObject(EntityManager em, String queryName, Map<String, ?> queryParams) {
        OpenJPAQuery q = getOpenJPAUtil().getOpenJPAQuery(queryName);
        for (Map.Entry<String, ?> entry : queryParams.entrySet()){
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return getOpenJPAUtil().getSingleResult(q);
    }

    public <T> List<T> getBaseObjects(String queryName) {
        List<T> result = new ArrayList<>();
        OpenJPAQuery q = getOpenJPAUtil().getOpenJPAQuery(queryName);
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
