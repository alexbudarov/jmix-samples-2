package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportDef {

    /**
     * Report name.
     * Use msg://group/key format if localization is required.
     */
    String name();

    /**
     * Unique report code, may be used as a unique identifier for using in APIs.
     */
    String code();

    /**
     * Report description
     */
    String description() default "";

    /**
     * Optional id in the UUID format.
     */
    String uuid() default "";

    Class<?> group();

    boolean availableThroughRestApi() default false;

    boolean system() default false;

    // todo cross-parameter validation: validationScript (with lambda),
    // validationOn - implicitly
    // isTmp, rolesIdx, screensIdx, inputEntityTypesIdx, xml - not necessary here
}
