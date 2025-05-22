package com.company.library.reports.api;

public interface Factory<T> {
    T create() throws Exception;
}
