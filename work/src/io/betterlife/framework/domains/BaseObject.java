package io.betterlife.framework.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.betterlife.framework.application.config.ApplicationConfig;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.domains.security.User;
import io.betterlife.framework.persistence.BaseOperator;
import io.betterlife.framework.persistence.NamedQueryRules;
import io.betterlife.framework.annotation.FormField;
import io.betterlife.framework.util.EntityUtils;
import io.betterlife.framework.condition.CommonFieldsVisibleCondition;
import io.betterlife.framework.converter.Converter;
import io.betterlife.framework.converter.ConverterFactory;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(lawrence@betterlife.io)
 * Date: 10/31/14
 */
@MappedSuperclass
@JsonIgnoreProperties({"creator", "lastModify", "active"})
public class BaseObject {

    private static final Logger logger = LogManager.getLogger(BaseObject.class.getName());

    private Map<String, Object> _map = new HashMap<>();

    private long id;

    private MetaDataManager metaDataManager = MetaDataManager.getInstance();

    public void setMetaDataManager(MetaDataManager manager) {
        this.metaDataManager = manager;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FormField(DisplayRank = 0, Visible = CommonFieldsVisibleCondition.class)
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

    @Temporal(value=TemporalType.DATE)
    @FormField(DisplayRank = 99, Visible = CommonFieldsVisibleCondition.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    public Date getLastModifyDate() {
        return getValue("lastModifyDate");
    }

    public void setLastModify(User lastModify) {
        setValue("lastModify", lastModify);
    }

    @ManyToOne
    @FormField(DisplayRank = 98, Visible = CommonFieldsVisibleCondition.class)
    public User getLastModify() {
        return getValue("lastModify");
    }

    public void setCreateDate(Date createDate) {
        setValue("createDate", createDate);
    }

    @Temporal(value=TemporalType.DATE)
    @FormField(DisplayRank = 97, Visible = CommonFieldsVisibleCondition.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd", timezone = "CST")
    public Date getCreateDate() {
        return getValue("createDate");
    }

    public void setCreator(User creator) {
        setValue("creator", creator);
    }

    @ManyToOne
    @FormField(DisplayRank = 96, Visible = CommonFieldsVisibleCondition.class)
    public User getCreator() {
        return getValue("creator");
    }

    public boolean getActive() { return getValue("active");}

    public void setActive(boolean active) {setValue("active", active);}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n{Type : ").append(getClass().getName());
        for (Map.Entry<String, Object> entry : _map.entrySet()) {
            if (ApplicationConfig.getToStringIgnoreFields().contains(entry.getKey())) {
                continue;
            }
            final Object value = entry.getValue();
            if (null != value) {
                if (EntityUtils.getInstance().isBaseObject(value.getClass())) {
                    sb.append("\n\t[").append(entry.getKey()).append(" : ").append(((BaseObject) entry.getValue()).getId()).append("]");
                } else {
                    sb.append("\n\t[").append(entry.getKey()).append(" : ").append(entry.getValue()).append("]");
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    public void setValues(Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            FieldMeta fieldMeta = metaDataManager.getFieldMeta(this.getClass(), key);
            Class clazz = fieldMeta.getType();
            if (logger.isTraceEnabled()) {
                logger.trace(String.format("Setting [%s, %s, %s] to type [%s]",
                                           key, value, (value == null) ? null : value.getClass().getName(), clazz));
            }
            if (null != clazz) {
                final Class<? extends Converter> converterClass = fieldMeta.getConverterClass();
                if (converterClass != null && !ApplicationConfig.DefaultConverterClass.equals(converterClass)) {
                    annotatedConvert(key, value, converterClass);
                } else if (null != value && EntityUtils.getInstance().isBaseObject(clazz)) {
                    baseObjectDefaultConvert(key, value, clazz);
                } else if (null == value || ClassUtils.isAssignable(value.getClass(), clazz)) {
                    setValue(key, value);
                } else if (Enum.class.isAssignableFrom(clazz) && value instanceof String) {
                    enumDefaultConvert(key, (String) value, clazz);
                } else {
                    buildInDefaultConvert(key, value, clazz);
                }
            }
        }
    }

    private void buildInDefaultConvert(String key, Object value, Class clazz) {
        final Converter converter = ConverterFactory.getInstance().getConverter(value.getClass(), clazz);
        Object convertedValue = null;
        try {
            if (converter != null) {
                convertedValue = converter.convert(value);
                setValue(key, convertedValue);
            } else {
                logger.error(String.format("Failed to get converter[%s->%s] for value[%s]", value.getClass(), clazz, value));
            }
        } catch (ParseException e) {
            logger.error(String.format("Failed to convert[%s,%s] to type [%s]",
                                       value, value.getClass().getName(), clazz.getName()), e);
        }
    }

    private void enumDefaultConvert(String key, String value, Class<? extends Enum> clazz) {
        Enum enumVal = Enum.valueOf(clazz, value);
        setValue(key, enumVal);
    }

    private void baseObjectDefaultConvert(String key, Object value, Class clazz) {
        if (value != null) {
            final String idQueryForEntity = NamedQueryRules.getInstance().getIdQueryForEntity(clazz.getSimpleName());
            if (value instanceof LinkedHashMap) {
                value = ((LinkedHashMap) value).get("id");
            }
            if (value instanceof String) {
                value = Long.parseLong((String) value);
            }
            if (value instanceof Integer) {
                value = (long) ((Integer) value);
            }
            BaseObject baseObj = BaseOperator.getInstance().getBaseObjectById((Long) value, idQueryForEntity);
            if (null != baseObj) {
                setValue(key, baseObj);
            }
        }
    }

    private void annotatedConvert(String key, Object value, Class<? extends Converter> converterClass) {
        try {
            Converter converter = converterClass.newInstance();
            final Object convertedVal = converter.convert(value);
            setValue(key, convertedVal);
        } catch (InstantiationException | IllegalAccessException | ParseException e ) {
            logger.error(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return !(null == obj || !obj.getClass().equals(getClass()))
            && ((BaseObject) obj).getId() == getId();
    }
}
