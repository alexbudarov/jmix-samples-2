package com.company.library.reports.annotation;

import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.PredefinedTransformation;

import java.lang.annotation.*;

/**
 * @see io.jmix.reports.entity.ReportInputParameter
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableInputParameterDef.class)
public @interface InputParameterDef {

    String alias();

    /**
     * Human-readable parameter name.
     * Use msg://group/key format if localization is required.
     */
    String name() default "";

    boolean required() default false;

    ParameterType type();

    // parameterClassName
    // probably makes sense only for Date/time types
    Class<?> parameterClassName() default void.class;

    Class<?> enumerationClass() default void.class;

    // for complex types - use DefaultValueProvider method
    String defaultValue() default ""; // todo provide additional configuration method

    EntityParameterDef entity() default @EntityParameterDef();

    boolean predefinedTransformationEnabled() default false; // because there's no NONE enum value
    PredefinedTransformation predefinedTransformation() default PredefinedTransformation.CONTAINS;

    boolean hidden() default false;

    boolean defaultDateIsCurrent() default false;

    // position - determine automatically by order of declaration
    // transformationScript - method with @EntityParameterTransformation
    // validationScript - method with @EntityParameterValidation
    // validationOn - auto-determine if annotated method exists
}
