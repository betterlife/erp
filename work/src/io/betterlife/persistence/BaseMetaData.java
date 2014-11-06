package io.betterlife.persistence;

import io.betterlife.domains.BaseObject;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;
import java.beans.Transient;
import java.util.*;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/7/14
 */

@MappedSuperclass
public abstract class BaseMetaData {
    private static Map<String, Map<String, Class>> _fieldsMetaData = new HashMap<>();
    private static boolean hasMetaData = false;

    @Transient
    public Class getFieldMetaData(Class<? extends BaseObject> aClass, String fieldName) {
        Map<String, Class> meta = _fieldsMetaData.get(aClass.getName());
        return meta.get(fieldName);
    }

    private void setFieldMetaData(Class<? extends BaseObject> aClass, String fieldName, Class clazz) {
        Map<String, Class> meta = _fieldsMetaData.get(aClass.getName());
        if (null == meta) {
            meta = new HashMap<>(8);
            _fieldsMetaData.put(aClass.getName(), meta);
        }
        meta.put(fieldName, clazz);
    }

    @Transient
    public boolean hasMetaData() {
        return hasMetaData;
    }

    private void setHasMetaData(boolean hasMetaData) {
        BaseMetaData.hasMetaData = hasMetaData;
    }

    public void setAllFieldMetaData(EntityManager entityManager) {
        if (!hasMetaData()) {
            synchronized (this) {
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
            }
        }
    }
}
