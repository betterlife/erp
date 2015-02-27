package io.betterlife.framework.application.manager;

import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.meta.EntityMeta;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.util.BLStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */

public class MetaDataManager {
    private static final Logger logger = LogManager.getLogger(MetaDataManager.class.getName());
    private static MetaDataManager instance = new MetaDataManager();
    private static FieldMetaDataContainer fieldMetaDataContainer = new FieldMetaDataContainer();
    private static EntityMetaDataContainer entityMetaDataContainer = new EntityMetaDataContainer();
    private static RestMetaDataContainer restMetaDataContainer = new RestMetaDataContainer();

    private MetaDataManager() {
    }

    public EntityMeta getEntityMeta(Class<? extends BaseObject> aClass) {
        return entityMetaDataContainer.get(aClass.getSimpleName());
    }

    public FieldMeta getFieldMeta(Class<? extends BaseObject> aClass, String fieldName) {
        Map<String, FieldMeta> meta = fieldMetaDataContainer.get(aClass.getName());
        return meta.get(fieldName);
    }

    public Map<String, FieldMeta> getFieldsMeta(Class<? extends BaseObject> clazz) {
        return fieldMetaDataContainer.get(clazz.getName());
    }

    public BaseObject entityObjectFromType(String objectType) throws InstantiationException, IllegalAccessException {
        Class<? extends BaseObject> clazz = restMetaDataContainer.get(objectType);
        return clazz.newInstance();
    }

    public Map<String, FieldMeta> getMetaFromEntityType(String entityType) {
        final Class<? extends BaseObject> entityClass = restMetaDataContainer.get(BLStringUtils.capitalize(entityType));
        return MetaDataManager.getInstance().getFieldsMeta(entityClass);
    }

    public static MetaDataManager getInstance() {
        return instance;
    }

    public static void setInstance(MetaDataManager metaDataManager) {
        instance = metaDataManager;
    }

    public void setAllFieldMetaData() {
        EntityManager entityManager = SharedEntityManager.getInstance().getEntityManager();
        try {
            fieldMetaDataContainer.loadMeta(entityManager);
            entityMetaDataContainer.loadMeta(entityManager);
            restMetaDataContainer.loadMeta(entityManager);
        } finally {
            if (null != entityManager && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

}
