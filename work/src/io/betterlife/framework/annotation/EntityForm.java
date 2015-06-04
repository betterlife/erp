package io.betterlife.framework.annotation;

import io.betterlife.erp.domains.financial.PaymentMethod;
import io.betterlife.framework.application.config.ApplicationConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Lawrence Liu
 * Date: 15/3/17
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityForm {
    String DetailField() default ApplicationConfig.DefaultDetailField;

    Class DetailFieldType();
}
