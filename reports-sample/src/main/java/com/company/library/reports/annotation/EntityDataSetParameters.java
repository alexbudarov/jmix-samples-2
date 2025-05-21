package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see DataSetFetchPlan
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityDataSetParameters {

    // paramName
    String parameterAlias() default "";

    // listParamName
    String listParameterAlias() default "";

    // second part of listParamName if it's like "param#nestedCollection"
    String nestedCollectionAttribute() default "";

    boolean useExistingFetchPlan() default false;

    String fetchPlanName() default "";

    String[] fetchPlan() default {};
}
