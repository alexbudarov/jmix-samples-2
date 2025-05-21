package com.company.library.reports.api;

import org.springframework.context.ApplicationContext;

public interface ValueFormatter<T> {
    String format(T value, ApplicationContext applicationContext);
}
