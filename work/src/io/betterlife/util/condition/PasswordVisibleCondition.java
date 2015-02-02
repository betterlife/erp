package io.betterlife.util.condition;

import io.betterlife.application.manager.FieldMeta;
import io.betterlife.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class PasswordVisibleCondition implements Condition {
    @Override
    public boolean evaluate(String entityType, FieldMeta fieldMeta, BaseObject baseObject, String operationType) {
        return "user".equals(entityType) && ("Create".equals(operationType) ||"Update".equals(operationType));
    }
}
