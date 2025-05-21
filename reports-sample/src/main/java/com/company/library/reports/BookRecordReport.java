package com.company.library.reports;

import com.company.library.entity.Book;
import com.company.library.reports.annotation.*;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@ReportDef(
        name = "msg://com.company.library.reports/BookRecordReport.name",
        code = "book-report",
        group = DemoReportGroup.class
)
public interface BookRecordReport {

    @InputParameterDef(
            alias = "entity",
            name = "msg://com.company.library.reports/BookRecordReport.param.entity",
            type = ParameterType.ENTITY,
            required = true,
            entityParameters = @EntityParameterDef(entityClass = Book.class)
    )
    void entityInputParameter();

    @BandDef(name = BandDef.ROOT, root = true, orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "title",
            type = DataSetType.GROOVY // todo rename or add new constant "CODE" / "METHOD"
    )
    void rootBand();

    @DataSetImplementation(dataSet = "title")
    default List<Map<String, Object>> titleImplementation(@ReportParameters Map<String, Object> parameters) {
        Book book = (Book) parameters.get("entity");
        return List.of(
                Map.of(
                        "title",
                        "Book Record - %s".formatted(book.getName())
                )
        );
    }

    @BandDef(name = "Book1", parent = BandDef.ROOT, orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "Book1",
            type = DataSetType.SINGLE,
            entity = @EntityDataSetParameters(
                    parameterAlias = "entity"
            )
    )
    void book1Band();

    @DataSetFetchPlan(name = "Book1")
    default FetchPlan book1FetchPlan(@Autowired FetchPlans fetchPlans) {
        return fetchPlans.builder(Book.class)
                .add("name")
                .add("summary")
                .add("literatureType", literatureType -> {
                    literatureType.add("name");
                })
                .build();
    }

    @BandDef(name = "Authors2", parent = BandDef.ROOT, orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "Authors2",
            type = DataSetType.MULTI,
            entity = @EntityDataSetParameters(
                    parameterAlias = "entity",
                    nestedCollectionAttribute = "authors"
            )
    )
    void authors2Band();

    @DataSetFetchPlan(name = "Authors2")
    default FetchPlan authors2FetchPlan(@Autowired FetchPlans fetchPlans) {
        // !!! we specify fetch plan for Book, not for nested authors
        return fetchPlans.builder(Book.class)
                .add("authors", author -> {
                    author.add("firstName")
                            .add("lastName");
                })
                .build();
    }

    @TemplateDef(
            outputType = ReportOutputType.PDF,
            filePath = "com/company/library/reports/new/Template-for-BookRecord.docx",
            isDefault = true,
            outputNamePattern = "${Root.title}.pdf"
    )
    void defaultTemplate();
}
