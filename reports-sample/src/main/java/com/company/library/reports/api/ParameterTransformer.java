package com.company.library.reports.api;

import org.springframework.context.ApplicationContext;

import java.util.Map;

public interface ParameterTransformer<T> {
    Object transform(T value, Map<String, Object> params, ApplicationContext applicationContext);
}
