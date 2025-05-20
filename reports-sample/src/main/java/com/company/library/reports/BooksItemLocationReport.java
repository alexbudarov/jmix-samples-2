package com.company.library.reports;

import com.company.library.entity.BookInstance;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.*;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.reports.entity.ParameterType;
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
                        .condition(PropertyCondition.create("inventoryNumber", PropertyCondition.Operation.EQUAL,487L))
                        .one()
        );
    }
}
