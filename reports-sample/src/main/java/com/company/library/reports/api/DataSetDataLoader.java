package com.company.library.reports.api;

import io.jmix.reports.yarg.structure.BandData;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface DataSetDataLoader {
    List<Map<String, Object>> loadData(Map<String, Object> parameters, @Nullable BandData parentBand);
}
