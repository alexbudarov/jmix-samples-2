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
     */
    String name() default "";

    /**
     * Message key for Report name.
     * Use this attribute if localization is required.
     */
    String nameKey() default "";

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
}
