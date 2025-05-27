package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks a delegate method that implements some logic related to a value format
 *   defined in the report definition class.
 * Method must conform to convention: no parameters, the result is one of supported functional interfaces.
 *
 * @see com.company.library.reports.api.ValueFormatter
 * @see com.company.library.reports.api.Factory
 * @see ValueFormatDef
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueFormatDelegate {
    /**
     * @return "band" attribute of the value format declared in the current report definition
     */
    String band();

    /**
     * @return "field" attribute of the value format declared in the current report definition
     */
    String field();

}
