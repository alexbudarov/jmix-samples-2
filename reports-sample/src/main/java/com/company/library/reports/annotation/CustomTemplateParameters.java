package com.company.library.reports.annotation;

import io.jmix.reports.entity.CustomTemplateDefinedBy;
import io.jmix.reports.yarg.formatters.CustomReport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.ReportTemplate
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTemplateParameters {

    // custom
    boolean enabled() default false;

    // customDefinedBy
    CustomTemplateDefinedBy definedBy() default CustomTemplateDefinedBy.SCRIPT;

    // customDefinition for URL
    String url() default "";

    // customDefinition for CLASS
    Class<? extends CustomReport> class_() default CustomReport.class;

    // customDefinition for SCRIPT
    String script() default "";

}
