package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.ReportValueFormat
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueFormatDef {

    // valueName - first part
    String band();

    // valueName - second part
    String field();

    /**
     * @return field format.
     *   For number values specify the format according to the {@link java.text.DecimalFormat} rules,
     *   for dates - {@link java.text.SimpleDateFormat}.
     */
    String format() default "";

    // groovy - not supported. Instead of script, write the method implementation.
}
