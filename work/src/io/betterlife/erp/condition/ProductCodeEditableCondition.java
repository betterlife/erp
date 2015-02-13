package io.betterlife.erp.condition;

import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.condition.Condition;
import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class ProductCodeEditableCondition implements Condition {
    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return !"product".equals(entityType) || "Create".equals(operationType) && "code".equals(fieldMeta.getName());
    }
}
