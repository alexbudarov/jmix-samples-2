package com.company.library.reports;

import com.company.library.reports.annotation.*;
import io.jmix.core.TimeSource;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ReportOutputType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ReportDef(
        name = "Publications grouped by types and books",
        code = "PUBLICATIONS_GROUPED",
        group = DemoReportGroup.class
)
public interface PublicationsGroupedByTypesBooksReport {

    @BandDef(name = BandDef.ROOT, root = true, orientation = Orientation.HORIZONTAL)
    void rootBand();

    @BandDef(name = "header", parent = BandDef.ROOT, orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "header",
            type = DataSetType.GROOVY // todo rename or add new constant "CODE" / "METHOD"
    )
    void headerBand();

    @DataSetImplementation(dataSet = "header")
    default List<Map<String, Object>> headerImplementation(@Autowired TimeSource timeSource,
                                                           @Autowired CurrentAuthentication currentAuthentication) {
        String user = currentAuthentication.getUser().getUsername();
        java.util.Date currentDate = timeSource.currentTimestamp();
        return List.of(
                Map.of(
                        "generated_by", user,
                        "generated_when", currentDate
                )
        );
    }

    @BandDef(name = "tableheader", parent = BandDef.ROOT, orientation = Orientation.HORIZONTAL)
    void tableheaderBand();

    @BandDef(name = "type", parent = BandDef.ROOT, orientation = Orientation.HORIZONTAL)
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
            valueName = "header.generated_when",
            formatString = "dd.MM.yyyy"
    )
    void headerGeneratedWhenValueFormat();

    // method with flexible signature, instead of Groovy script
    @ValueFormatDef(
            valueName = "header.generated_by"
    )
    default String headerGeneratedWhenValueFormat(@ParameterValue String value) {
        return value.toUpperCase();
    }
}
