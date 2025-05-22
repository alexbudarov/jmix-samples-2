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

    JsonSourceType source() default JsonSourceType.GROOVY_SCRIPT;

    String sourceText() default ""; // todo delete if we don't support GROOVY_SCRIPT

    String url() default "";

    String pathQuery() default "";

    String inputParameter() default "";
}
