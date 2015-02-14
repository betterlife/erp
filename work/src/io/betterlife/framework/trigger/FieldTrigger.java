package io.betterlife.framework.trigger;

import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public interface FieldTrigger {
    public <T> void action(BaseObject baseObject, String fieldName, T value);
}
