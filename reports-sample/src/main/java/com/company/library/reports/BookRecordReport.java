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
public interface BookRecordReport {

    @InputParameterDef(
            alias = "entity",
            name = "msg://com.company.library.reports/BookRecordReport.param.entity",
            type = ParameterType.ENTITY,
            required = true,
            entityParameters = @EntityParameterDef(entityClass = Book.class)
    )
    void entityInputParameter();

    @BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "title",
            type = DataSetType.GROOVY // todo rename or add new constant "CODE" / "METHOD"
    )
    void rootBand();

    @RelatesTo(dataSet = "title")
    default DataSetDataLoader titleDataLoader() {
        return (parameters, parentBand, applicationContext) -> {
            Book book = (Book) parameters.get("entity");
            return List.of(
                    Map.of(
                            "title",
                            "Book Record - %s".formatted(book.getName())
                    )
            );
        };
    }

    @BandDef(name = "Book1", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "Book1",
            type = DataSetType.SINGLE,
            entity = @EntityDataSetParameters(
                    parameterAlias = "entity"
            )
    )
    void book1Band();

    @RelatesTo(dataSet = "Book1")
    default FetchPlanProvider book1FetchPlan() {
        return applicationContext -> {
            return applicationContext.getBean(FetchPlans.class).builder(Book.class)
                    .add("name")
                    .add("summary")
                    .add("literatureType", literatureType -> {
                        literatureType.add("name");
                    })
                    .build();
        };
    }

    @BandDef(name = "Authors2", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "Authors2",
            type = DataSetType.MULTI,
            entity = @EntityDataSetParameters(
                    parameterAlias = "entity",
                    nestedCollectionAttribute = "authors"
            )
    )
    void authors2Band();

    @RelatesTo(dataSet = "Authors2")
    default FetchPlanProvider authors2FetchPlan() {
        // !!! we specify fetch plan for Book, not for nested authors
        return applicationContext -> {
            return applicationContext.getBean(FetchPlans.class).builder(Book.class)
                    .add("authors", author -> {
                        author.add("firstName")
                                .add("lastName");
                    })
                    .build();
        };
    }

    // concept of custom factory method for report template
    @TemplateDef(
            outputType = ReportOutputType.PDF,
            isDefault = true
    )
    default Factory<ReportTemplate> defaultTemplate() {
        return applicationContext -> {
            DataManager dataManager = applicationContext.getBean(DataManager.class);
            ResourceLoader resourceLoader = applicationContext.getBean(ResourceLoader.class);

            ReportTemplate t = dataManager.create(ReportTemplate.class);

            byte[] customTemplateFromDb = loadTemplateFileFromDatabase(dataManager);
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

    default byte[] loadTemplateFileFromDatabase(DataManager dataManager) {
        // todo load from database
        return new byte[0];
    }
}
