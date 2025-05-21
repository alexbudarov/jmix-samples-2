package com.company.library.reports.annotation;

import io.jmix.reports.entity.table.TemplateTableColumn;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see TemplateTableColumn
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumnDef {

    String key();

    /**
     * Use msg://group/key format if localization is required.
     */
    String caption();
}