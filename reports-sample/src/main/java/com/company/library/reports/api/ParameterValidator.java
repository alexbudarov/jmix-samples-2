package com.company.library.reports.api;

import org.springframework.context.ApplicationContext;

public interface ParameterValidator<T> {
    void validate(T value, ErrorConsumer errorConsumer, ApplicationContext applicationContext);
}
