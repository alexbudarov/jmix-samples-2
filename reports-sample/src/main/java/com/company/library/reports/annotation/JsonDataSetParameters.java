package com.company.library.reports.annotation;

import io.jmix.reports.entity.JsonSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.DataSet
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonDataSetParameters {

    // jsonSourceType
    JsonSourceType source() default JsonSourceType.GROOVY_SCRIPT;

    // jsonSourceText for GROOVY_SCRIPT
    String script() default "";

    // jsonSourceText for URL
    String url() default "";

    String jsonPathQuery() default "";

    // jsonSourceInputParameter
    String inputParameter() default "";
}
