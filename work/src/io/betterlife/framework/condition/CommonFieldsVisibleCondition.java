package io.betterlife.framework.condition;

import io.betterlife.framework.application.config.FormConfig;
import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class CommonFieldsVisibleCondition implements Condition {
    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        String fieldName = fieldMeta.getName();
        if ("List".equals(operationType)) {
            return !FormConfig.getInstance().getListFormIgnoreFields().contains(fieldName);
        } else if ("Update".equals(operationType)) {
            return !FormConfig.getInstance().getEditFormIgnoreFields().contains(fieldName);
        } else if ("Create".equals(operationType)) {
            return !FormConfig.getInstance().getCreateFormIgnoreFields().contains(fieldName);
        }
        return true;
    }
}
