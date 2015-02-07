package io.betterlife.framework.application.manager;

import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.condition.FalseCondition;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.util.BLStringUtils;
import io.betterlife.framework.util.EntityUtils;

import javax.persistence.Transient;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 */
public class FieldMetaDataContainer extends MetaDataContainer {

    private Map<String, Map<String, FieldMeta>> _fieldsMetaData = new HashMap<>();

    @Override
    public void createMeta(ManagedType managedType, Object... additionalParams) throws Exception {
        Class<? extends BaseObject> clazz = (Class<? extends BaseObject>) additionalParams[0];
        addManagedAttributes(managedType, clazz);
        addTransientAttributes(clazz);
    }

    @Override
    public Map<String, FieldMeta> get(String name) {
        return _fieldsMetaData.get(name);
    }

    private void addManagedAttributes(ManagedType managedType, Class<? extends BaseObject> clazz)
        throws NoSuchMethodException {
        @SuppressWarnings("unchecked")
        Set<Attribute> attributeSet = managedType.getAttributes();
        for (Attribute attr : attributeSet) {
            Class attrJavaType = attr.getJavaType();
            String name = attr.getName();
            FieldMeta meta = new FieldMeta();
            meta.setName(name);
            meta.setType(attrJavaType);
            readFormAnnotation(clazz, name, meta);
            setFieldMetaData(clazz, meta);
        }
    }

    private void addTransientAttributes(Class<? extends BaseObject> clazz) throws NoSuchMethodException {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers()) && m.getName().startsWith("get")) {
                Annotation annotation = m.getAnnotation(Transient.class);
                if (annotation != null) {
                    String attrName = BLStringUtils.uncapitalize(m.getName().substring(3));
                    FieldMeta meta = new FieldMeta();
                    meta.setName(attrName);
                    meta.setType(m.getReturnType());
                    readFormAnnotation(clazz, attrName, meta);
                    meta.setEditableCondition(FalseCondition.class);
                    setFieldMetaData(clazz, meta);
                }
            }
        }
    }

    private void readFormAnnotation(Class<? extends BaseObject> clazz, String name, FieldMeta meta) throws NoSuchMethodException {
        Method m = clazz.getMethod("get" + BLStringUtils.capitalize(name));
        if (m != null) {
            FormField form = m.getAnnotation(FormField.class);
            if (null != form) {
                meta.setDisplayRank(form.DisplayRank());
                meta.setVisibleCondition(form.Visible());
                meta.setRepresentField(form.RepresentField());
                meta.setConverter(form.Converter());
                meta.setEditableCondition(form.Editable());
                if (EntityUtils.getInstance().isBooleanField(meta)) {
                    meta.setTrueLabel(form.TrueLabel());
                    meta.setFalseLabel(form.FalseLabel());
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

}
