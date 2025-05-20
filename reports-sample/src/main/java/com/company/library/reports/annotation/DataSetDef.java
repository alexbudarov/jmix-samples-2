package com.company.library.reports.annotation;

import io.jmix.reports.entity.DataSetType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DataSetDefs.class)
public @interface DataSetDef {
    String name() default "";

    String text() default "";

    DataSetType type();

    JsonDataSetParameters json() default @JsonDataSetParameters();

    EntityDataSetParameters entity() default @EntityDataSetParameters();

    String linkParameterName() default "";
    String dataStore() default "";
    boolean processTemplate() default false;
}
