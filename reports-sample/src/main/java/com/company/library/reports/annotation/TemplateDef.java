package com.company.library.reports.annotation;

import com.company.library.reports.api.HtmlTemplateType;
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

    // groovy
    // make enum instead of boolean like in UI
    HtmlTemplateType htmlTemplateType() default HtmlTemplateType.FREEMARKER;

    // Chart attributes (AbstractChartDescription) - it's not supported yet in Jmix 2
    // PivotTable attributes (PivotTableDescription) - it's not supported yet in Jmix 2
}
