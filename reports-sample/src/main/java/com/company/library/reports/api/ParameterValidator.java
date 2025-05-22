package com.company.library.reports.api;

public interface ParameterValidator<T> {
    void validate(T value, ErrorConsumer errorConsumer);
}
