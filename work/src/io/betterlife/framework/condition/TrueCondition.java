package io.betterlife.framework.condition;

import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class TrueCondition implements Condition {
    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return true;
    }
}
