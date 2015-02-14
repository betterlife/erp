package io.betterlife.framework.condition;

import io.betterlife.framework.meta.FieldMeta;
import io.betterlife.framework.domains.BaseObject;

/**
 * Author: Lawrence Liu
 * Date: 2/2/15
 */
public class Evaluator {
    private static Evaluator instance = new Evaluator();

    private Evaluator(){};

    public static boolean eval(Class<? extends Condition> clazz, String entityType,
                               BaseObject baseObject, FieldMeta fieldMeta, String operationType) {
        Condition cond = null;
        try {
            if (null != clazz) {
                cond = clazz.newInstance();
            }
            return null != cond && cond.evaluate(entityType, fieldMeta, baseObject, operationType);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean evalVisible(String entityType, final FieldMeta fieldMeta,
                                      final BaseObject baseObject, final String operationType) {
        Class<? extends Condition> vc = fieldMeta.getVisibleCondition();
        return Evaluator.eval(vc, entityType, baseObject, fieldMeta, operationType);
    }

    public boolean evalEditable(String entityType, final FieldMeta fieldMeta,
                                final BaseObject baseObject, final String operationType) {
        Class<? extends Condition> vc = fieldMeta.getEditableCondition();
        return Evaluator.eval(vc, entityType, baseObject, fieldMeta, operationType);
    }

    public static Evaluator getInstance() {
        return instance;
    }

    public static void setInstance(Evaluator instance) {
        Evaluator.instance = instance;
    }
}
