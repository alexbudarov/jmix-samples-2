package com.company.library.reports;

import com.company.library.entity.Book;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.DataSetDataLoader;
import com.company.library.reports.api.Factory;
import com.company.library.reports.api.FetchPlanProvider;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlans;
import io.jmix.reports.entity.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Map;

@ReportDef(
        name = "msg://com.company.library.reports/BookRecordReport.name",
        code = "book-report",
        group = DemoReportGroup.class
)
@InputParameterDef(
        alias = "entity",
        name = "msg://com.company.library.reports/BookRecordReport.param.entity",
        type = ParameterType.ENTITY,
        required = true,
        entity = @EntityParameterDef(entityClass = Book.class)
)
@BandDef(
        name = "Root",
        root = true,
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                name = "title",
                type = DataSetType.GROOVY // todo add new constant "CODE" / "METHOD"
        )
)
@BandDef(
        name = "Book1",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                name = "Book1",
                type = DataSetType.SINGLE,
                entity = @EntityDataSetDef(
                        parameterAlias = "entity"
                )
        )
)
@BandDef(
        name = "Authors2",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                name = "Authors2",
                type = DataSetType.MULTI,
                entity = @EntityDataSetDef(
                        parameterAlias = "entity",
                        nestedCollectionAttribute = "authors"
                )
        )
)
@TemplateDef(
        code = "DEFAULT",
        outputType = ReportOutputType.PDF,
        isDefault = true
)
public class BookRecordReport {

    private final FetchPlans fetchPlans;
    private final DataManager dataManager;
    private final ResourceLoader resourceLoader;

    public BookRecordReport(FetchPlans fetchPlans, DataManager dataManager, ResourceLoader resourceLoader) {
        this.fetchPlans = fetchPlans;
        this.dataManager = dataManager;
        this.resourceLoader = resourceLoader;
    }

    @RelatesTo(dataSet = "title")
    public DataSetDataLoader titleDataLoader() {
        return (parameters, parentBand) -> {
            Book book = (Book) parameters.get("entity");
            return List.of(
                    Map.of(
                            "title",
                            "Book Record - %s".formatted(book.getName())
                    )
            );
        };
    }

    @RelatesTo(dataSet = "Book1")
    public FetchPlanProvider book1FetchPlan() {
        return () -> fetchPlans.builder(Book.class)
                .add("name")
                .add("summary")
                .add("literatureType", literatureType -> {
                    literatureType.add("name");
                })
                .build();
    }

    @RelatesTo(dataSet = "Authors2")
    public FetchPlanProvider authors2FetchPlan() {
        // !!! we specify fetch plan for Book, not for nested authors
        return () -> fetchPlans.builder(Book.class)
                .add("authors", author -> {
                    author.add("firstName")
                            .add("lastName");
                })
                .build();
    }

    // example of custom factory method for report template
    @RelatesTo(template = "DEFAULT")
    public Factory<ReportTemplate> defaultTemplate() {
        return () -> {
            ReportTemplate t = dataManager.create(ReportTemplate.class);

            byte[] customTemplateFromDb = loadTemplateFileFromDatabase();
            if (customTemplateFromDb.length == 0) {
                Resource file = resourceLoader.getResource("com/company/library/reports/new/Template-for-BookRecord.docx");
                t.setContent(file.getContentAsByteArray());
            } else {
                t.setContent(customTemplateFromDb);
            }

            t.setOutputNamePattern("${Root.title}.pdf");
            return t;
        };
    }

    public byte[] loadTemplateFileFromDatabase() {
        // todo load from database
        return new byte[0];
    }
}
