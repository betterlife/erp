package io.betterlife.framework.trigger;

import io.betterlife.framework.domains.BaseObject;

import javax.persistence.EntityManager;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
public class DoNothingEntityTrigger implements EntityTrigger {
    @Override
    public void action(EntityManager entityManager, BaseObject newObj, BaseObject oldObj) {

    }
}
