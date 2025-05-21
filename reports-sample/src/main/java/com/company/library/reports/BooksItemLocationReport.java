package com.company.library.reports;

import com.company.library.entity.BookInstance;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.DefaultValueProvider;
import com.company.library.reports.api.FetchPlanProvider;
import com.company.library.reports.api.ParameterTransformer;
import com.company.library.reports.api.ParameterValidator;
import io.jmix.core.*;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.reports.entity.*;

import java.util.Comparator;
import java.util.List;

@ReportDef(
        name = "Book Items location",
        code = "BOOK_ITEMS_LOCATION",
        group = DemoReportGroup.class
)
public interface BooksItemLocationReport {

    @InputParameterDef(
            alias = "entities",
            name = "Entities",
            type = ParameterType.ENTITY_LIST,
            required = true,
            entityParameters = @EntityParameterDef(entityClass = BookInstance.class)
    )
    void entitiesInputParameter();

    @RelatesTo(inputParameter = "entities")
    default ParameterValidator<List<BookInstance>> validateEntities() {
        return (value, errorConsumer, applicationContext) -> {
            if (value.size() > 1000) {
                Messages messages = applicationContext.getBean(Messages.class);
                errorConsumer.addError(messages.getMessage("report.booksItemLocation.entities.tooBig"));
            }
        };
    }

    @RelatesTo(inputParameter = "entities")
    default ParameterTransformer<List<BookInstance>> transformEntities() {
        return (value, params, applicationContext) -> {
            return value.stream()
                    .sorted(Comparator.comparing(BookInstance::getInventoryNumber))
                    .toList();
        };
    }

    @RelatesTo(inputParameter = "entities")
    default DefaultValueProvider<List<BookInstance>> defaultValueEntities() {
        return applicationContext -> {
            DataManager dataManager = applicationContext.getBean(DataManager.class);
            return List.of(
                    dataManager.load(BookInstance.class)
                            .condition(PropertyCondition.create("inventoryNumber", PropertyCondition.Operation.EQUAL, 155L))
                            .one(),
                    dataManager.load(BookInstance.class)
                            .condition(PropertyCondition.create("inventoryNumber", PropertyCondition.Operation.EQUAL, 487L))
                            .one()
            );
        };
    }

    // maybe make implicit?
    @BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
    void rootBand();

    @BandDef(name = "headerBookInstances", parent = "Root", orientation = Orientation.HORIZONTAL)
    void headerBookInstancesBand();

    @BandDef(name = "BookInstances", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "BookInstances",
            type = DataSetType.MULTI,
            entity = @EntityDataSetParameters(
                    listParameterAlias = "entities"
            )
    )
    void bookInstancesBand();

    @RelatesTo(dataSet = "BookInstances")
    default FetchPlanProvider bookInstancesFetchPlan() {
        return applicationContext -> {
            return applicationContext.getBean(FetchPlans.class).builder(BookInstance.class)
                    .add("bookPublication", publication -> {
                        publication.add("book", book -> {
                            book.add("name");
                        });
                    })
                    .add("libraryDepartment", department -> {
                        department.add("name");
                    })
                    .build();
        };
    }

    @TemplateDef(
            outputType = ReportOutputType.XLSX,
            filePath = "com/company/library/reports/new/BookItemsLocation.xlsx",
            isDefault = true,
            outputNamePattern = "Book Items location.xlsx"
    )
    void defaultTemplate();
}
