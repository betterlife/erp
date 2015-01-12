package io.betterlife.application.manager;

import io.betterlife.application.EntityManagerConsumer;
import io.betterlife.domains.BaseObject;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */

public class MetaDataManager extends EntityManagerConsumer {
    private Map<String, Map<String, Class>> _fieldsMetaData = new HashMap<>();
    private boolean hasMetaData = false;
    private static MetaDataManager instance = new MetaDataManager();
    private EntityManager entityManager;

    private MetaDataManager(){}

    public Class getFieldMetaData(Class<? extends BaseObject> aClass, String fieldName) {
        Map<String, Class> meta = _fieldsMetaData.get(aClass.getName());
        return meta.get(fieldName);
    }

    public Map<String, Class> getMetaDataOfClass(Class<? extends BaseObject> clazz) {
        if (!hasMetaData() || null == _fieldsMetaData || _fieldsMetaData.size() == 0) {
            setAllFieldMetaData();
        }
        return _fieldsMetaData.get(clazz.getName());
    }

    private void setFieldMetaData(Class<? extends BaseObject> aClass, String fieldName, Class clazz) {
        Map<String, Class> meta = _fieldsMetaData.get(aClass.getName());
        if (null == meta) {
            meta = new HashMap<>(8);
            _fieldsMetaData.put(aClass.getName(), meta);
        }
        meta.put(fieldName, clazz);
    }

    public static MetaDataManager getInstance() {
        return instance;
    }

    public boolean hasMetaData() {
        return hasMetaData;
    }

    private void setHasMetaData(boolean hasMetaData) {
        this.hasMetaData = hasMetaData;
    }

    public void setAllFieldMetaData() {
        if (!hasMetaData()) {
            synchronized (this) {
                if (hasMetaData()) {
                    return;
                }
                try {
                    entityManager = newEntityManager();
                    Metamodel metaModel = entityManager.getMetamodel();
                    Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
                    for (ManagedType managedType : managedTypes) {
                        @SuppressWarnings("unchecked")
                        Class<? extends BaseObject> clazz = managedType.getJavaType();
                        List<Class<?>> ignoreClasses = ClassUtils.getAllSuperclasses(BaseObject.class);
                        if (clazz.equals(BaseObject.class) || (ignoreClasses != null && ignoreClasses.contains(clazz))) {
                            continue;
                        }
                        @SuppressWarnings("unchecked")
                        Set<Attribute> attributeSet = managedType.getAttributes();
                        for (Attribute attr : attributeSet) {
                            Class attrJavaType = attr.getJavaType();
                            String name = attr.getName();
                            setFieldMetaData(clazz, name, attrJavaType);
                        }
                    }
                    setHasMetaData(true);
                } finally {
                    close(entityManager);
                }
            }
        }
    }
}
