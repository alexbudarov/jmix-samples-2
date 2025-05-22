package com.company.library.reports.annotation;

import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.entity.ReportTemplate;

import java.lang.annotation.*;

/**
 * @see ReportTemplate
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableTemplateDef.class)
public @interface TemplateDef {

    String code();

    boolean isDefault() default false;

    ReportOutputType outputType();

    // alterable
    boolean alterableOutput() default false;

    CustomTemplateParameters custom() default @CustomTemplateParameters();

    String outputNamePattern() default "";

    // determines name and content
    // (resource path to file in /src/xxx/resources/yyy)
    String filePath() default "";

    TemplateTableDef table() default @TemplateTableDef(bands = {});

    // groovy - seems unused
    // todo attributes for io.jmix.reports.entity.charts.AbstractChartDescription
    // todo attributes for io.jmix.reports.entity.pivottable.PivotTableDescription
}
