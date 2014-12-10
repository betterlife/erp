package io.betterlife.util;

import io.betterlife.application.ApplicationConfig;
import io.betterlife.application.ServiceEntityManager;
import io.betterlife.domains.BaseObject;
import io.betterlife.rest.Form;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
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

    private EntityUtils() {
    }

    public void mapToBaseObject(EntityManager entityManager, Object obj, Map<String, String> parameters) {
        if (obj instanceof BaseObject) {
            ((BaseObject) obj).setValues(entityManager, parameters);
        }
    }

    public boolean isBaseObject(Class clazz) {
        return ClassUtils.isAssignable(clazz, BaseObject.class);
    }

    public String getRepresentFieldWithDot(String entityType, String field) {
        try {
            field = field  + "." + getRepresentField(entityType, field);;
        } catch (Exception e) {
            logger.warn(String.format("Failed to get represent field for field[%s], class[%s]", field, entityType));
        }
        return field;
    }

    public String getRepresentField(String entityType, String field) {
        Class entityClass = ServiceEntityManager.getInstance().getServiceEntityClass(BLStringUtils.uncapitalize(entityType));
        final String methodName = "get" + BLStringUtils.capitalize(field);
        try {
            Method method = entityClass.getDeclaredMethod(methodName);
            if (null != method) {
                Form form = method.getAnnotation(Form.class);
                return (null == form) ? ApplicationConfig.getDefaultRepresentField() : form.RepresentField();
            }
        } catch (NoSuchMethodException e) {
            logger.warn("Failed to get method definition for " + methodName);
        }
        return ApplicationConfig.getDefaultRepresentField();
    }
}
