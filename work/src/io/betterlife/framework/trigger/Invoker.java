package io.betterlife.framework.trigger;

import io.betterlife.framework.application.manager.EntityMeta;
import io.betterlife.framework.application.manager.MetaDataManager;
import io.betterlife.framework.domains.BaseObject;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 2/6/15
 */
public class Invoker {

    public static <T extends BaseObject> void invokeSaveTrigger(EntityManager entityManager, T obj, BaseObject original)
        throws Exception {
        EntityMeta meta = MetaDataManager.getInstance().getEntityMeta(obj.getClass());
        if (null != meta) {
            Class<? extends EntityTrigger> triggerClazz = meta.getSaveTrigger();
            if (null != triggerClazz) {
                EntityTrigger trigger = triggerClazz.newInstance();
                if (null != trigger) {
                    trigger.action(entityManager, obj, original);
                }
            }
        }
    }

}
