package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marks a delegate method that implements some logic related to a template
 *   defined in the report definition class.
 * Method must conform to convention: no parameters, the result is one of supported functional interfaces.
 *
 * @see io.jmix.reports.yarg.formatters.CustomReport
 * @see com.company.library.reports.api.Factory
 * @see TemplateDef
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateDelegate {
    /**
     * @return code of the template declared in the current report definition
     */
    String code();
}
