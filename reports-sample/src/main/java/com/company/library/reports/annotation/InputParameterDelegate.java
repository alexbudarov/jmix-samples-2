package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks a delegate method that implements some logic related to an input parameter
 *   defined in the report definition class.
 * Method must conform to convention: no parameters, the result is one of supported functional interfaces.
 *
 * @see com.company.library.reports.api.ParameterValidator
 * @see com.company.library.reports.api.ParameterTransformer
 * @see com.company.library.reports.api.DefaultValueProvider
 * @see com.company.library.reports.api.Factory
 * @see InputParameterDef
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InputParameterDelegate {
    /**
     * @return alias of the input parameter declared in the current report definition
     */
    String alias();
}
