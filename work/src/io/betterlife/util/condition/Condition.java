package io.betterlife.util.condition;

import io.betterlife.application.manager.FieldMeta;
import io.betterlife.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public interface Condition {
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType);
}
