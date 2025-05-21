package com.company.library.reports.api;

import io.jmix.core.FetchPlan;
import org.springframework.context.ApplicationContext;

public interface FetchPlanProvider {
    FetchPlan getFetchPlan(ApplicationContext applicationContext);
}
