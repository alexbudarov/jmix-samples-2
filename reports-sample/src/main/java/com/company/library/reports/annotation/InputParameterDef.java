package com.company.library.reports.annotation;

import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.PredefinedTransformation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InputParameterDef {

    String alias();

    /**
     * Human-readable parameter name.
     * Use msg://group/key format if localization is required.
     */
    String name() default "";

    boolean required() default false;

    ParameterType type();

    Class<?> enumerationClass() default void.class;

    String defaultValue() default ""; // todo provide additional configuration method

    EntityParameterDef entityParameters() default @EntityParameterDef();

    boolean predefinedTransformationEnabled() default false; // because there's no NONE enum value
    PredefinedTransformation predefinedTransformation() default PredefinedTransformation.CONTAINS;

    boolean hidden() default false;

    boolean defaultDateIsCurrent() default false;

    // position - determine automatically by order of declaration
    // parameterClassName - auto calculated, not necessary
    // transformationScript - method with @EntityParameterTransformation
    // validationScript - method with @EntityParameterValidation
    // validationOn - auto-determine if annotated method exists
}
