package com.company.library.reports;

import com.company.library.reports.annotation.*;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.JsonSourceType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ReportOutputType;

@ReportDef(
        name = "Neighbourhoods",
        code = "NEIGHB",
        group = DemoReportGroup.class,
        description = "JSON data set example"
)
@BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
@BandDef(name = "header", parent = "Root", orientation = Orientation.HORIZONTAL)
@BandDef(
        name = "items",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                type = DataSetType.JSON,
                json = @JsonDataSetParameters(
                        source = JsonSourceType.URL,
                        url = "https://data.police.uk/api/leicestershire/neighbourhoods",
                        jsonPathQuery = "$.*"
                )
        )
)
@TemplateDef(
        code = "DEFAULT",
        outputType = ReportOutputType.XLSX,
        filePath = "com/company/library/reports/new/Neighbourhoods.xlsx",
        isDefault = true,
        outputNamePattern = "Neighbourhood List.xlsx"
)
public class NeighbourhoodsReport {
}
