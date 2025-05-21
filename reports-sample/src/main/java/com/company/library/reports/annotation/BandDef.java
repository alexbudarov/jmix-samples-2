package com.company.library.reports.annotation;

import io.jmix.reports.entity.Orientation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DataSetDef
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BandDef {

    String name() default "";

    boolean root() default false;
    String parent() default "";
    Orientation orientation();

    // position - implicitly by definition order
    // multiDataSet - implicitly
}
