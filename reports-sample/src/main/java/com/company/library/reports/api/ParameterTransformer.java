package com.company.library.reports.api;

import java.util.Map;

public interface ParameterTransformer<T> {
    Object transform(T value, Map<String, Object> parameterValues);
}
