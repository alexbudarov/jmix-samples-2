package com.company.library.reports.annotation;

import io.jmix.reports.entity.BandDefinition;
import io.jmix.reports.entity.Orientation;

import java.lang.annotation.*;

/**
 * @see BandDefinition
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableBandDef.class)
public @interface BandDef {

    String name() default "";

    boolean root() default false;
    String parent() default "";
    Orientation orientation();

    DataSetDef[] dataSets() default {};

    // position - implicitly by definition order
    // multiDataSet - implicitly
}
