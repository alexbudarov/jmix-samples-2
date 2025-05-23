package com.company.library.reports.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.ReportGroup
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ReportGroupDef {

    /**
     * Group title.
     * Use msg://group/key format if localization is required.
     */
    String title();

    /**
     * Unique group code, may be used to identify the group in APIs.
     */
    String code();

    /**
     * Optional unique id in the UUID format.
     * Specify this attribute to have stable object id in runtime (e.g. for URL routes)
     */
    String uuid() default "";

    @AliasFor(annotation = Component.class, attribute = "value")
    String beanName() default "";
}
