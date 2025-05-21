package com.company.library.reports;

import com.company.library.entity.BookPublication;
import com.company.library.reports.annotation.*;
import com.company.library.security.FullAccessRole;
import com.company.library.security.UserManagementRole;
import com.company.library.view.bookpublication.BookPublicationListView;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ParameterType;
import io.jmix.reports.entity.ReportOutputType;
import org.springframework.beans.factory.annotation.Autowired;

@ReportDef(
        name = "Publication details",
        code = "PUBL",
        group = DemoReportGroup.class
)
public interface PublicationDetailsReport {

    @InputParameterDef(
            alias = "entity",
            name = "Entity",
            type = ParameterType.ENTITY,
            required = true,
            entityParameters = @EntityParameterDef(entityClass = BookPublication.class)
    )
    void entityInputParameter();

    // maybe make implicit?
    @BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
    void rootBand();

    @BandDef(name = "BookPublication", parent = "Root", orientation = Orientation.HORIZONTAL)
    @DataSetDef(
            name = "BookPublication",
            type = DataSetType.SINGLE,
            entity = @EntityDataSetParameters(
                    parameterAlias = "entity"
            )
    )
    void bookPublicationBand();

    @DataSetFetchPlan(name = "BookPublication")
    default FetchPlan bookPublicationFetchPlan(@Autowired FetchPlans fetchPlans) {
        return fetchPlans.builder(BookPublication.class)
                .add("year")
                .add("book", FetchPlan.INSTANCE_NAME)
                .add("publisher", FetchPlan.INSTANCE_NAME)
                .add("city", FetchPlan.INSTANCE_NAME)
                .build();
    }

    @TemplateDef(
            outputType = ReportOutputType.DOCX,
            filePath = "com/company/library/reports/new/Template-for-PublicationDetailsReport.docx",
            isDefault = true,
            outputNamePattern = "Report for entity Book publication.docx"
    )
    void defaultTemplate();

    @TemplateDef(
            code = "publication-template",
            outputType = ReportOutputType.PDF,
            filePath = "com/company/library/reports/new/Template-for-PublicationDetailsReport.docx",
            outputNamePattern = "Publication details.pdf"
    )
    void publicationTemplateTemplate();

    @AvailableInViews(viewClasses = BookPublicationListView.class)
    void views();

    @AvailableForRoles(roleClasses = {FullAccessRole.class, UserManagementRole.class})
    void roles();
}
