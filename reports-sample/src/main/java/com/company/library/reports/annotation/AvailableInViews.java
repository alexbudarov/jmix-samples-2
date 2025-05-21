package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.ReportScreen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvailableInViews {

    String[] viewIds() default {};

    Class[] viewClasses() default {};
}
