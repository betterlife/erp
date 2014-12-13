package io.betterlife.domains;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.MetaDataManager;
import io.betterlife.persistence.NamedQueryRules;
import io.betterlife.util.EntityUtils;
import io.betterlife.util.converter.Converter;
import io.betterlife.util.converter.ConverterFactory;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@MappedSuperclass
public abstract class BaseObject {

    private static final Logger logger = LogManager.getLogger(BaseObject.class.getName());

    private Map<String, Object> _map = new HashMap<>();

    private long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Transient
    public <T> T getValue(String fieldName) {
        @SuppressWarnings("unchecked")
        T result = (T) _map.get(fieldName);
        return result;
    }

    public <T> void setValue(String fieldName, T value) {
        _map.put(fieldName, value);
    }

    public void setLastModifyDate(Date lastModifyDate) {
        setValue("lastModifyDate", lastModifyDate);
    }

    public Date getLastModifyDate() {
        return getValue("lastModifyDate");
    }

    public void setLastModify(User lastModify) {
        setValue("lastModify", lastModify);
    }

    @ManyToOne
    public User getLastModify() {
        return getValue("lastModify");
    }

    public void setCreateDate(Date createDate) {
        setValue("createDate", createDate);
    }

    public Date getCreateDate() {
        return getValue("createDate");
    }

    public void setCreator(User creator) {
        setValue("creator", creator);
    }

    @ManyToOne
    public User getCreator() {
        return getValue("creator");
    }

    public void setValues(EntityManager entityManager, Map<String, Object> parameters) {
        MetaDataManager.getInstance().setAllFieldMetaData(entityManager);
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            Class clazz = MetaDataManager.getInstance().getFieldMetaData(this.getClass(), key);
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("Setting [%s, %s] to type [%s]", value, value.getClass().getName(), clazz));
            }
            if (EntityUtils.getInstance().isBaseObject(clazz)) {
                final String idQueryForEntity = NamedQueryRules.getInstance().getIdQueryForEntity(clazz.getSimpleName());
                BaseObject baseObj = BaseOperator.getInstance().getBaseObjectById(
                    entityManager, Long.parseLong((String) value),
                    idQueryForEntity
                );
                if (null != baseObj) {
                    setValue(key, baseObj);
                }
            } else if (ClassUtils.isAssignable(value.getClass(), clazz)) {
                setValue(key, value);
            } else {
                final Converter converter = ConverterFactory.getInstance().getConverter(value.getClass(), clazz);
                Object convertedValue = null;
                try {
                    if (converter != null) {
                        convertedValue = converter.convert(value);
                        setValue(key, convertedValue);
                    } else {
                        logger.warn(String.format("Failed to get converter[%s->%s] for value[%s]", value.getClass(), clazz, value));
                    }
                } catch (ParseException e) {
                    logger.error(String.format(
                                     "Failed to convert[%s,%s] to type [%s]", value,
                                     value.getClass().getName(), clazz.getName()
                                 ), e);
                }
            }
        }
    }

}
