package io.betterlife.framework.meta;

import io.betterlife.framework.trigger.EntityTrigger;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 */
public class EntityMeta {
    private Class type;
    private Class<? extends EntityTrigger> saveTrigger;
    private String detailField;

    public Class<? extends EntityTrigger> getSaveTrigger() {
        return saveTrigger;
    }

    public void setSaveTrigger(Class<? extends EntityTrigger> saveTrigger) {
        this.saveTrigger = saveTrigger;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public void setDetailField(String detailField) {
        this.detailField = detailField;
    }

    public String getDetailField() {
        return detailField;
    }
}
