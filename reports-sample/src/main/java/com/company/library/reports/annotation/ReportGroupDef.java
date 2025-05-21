package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportGroupDef {

    /**
     * Group title.
     * Use msg://group/key format if localization is required.
     */
    String title() default "";

    /**
     * Unique group code, may be used to identify the group in APIs.
     */
    String code();

    /**
     * Optional id in the UUID format.
     */
    String uuid() default "";
}
