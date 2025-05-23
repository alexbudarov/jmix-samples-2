package com.company.library.reports.api;

import java.util.Map;

/**
 * like io.jmix.reports.entity.Report#validationScript
 */
public interface ParametersCrossValidator {
    void validateParameters(Map<String, Object> parameterValues, ErrorConsumer errorConsumer);
}
