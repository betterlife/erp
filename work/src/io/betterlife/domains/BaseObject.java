package io.betterlife.domains;

import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import io.betterlife.persistence.MetaDataManager;
import io.betterlife.persistence.NamedQueryRules;
import io.betterlife.util.jpa.OpenJPAUtil;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@MappedSuperclass
public abstract class BaseObject {

    private Map<String, Object> _map = new HashMap<>();

    private OpenJPAUtil openJPAUtil = OpenJPAUtil.getInstance();

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
    protected <T> T getValue(String fieldName) {
        @SuppressWarnings("unchecked")
        T result = (T) _map.get(fieldName);
        return result;
    }

    protected <T> void setValue(String fieldName, T value) {
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

    public void setValues(EntityManager entityManager, Map<String, String> parameters) {
        MetaDataManager.getInstance().setAllFieldMetaData(entityManager);
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Class clazz = MetaDataManager.getInstance().getFieldMetaData(this.getClass(), key);
            if (clazz.equals(String.class)) {
                setValue(key, value);
            } else if (clazz.equals(Date.class)) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
                    setValue(key, date);
                } catch (Exception e) {
                    //If clazz is Date and failed to parse by "yyyy-MM-dd", then we assume a time stamp will pass here.
                    setValue(key, new Date(Long.parseLong(value)));
                }
            } else if (clazz.equals(Long.class)) {
                setValue(key, Long.parseLong(value));
            } else if (clazz.equals(BigDecimal.class)) {
                setValue(key, new BigDecimal(value));
            } else if (ClassUtils.isAssignable(clazz, BaseObject.class)) {
                //If clazz is child type of BaseObject, then we assume an id will be passed here.
                BaseObject baseObj = BaseOperator.getInstance().getBaseObjectById(entityManager, openJPAUtil, Long.parseLong(value),
                    NamedQueryRules.getInstance().getIdQueryForEntity(clazz.getSimpleName()));
                if (null != baseObj) {
                    setValue(key, baseObj);
                }
            }
        }
    }

}
