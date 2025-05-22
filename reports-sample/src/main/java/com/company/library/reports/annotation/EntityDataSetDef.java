package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see io.jmix.reports.entity.DataSet
 * @see com.company.library.reports.api.FetchPlanProvider
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityDataSetDef {

    // paramName
    String parameterAlias() default "";

    // listParamName
    String listParameterAlias() default "";

    // second part of listParamName if it's like "param#nestedCollection"
    String nestedCollectionAttribute() default "";

    boolean useExistingFetchPlan() default false;

    String fetchPlanName() default "";
}
