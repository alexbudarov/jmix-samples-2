package com.company.library.reports.annotation;

import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.entity.ReportTemplate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * See also: {@link TemplateTableDef}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateDef {
    String code() default ReportTemplate.DEFAULT_TEMPLATE_CODE;

    boolean isDefault() default false;

    ReportOutputType outputType();

    // alterable
    boolean alterableOutput() default false;

    CustomTemplateParameters custom() default @CustomTemplateParameters();

    String outputNamePattern() default "";

    // determines name and content
    // (resource path to file in /src/xxx/resources/yyy)
    String filePath() default "";

    // groovy - seems unused
    // todo attributes for io.jmix.reports.entity.charts.AbstractChartDescription
    // todo attributes for io.jmix.reports.entity.pivottable.PivotTableDescription
}
