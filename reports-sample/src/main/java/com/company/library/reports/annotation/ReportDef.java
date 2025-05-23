package com.company.library.reports.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.Report
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ReportDef {

    @AliasFor(annotation = Component.class, attribute = "value")
    String beanName() default "";

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
     * Optional unique id in the UUID format.
     * Specify this attribute to have stable object id in runtime (e.g. for URL routes)
     */
    String uuid() default "";

    Class<?> group();

    boolean availableThroughRestApi() default false;

    boolean system() default false;

    // validationScript
    // or via com.company.library.reports.api.ParametersCrossValidator
    String crossValidationScript() default "";

    // validationOn - implicitly
    // isTmp, rolesIdx, screensIdx, inputEntityTypesIdx, xml - not necessary here

}
