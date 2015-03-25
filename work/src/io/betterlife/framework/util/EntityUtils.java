package io.betterlife.framework.util;

import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 11/22/14
 */
public class EntityUtils {

    private static final Logger logger = LogManager.getLogger(EntityUtils.class.getName());

    private static EntityUtils instance = new EntityUtils();

    public static EntityUtils getInstance() {
        return instance;
    }

    public static void setInstance(EntityUtils entityUtils) {
        instance = entityUtils;
    }

    private EntityUtils() {
    }

    public void mapToBaseObject(BaseObject obj, Map<String, Object> parameters) {
        if (null != obj) {
            obj.setValues(parameters);
        } else {
            throw new RuntimeException("Trying to setting parameters to null object: " + String.valueOf(parameters));
        }
    }

    public boolean isBaseObject(Class clazz) {
        return null != clazz && ClassUtils.isAssignable(clazz, BaseObject.class);
    }

    public String getRepresentFieldWithDot(FieldMeta fieldMeta) {
        if (null != fieldMeta) {
            if (isBaseObject(fieldMeta.getType())) {
                return fieldMeta.getName() + "." + fieldMeta.getRepresentField();
            }
            return fieldMeta.getName();
        } else {
            throw new RuntimeException("Field Meta is null when invoke getRepresentFieldWithDot");
        }
    }

    public boolean isIdField(String key) {
        return "id".equals(key);
    }

    public LinkedHashMap<String, FieldMeta> sortEntityMetaByDisplayRank(Map<String, FieldMeta> meta) {
        LinkedHashMap<String, FieldMeta> sortedResult = new LinkedHashMap<>();
        String[] fieldNamesInOrder = new String[100];
        for(Map.Entry<String, FieldMeta> entry : meta.entrySet()) {
            String fieldName = entry.getKey();
            int rank = entry.getValue().getDisplayRank();
            while (fieldNamesInOrder[rank] != null) {
                rank = rank + 1;
            }
            fieldNamesInOrder[rank] = fieldName;
        }
        for (String fieldName : fieldNamesInOrder) {
            if (fieldName != null) {
                sortedResult.put(fieldName, meta.get(fieldName));
            }
        }
        return sortedResult;
    }

    public boolean isBooleanField(FieldMeta fieldMeta) {
        final Class type = fieldMeta.getType();
        return Boolean.class.equals(type) || "boolean".equals(type.getSimpleName());
    }

    public String getNgModelNameForField(String key) {
        return "entity." + key;
    }

    public boolean isEnumField(FieldMeta fieldMeta) {
        final Class type = fieldMeta.getType();
        return null != type && type.isEnum();
    }

    public boolean isDateField(FieldMeta fieldMeta) {
        return ClassUtils.isAssignable(Date.class, fieldMeta.getType());
    }

    public boolean isDecimalField(FieldMeta fieldMeta) {
        return ClassUtils.isAssignable(BigDecimal.class, fieldMeta.getType())
            || ClassUtils.isAssignable(Float.class, fieldMeta.getType(), true)
            || ClassUtils.isAssignable(Double.class, fieldMeta.getType(), true)
            || ClassUtils.isAssignable(Integer.class, fieldMeta.getType(), true);
    }

    public boolean isUserField(FieldMeta fieldMeta) {
        return ClassUtils.isAssignable(User.class, fieldMeta.getType());
    }

    public boolean isRemarkField(FieldMeta fieldMeta) {
        return "Remark".equalsIgnoreCase(fieldMeta.getName());
    }
}
