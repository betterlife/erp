package io.betterlife.framework.condition;

import io.betterlife.framework.constant.Operation;
import io.betterlife.framework.domains.BaseObject;
import io.betterlife.framework.meta.FieldMeta;

/**
 * Author: Lawrence Liu
 * Date: 15/3/6
 */
public class DefaultEditableCondition implements Condition {
    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return !"id".equals(fieldMeta.getName()) && (Operation.UPDATE.equals(operationType) || Operation.CREATE.equals(operationType));
    }
}
