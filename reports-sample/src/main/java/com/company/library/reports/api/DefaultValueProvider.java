package com.company.library.reports.api;

import org.springframework.context.ApplicationContext;

public interface DefaultValueProvider<T> {
    T getDefaultValue(ApplicationContext applicationContext);
}
