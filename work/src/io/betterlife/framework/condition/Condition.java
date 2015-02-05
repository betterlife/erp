package io.betterlife.framework.condition;

import io.betterlife.framework.application.manager.FieldMeta;
import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public interface Condition {
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType);
}
