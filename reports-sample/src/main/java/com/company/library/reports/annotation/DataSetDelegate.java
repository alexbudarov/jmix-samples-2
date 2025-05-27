package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks a delegate method that implements some logic related to a data set
 *   defined in the report definition class.
 * Method must conform to convention: no parameters, the result is one of supported functional interfaces.
 *
 * @see com.company.library.reports.api.DataSetDataLoader
 * @see com.company.library.reports.api.FetchPlanProvider
 * @see com.company.library.reports.api.Factory
 * @see DataSetDef
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSetDelegate {
    /**
     * @return name of the data set declared in the current report definition
     */
    String name();
}
