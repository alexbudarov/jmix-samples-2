package com.company.library.reports.annotation;

import java.lang.annotation.*;

/**
 * @see io.jmix.reports.entity.ReportValueFormat
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableValueFormatDef.class)
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

    // groovy - not supported. Instead of script, write ValueFormatter method
}
