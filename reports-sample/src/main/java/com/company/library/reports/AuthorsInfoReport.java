package com.company.library.reports;

import com.company.library.reports.annotation.*;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ReportOutputType;

@ReportDef(
        name = "Authors Info",
        code = "authors-info",
        group = DemoReportGroup.class,
        description = "Example report with Table template"
)
@BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
@BandDef(
        name = "Authors",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                type = DataSetType.JPQL,
                query = """
                    select
                    e.createdDate as "createdDate",
                    e.createdBy as "createdBy",
                    e.firstName as "firstName",
                    e.lastName as "lastName"
                    from Author e
                    """
        )
)
@TemplateDef(
        code = "DEFAULT",
        outputType = ReportOutputType.TABLE,
        isDefault = true,
        table = @TemplateTableDef(
                bands = @TableBandDef(
                        bandName = "Authors",
                        columns = {
                                @TableColumnDef(
                                        key = "createdDate",
                                        caption = "msg://com.company.library.entity/Author.createdDate"
                                ),
                                @TableColumnDef(
                                        key = "createdBy",
                                        caption = "com.company.library.entity/Author.createdBy"
                                ),
                                @TableColumnDef(
                                        key = "firstName",
                                        caption = "com.company.library.entity/Author.firstName"
                                ),
                                @TableColumnDef(
                                        key = "lastName",
                                        caption = "com.company.library.entity/Author.lastName"
                                ),
                        }
                )
        )
)
@ValueFormatDef(
        band = "Authors",
        field = "createdDate",
        format = "dd.MM.yyyy HH:mm:ss"
)
public class AuthorsInfoReport {
}
