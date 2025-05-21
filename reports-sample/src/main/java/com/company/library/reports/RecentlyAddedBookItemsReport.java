package com.company.library.reports;

import com.company.library.reports.annotation.*;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;

import java.time.ZoneOffset;
import java.util.Date;

@ReportDef(
        name = "Recently added book items",
        code = "RECENTLY_ADDED_BOOK_ITEMS",
        group = DemoReportGroup.class
)
public interface RecentlyAddedBookItemsReport {

    @InputParameterDef(
            alias = "createDt",
            name = "Create After",
            type = ParameterType.DATETIME,
            required = true
    )
    void createDtInputParameter();

    @InputParameterTransformation(alias = "createDt")
    default Object createDtTransform(@ParameterValue Date paramValue) {
        return paramValue.toInstant().atOffset(ZoneOffset.UTC);
    }

    @BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
    void rootBand();

    @BandDef(name = "headerBookInstances", parent = "Root", orientation = Orientation.HORIZONTAL)
    void headerBookInstancesBand();

    @BandDef(name = "BookInstances", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            type = DataSetType.JPQL,
            query = """
                    select bookPublication_book.name as "bookPublication.book.name",
                    libraryDepartment.name as "libraryDepartment.name"
                    from BookInstance e
                    left join e.bookPublication.book bookPublication_book
                    left join e.libraryDepartment libraryDepartment
                    where e.createdDate >= ${createDt}
                    """
    )
    void bookInstancesBand();

    @TemplateDef(
            outputType = ReportOutputType.XLSX,
            filePath = "com/company/library/reports/new/RecentlyAddedBookItems.xlsx",
            isDefault = true,
            outputNamePattern = "Recently added book items.xlsx"
    )
    void defaultTemplate();
}
