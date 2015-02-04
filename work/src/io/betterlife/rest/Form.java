package io.betterlife.rest;

import io.betterlife.application.config.ApplicationConfig;
import io.betterlife.util.condition.Condition;
import io.betterlife.util.condition.TrueCondition;
import io.betterlife.util.converter.Converter;
import io.betterlife.util.converter.DefaultConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Lawrence Liu
 * Date: 12/9/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {
    String RepresentField() default ApplicationConfig.DefaultRepresentField;

    int DisplayRank() default ApplicationConfig.DefaultFieldRank;

    Class<? extends Condition> Visible() default TrueCondition.class;

    Class<? extends Converter> Converter() default DefaultConverter.class;

    Class<? extends Condition> Editable() default TrueCondition.class;

    String TrueLabel() default "True";
    String FalseLabel() default "False";
}
