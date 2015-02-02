package io.betterlife.util.condition;

import io.betterlife.application.manager.FieldMeta;
import io.betterlife.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class Evaluator {
    public static boolean eval(Class<? extends Condition> clazz, String entityType,
                               BaseObject baseObject, FieldMeta fieldMeta, String operationType) {
        Condition cond = null;
        try {
            cond = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return cond.evaluate(entityType, fieldMeta, baseObject, operationType);
    }

    public static boolean evalVisible(String entityType, final FieldMeta fieldMeta,
                                      final BaseObject baseObject, final String operationType) {
        Class<? extends Condition> vc = fieldMeta.getVisibleCondition();
        return TrueCondition.class.equals(vc) || !FalseCondition.class.equals(vc)
            && Evaluator.eval(vc, entityType, baseObject, fieldMeta, operationType);
    }
}
