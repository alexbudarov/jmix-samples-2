package com.company.library.reports.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityParameterDef {

    Class<?> entityClass() default void.class; // entityMetaClass

    boolean lookup() default false;

    String lookupJoin() default "";

    String lookupWhere() default "";

    String screen() default "";
}
