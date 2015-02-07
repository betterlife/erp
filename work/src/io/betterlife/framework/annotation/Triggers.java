package io.betterlife.framework.annotation;

import io.betterlife.framework.trigger.DoNothingEntityTrigger;
import io.betterlife.framework.trigger.EntityTrigger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Lawrence Liu
 * Date: 2/5/15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Triggers {

    Class<? extends EntityTrigger> Save() default DoNothingEntityTrigger.class;

}
