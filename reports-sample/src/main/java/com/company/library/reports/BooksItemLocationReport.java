package com.company.library.reports;

import com.company.library.entity.BookInstance;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.DefaultValueProvider;
import com.company.library.reports.api.FetchPlanProvider;
import com.company.library.reports.api.ParameterTransformer;
import com.company.library.reports.api.ParameterValidator;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlans;
import io.jmix.core.Messages;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;

import java.util.Comparator;
import java.util.List;

@ReportDef(
        name = "Book Items location",
        code = "BOOK_ITEMS_LOCATION",
        group = DemoReportGroup.class
)
@InputParameterDef(
        alias = "entities",
        name = "Entities",
        type = ParameterType.ENTITY_LIST,
        required = true,
        entity = @EntityParameterDef(entityClass = BookInstance.class)
)
@BandDef(
        name = "Root",
        root = true,
        orientation = Orientation.HORIZONTAL
)
@BandDef(
        name = "headerBookInstances",
        parent = "Root",
        orientation = Orientation.HORIZONTAL
)
@BandDef(
        name = "BookInstances",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                name = "BookInstances",
                type = DataSetType.MULTI,
                entity = @EntityDataSetDef(
                        listParameterAlias = "entities"
                )
        )
)
@TemplateDef(
        code = "default",
        outputType = ReportOutputType.XLSX,
        filePath = "com/company/library/reports/new/BookItemsLocation.xlsx",
        isDefault = true,
        outputNamePattern = "Book Items location.xlsx"
)
public class BooksItemLocationReport {
    private final Messages messages;
    private final DataManager dataManager;
    private final FetchPlans fetchPlans;

    public BooksItemLocationReport(Messages messages, DataManager dataManager, FetchPlans fetchPlans) {
        this.messages = messages;
        this.dataManager = dataManager;
        this.fetchPlans = fetchPlans;
    }

    @InputParameterDelegate(alias = "entities")
    public ParameterValidator<List<BookInstance>> validateEntities() {
        return (value, errorConsumer) -> {
            if (value.size() > 1000) {
                errorConsumer.addError(messages.getMessage("report.booksItemLocation.entities.tooBig"));
            }
        };
    }

    @InputParameterDelegate(alias = "entities")
    public ParameterTransformer<List<BookInstance>> transformEntities() {
        return (value, params) -> {
            return value.stream()
                    .sorted(Comparator.comparing(BookInstance::getInventoryNumber))
                    .toList();
        };
    }

    @InputParameterDelegate(alias = "entities")
    public DefaultValueProvider<List<BookInstance>> defaultValueEntities() {
        return () -> {
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

    @DataSetDelegate(name = "BookInstances")
    public FetchPlanProvider bookInstancesFetchPlan() {
        return () -> fetchPlans.builder(BookInstance.class)
                .add("bookPublication", publication -> {
                    publication.add("book", book -> {
                        book.add("name");
                    });
                })
                .add("libraryDepartment", department -> {
                    department.add("name");
                })
                .build();
    }
}
