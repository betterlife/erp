package io.betterlife.framework.trigger;

import io.betterlife.framework.domains.BaseObject;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public interface EntityTrigger {
    public void action(EntityManager entityManager, BaseObject baseObject) throws Exception;
}
