package com.company.library.reports;

import com.company.library.entity.BookPublication;
import com.company.library.reports.annotation.*;
import com.company.library.reports.api.FetchPlanProvider;
import com.company.library.security.FullAccessRole;
import com.company.library.security.UserManagementRole;
import com.company.library.view.bookpublication.BookPublicationListView;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;

@ReportDef(
        name = "Publication details",
        code = "PUBL",
        group = DemoReportGroup.class
)
@InputParameterDef(
        alias = "entity",
        name = "Entity",
        type = ParameterType.ENTITY,
        required = true,
        entity = @EntityParameterDef(entityClass = BookPublication.class)
)
@BandDef(
        name = "Root",
        root = true,
        orientation = Orientation.HORIZONTAL
)
@BandDef(
        name = "BookPublication",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                name = "BookPublication",
                type = DataSetType.SINGLE,
                entity = @EntityDataSetDef(
                        parameterAlias = "entity"
                )
        )
)
@TemplateDef(
        code = "DEFAULT",
        outputType = ReportOutputType.DOCX,
        filePath = "com/company/library/reports/new/Template-for-PublicationDetailsReport.docx",
        isDefault = true,
        outputNamePattern = "Report for entity Book publication.docx"
)
@TemplateDef(
        code = "publication-template",
        outputType = ReportOutputType.PDF,
        filePath = "com/company/library/reports/new/Template-for-PublicationDetailsReport.docx",
        outputNamePattern = "Publication details.pdf"
)
@AvailableInViews(viewClasses = BookPublicationListView.class)
@AvailableForRoles(roleClasses = {FullAccessRole.class, UserManagementRole.class})
public class PublicationDetailsReport {

    private final FetchPlans fetchPlans;

    public PublicationDetailsReport(FetchPlans fetchPlans) {
        this.fetchPlans = fetchPlans;
    }

    @RelatesTo(dataSet = "BookPublication")
    public FetchPlanProvider bookPublicationFetchPlan() {
        return () -> fetchPlans.builder(BookPublication.class)
                .add("year")
                .add("book", FetchPlan.INSTANCE_NAME)
                .add("publisher", FetchPlan.INSTANCE_NAME)
                .add("city", FetchPlan.INSTANCE_NAME)
                .build();
    }
}
