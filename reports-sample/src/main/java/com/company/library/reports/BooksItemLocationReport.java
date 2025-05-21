package com.company.library.reports;

import com.company.library.entity.BookInstance;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.ErrorConsumer;
import io.jmix.core.*;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.reports.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @InputParameterValidation(alias = "entities")
    default void validateEntities(@ParameterValue List<BookInstance> entities, @Autowired Messages messages, ErrorConsumer errorConsumer) {
        if (entities.size() > 1000) {
            errorConsumer.showErrorMessage(messages.getMessage("report.booksItemLocation.entities.tooBig"));
        }
    }

    @InputParameterTransformation(alias = "entities")
    default List<BookInstance> transformEntities(@ParameterValue List<BookInstance> entities) {
        return entities.stream()
                .sorted(Comparator.comparing(BookInstance::getInventoryNumber))
                .toList();
    }

    @InputParameterDefaultValue(alias = "entities")
    default List<BookInstance> defaultValueEntities(@Autowired DataManager dataManager) {
        return List.of(
                dataManager.load(BookInstance.class)
                        .condition(PropertyCondition.create("inventoryNumber", PropertyCondition.Operation.EQUAL, 155L))
                        .one(),
                dataManager.load(BookInstance.class)
                        .condition(PropertyCondition.create("inventoryNumber", PropertyCondition.Operation.EQUAL, 487L))
                        .one()
        );
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
                    listParameterAlias = "entities",
                    fetchPlan = {"bookPublication.book.name", "libraryDepartment.name"} // OR variant like below
            )
    )
    void bookInstancesBand();

    @DataSetFetchPlan(name = "BookInstances")
    // @SupplyAttribute(target = "BookInstances", attribute = "fetchPlan")
    default FetchPlan bookInstancesFetchPlan(@Autowired FetchPlans fetchPlans) {
        return fetchPlans.builder(BookInstance.class)
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

    @TemplateDef(
            outputType = ReportOutputType.XLSX,
            filePath = "com/company/library/reports/new/BookItemsLocation.xlsx",
            isDefault = true,
            outputNamePattern = "Book Items location.xlsx"
    )
    void defaultTemplate();
}
