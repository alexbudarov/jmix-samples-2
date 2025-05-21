package com.company.library.reports;

import com.company.library.reports.annotation.*;
import com.company.library.reports.api.DataSetDataLoader;
import com.company.library.reports.api.ValueFormatter;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ReportOutputType;

import java.util.List;
import java.util.Map;

@ReportDef(
        name = "Publications grouped by types and books",
        code = "PUBLICATIONS_GROUPED",
        group = DemoReportGroup.class
)
public interface PublicationsGroupedByTypesBooksReport {

    @BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
    void rootBand();

    @BandDef(name = "header", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "header",
            type = DataSetType.GROOVY // todo rename or add new constant "CODE" / "METHOD"
    )
    void headerBand();

    @RelatesTo(dataSet = "header")
    default DataSetDataLoader headerImplementation() {
        return (parameters, parentBand, applicationContext) -> {
            String user = applicationContext.getBean(CurrentAuthentication.class).getUser().getUsername();
            java.util.Date currentDate = applicationContext.getBean(TimeSource.class).currentTimestamp();
            return List.of(
                    Map.of(
                            "generated_by", user,
                            "generated_when", currentDate
                    )
            );
        };
    }

    @BandDef(name = "tableheader", parent = "Root", orientation = Orientation.HORIZONTAL)
    void tableheaderBand();

    @BandDef(name = "type", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            type = DataSetType.JPQL,
            query = """
                    select b.literatureType.id as typeId,
                    b.literatureType.name as type
                    from Book b
                    """
    )
    void typeBand();

    @BandDef(name = "book", parent = "type", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            type = DataSetType.JPQL,
            query = """
                    select b.id as bookId,
                    b.name as bookName
                    from Book b
                    where b.literatureType.id = ${type.typeId}
                    """
    )
    void bookBand();

    @BandDef(name = "publisher", parent = "book", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            type = DataSetType.JPQL,
            query = """
                    select bp.publisher.name as publisher,
                    bp.year as year,
                    bp.city as town
                    from BookPublication bp
                    where bp.book.id = ${book.bookId}
                    """
    )
    void publisherBand();

    @TemplateDef(
            outputType = ReportOutputType.XLSX,
            filePath = "com/company/library/reports/new/Template for publications by type.xlsx",
            isDefault = true,
            outputNamePattern = "Publications grouped by types and books"
    )
    void defaultTemplate();

    @ValueFormatDef(
            band = "header",
            field = "generated_when",
            format = "dd.MM.yyyy"
    )
    void headerGeneratedWhenValueFormat();

    // method with flexible signature, instead of Groovy script
    @ValueFormatDef(
            band = "header",
            field = "generated_by"
    )
    default ValueFormatter<String> headerGeneratedByValueFormat() {
        return (value, applicationContext) -> {
            return value.toUpperCase();
        };
    }
}
