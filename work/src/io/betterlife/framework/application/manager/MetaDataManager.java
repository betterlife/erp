package io.betterlife.framework.application.manager;

import io.betterlife.framework.annotation.Triggers;
import io.betterlife.framework.application.EntityManagerConsumer;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.trigger.EntityTrigger;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.EntityUtils;
import io.betterlife.framework.condition.FalseCondition;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */

public class MetaDataManager extends EntityManagerConsumer {
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


    public void setAllFieldMetaData() {
        fieldMetaDataContainer.loadMeta();
        entityMetaDataContainer.loadMeta();
        restMetaDataContainer.loadMeta();
    }

}
