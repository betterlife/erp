package io.betterlife.application.manager;

import io.betterlife.application.EntityManagerConsumer;
import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.domains.BaseObject;
import io.betterlife.util.BLStringUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

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
    private Map<String, Map<String, FieldMeta>> _fieldsMetaData = new HashMap<>();
    private boolean hasMetaData = false;
    private static MetaDataManager instance = new MetaDataManager();

    private MetaDataManager(){}

    public FieldMeta getFieldMetaData(Class<? extends BaseObject> aClass, String fieldName) {
        Map<String, FieldMeta> meta = _fieldsMetaData.get(aClass.getName());
        return meta.get(fieldName);
    }

    public Map<String, FieldMeta> getMetaDataOfClass(Class<? extends BaseObject> clazz) {
        if (!hasMetaData() || null == _fieldsMetaData || _fieldsMetaData.size() == 0) {
            setAllFieldMetaData();
        }
        return _fieldsMetaData.get(clazz.getName());
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
                    EntityManager entityManager = newEntityManager();
                    Metamodel metaModel = entityManager.getMetamodel();
                    Set<ManagedType<?>> managedTypes = metaModel.getManagedTypes();
                    for (ManagedType managedType : managedTypes) {
                        @SuppressWarnings("unchecked")
                        Class<? extends BaseObject> clazz = managedType.getJavaType();
                        List<Class<?>> ignoreClasses = ClassUtils.getAllSuperclasses(BaseObject.class);
                        if (clazz.equals(BaseObject.class) || (ignoreClasses != null && ignoreClasses.contains(clazz))) {
                            continue;
                        }
                        addManagedAttributes(managedType, clazz);
                        addTransientAttributes(clazz);
                    }
                    setHasMetaData(true);
                } finally {
                    closeEntityManager();
                }
            }
        }
    }

    private void setFieldMetaData(Class<? extends BaseObject> clazz, FieldMeta fieldMeta) {
        Map<String, FieldMeta> classMeta = _fieldsMetaData.get(clazz.getName());
        if (null == classMeta) {
            classMeta = new HashMap<>(8);
            _fieldsMetaData.put(clazz.getName(), classMeta);
        }
        classMeta.put(fieldMeta.getName(), fieldMeta);
    }

    private void addManagedAttributes(ManagedType managedType, Class<? extends BaseObject> clazz) {
        @SuppressWarnings("unchecked")
        Set<Attribute> attributeSet = managedType.getAttributes();
        for (Attribute attr : attributeSet) {
            Class attrJavaType = attr.getJavaType();
            String name = attr.getName();
            FieldMeta meta = new FieldMeta();
            meta.setName(name);
            meta.setType(attrJavaType);
            meta.setEditable(true);
            setFieldMetaData(clazz, meta);
        }
    }

    private void addTransientAttributes(Class<? extends BaseObject> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers()) && m.getName().startsWith("get")) {
                Annotation annotation = m.getAnnotation(Transient.class);
                if (annotation != null) {
                    String attrName = BLStringUtils.uncapitalize(m.getName().substring(3));
                    FieldMeta meta = new FieldMeta();
                    meta.setName(attrName);
                    meta.setType(m.getReturnType());
                    meta.setEditable(false);
                    setFieldMetaData(clazz, meta);
                }
            }
        }
    }
}
