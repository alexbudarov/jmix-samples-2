package com.company.library.reports.api;

import org.springframework.context.ApplicationContext;

public interface Factory<T> {
    T create(ApplicationContext applicationContext) throws Exception;
}
