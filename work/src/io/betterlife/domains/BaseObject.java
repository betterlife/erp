package io.betterlife.domains;

import io.betterlife.domains.finical.CostCenter;
import io.betterlife.domains.finical.Expense;
import io.betterlife.domains.finical.ExpenseCategory;
import io.betterlife.domains.security.User;
import io.betterlife.persistence.BaseOperator;
import org.apache.commons.lang3.ClassUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 10/31/14
 */
@MappedSuperclass
public abstract class BaseObject {

    //TODO.xqliu 如果不同的Entity里面有相同的名称的字段,会发生冲突.-->在key里面加上Entity的名称部分-->在setValues里面getFieldMeta的时候,需要也加上
    private static Map<String, Class> _fieldsMeta = new HashMap<>();
    private static boolean fieldMetaAlreadySet  = false;

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
    public static Class getFieldMeta(String fieldName) {
        return _fieldsMeta.get(fieldName);
    }

    public static void setFieldMeta(String fieldName, Class clazz) {
        _fieldsMeta.put(fieldName, clazz);
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
        setAllFieldMetas();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Class clazz = getFieldMeta(key);
            if (clazz.equals(String.class)) {
                setValue(key, value);
            } else if (clazz.equals(Date.class)) {
                //If clazz is Date, then we assume a time stamp will pass here.
                try {
                    setValue(key, new Date(Long.parseLong(value)));
                } catch (Exception e) {
                    //Do nothing right now.
                }
            } else if (clazz.equals(Long.class)) {
                setValue(key, Long.parseLong(value));
            } else if (clazz.equals(BigDecimal.class)) {
                setValue(key, new BigDecimal(value));
            } else if (ClassUtils.isAssignable(clazz, BaseObject.class)){
                //If clazz is child type of BaseObject, then we assume an id will be passed here.
                BaseObject baseObj = getBaseObjectById(entityManager, value, clazz);
                if (null != baseObj) {
                    setValue(key, baseObj);
                }
            }
        }
    }

    public <T> T getBaseObjectById(EntityManager entityManager, String id, Class clazz) {
        return BaseOperator.getInstance().getBaseObject(entityManager,
            Long.parseLong(id), clazz.getSimpleName() + ".getById");
    }

    public void setAllFieldMetas() {
        if (!fieldMetaAlreadySet) {
            BaseObject.setFieldMeta("id", Long.class);
            BaseObject.setFieldMeta("lastModifyDate", Date.class);
            BaseObject.setFieldMeta("lastModify", User.class);
            BaseObject.setFieldMeta("createDate", Date.class);
            BaseObject.setFieldMeta("creator", User.class);
            ExpenseCategory.setFieldMeta("categoryName", String.class);
            User.setFieldMeta("username", String.class);
            User.setFieldMeta("password", String.class);
            CostCenter.setFieldMeta("name", String.class);
            Expense.setFieldMeta("expenseCategory", ExpenseCategory.class);
            Expense.setFieldMeta("costCenter", CostCenter.class);
            Expense.setFieldMeta("user", User.class);
            Expense.setFieldMeta("amount", BigDecimal.class);
            Expense.setFieldMeta("remark", String.class);
            Expense.setFieldMeta("date", Date.class);
            fieldMetaAlreadySet = true;
        }
    }
}
