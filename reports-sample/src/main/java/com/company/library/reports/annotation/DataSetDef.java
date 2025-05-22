package com.company.library.reports.annotation;

import io.jmix.reports.entity.DataSetType;

import java.lang.annotation.*;

/**
 * @see io.jmix.reports.entity.DataSet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSetDef {
    String name() default "";

    // text for GROOVY
    String script() default "";

    DataSetType type();

    JsonDataSetParameters json() default @JsonDataSetParameters();

    EntityDataSetDef entity() default @EntityDataSetDef();

    String linkParameterName() default "";
    String dataStore() default "";
    boolean processTemplate() default false;

    // text for JPQL or SQL
    String query() default "";
}
