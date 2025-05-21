package com.company.library.reports.annotation;

import io.jmix.reports.entity.table.TemplateTableBand;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see TemplateTableBand
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableBandDef {

    String bandName();

    TableColumnDef[] columns();
}
