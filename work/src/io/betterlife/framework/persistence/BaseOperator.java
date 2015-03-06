package io.betterlife.framework.persistence;

import io.betterlife.framework.application.manager.SharedEntityManager;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.trigger.Invoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
public class BaseOperator {
    private static final Logger logger = LogManager.getLogger(BaseOperator.class.getName());
    private static BaseOperator instance = new BaseOperator();

    public static final int UPDATE_OPERA = 1;
    public static final int CREATE_OPERA = 0;
    private EntityManager entityManager;

    public static BaseOperator getInstance() {
        return instance;
    }

    public static void setInstance(BaseOperator operator) {
        instance = operator;
    }

    private BaseOperator() {
    }

    public <T> T getBaseObjectById(long id, String queryName) {
        Query q = getQuery(queryName);
        q.setParameter("id", id);
        return getSingleResult(q);
    }

    public <T extends BaseObject> void save(T obj, int operation) {
        if (obj != null) {
            final Date date = new Date();
            if (obj.getId() == 0) {
                obj.setCreateDate(date);
                obj.setActive(true);
            }
            obj.setLastModifyDate(date);
            saveBaseObjectWithTransaction(obj, operation);
        }
    }

    public <T extends BaseObject> void saveBaseObjectWithTransaction(T obj, int operation) {
        try {
            getEntityManager().getTransaction().begin();
            if (UPDATE_OPERA == operation) {
                obj = getEntityManager().merge(obj);
            } else {
                getEntityManager().persist(obj);
            }
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("Saving Object: [%s]", obj));
            }
            BaseObject original = null;
            if (operation == UPDATE_OPERA) {
                 original = getOriginalBaseObject(obj.getId(), obj.getClass());
            }
            Invoker.invokeSaveTrigger(getEntityManager(), obj, original);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            logger.error("Error to save object " + obj);
            logger.error(e);
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
        } finally {
            instance.entityManager = null;
        }
    }

    private <T extends BaseObject> T getOriginalBaseObject(long id, Class<? extends BaseObject> aClass) {
        String queryName = NamedQueryRules.getInstance().getIdQueryForEntity(aClass.getSimpleName());
        EntityManager entityManager = SharedEntityManager.getInstance().getEntityManager();
        Query q = entityManager.createNamedQuery(queryName);
        q.setHint(QueryHints.REFRESH, HintValues.TRUE);
        q.setParameter("id", id);
        return (T) q.getSingleResult();
    }

    public <T extends BaseObject> T getBaseObject(String queryName, Map<String, ?> queryParams) {
        Query q = getQuery(queryName);
        for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }
        return getSingleResult(q);
    }

    public <T extends BaseObject> List<T> getBaseObjects(String queryName, Map<String, String> queryParams) {
        List<T> result = new ArrayList<>();
        Query q = getQuery(queryName);
        if (null != queryParams && 0 != queryParams.size()) {
            for (Map.Entry<String, ?> entry : queryParams.entrySet()) {
                q.setParameter(entry.getKey(), "%" + entry.getValue() + "%");
            }
        }
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

    public Query getQuery(String queryName) {
        return getEntityManager().createNamedQuery(queryName);
    }

    private EntityManager getEntityManager() {
        if (null == instance.entityManager) {
            instance.entityManager = SharedEntityManager.getInstance().getEntityManager();
        }
        return instance.entityManager;
    }

    public <T> T getSingleResult(Query q) {
        @SuppressWarnings("unchecked")
        final T singleResult = (T) q.getSingleResult();
        return singleResult;
    }

    public long getObjectCount(Class<? extends BaseObject> clazz) {
        final String tableName = clazz.getSimpleName().equals("User")? "UserEntity" : clazz.getSimpleName();
        Query query = getEntityManager().createQuery("SELECT COUNT(p.id) from " + tableName + " p where p.active = :active", clazz);
        query.setParameter("active", true);
        return getSingleResult(query);
    }
}
