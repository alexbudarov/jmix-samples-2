package com.company.library.reports;

import com.company.library.reports.annotation.*;
import com.company.library.reports.api.HtmlTemplateType;
import io.jmix.reports.entity.DataSetType;
import io.jmix.reports.entity.Orientation;
import io.jmix.reports.entity.ReportOutputType;

@ReportDef(
        name = "All Books",
        code = "ALL_BOOKS",
        group = DemoReportGroup.class,
        description = "Example of using multi data sets, link field, separate data store, HTML template",
        uuid = "01970d3e-5c8e-741c-bf98-0b242cfde70d"
)
@BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
@BandDef(
        name = "Books",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = {
                @DataSetDef(
                        name = "Main",
                        type = DataSetType.JPQL,
                        query = """                    
                    select
                    e.name as "name",
                    literatureType.name as "literatureType.name"
                    from Book e
                    left join e.literatureType literatureType
                    """
                ),
                @DataSetDef(
                        name = "Picture",
                        type = DataSetType.JPQL,
                        query = """                        
                        select
                        bp.bookName as "name",
                        bp.picturePath as "picturePath"
                        from BookPicture bp
                        """,
                        linkParameterName = "name",
                        dataStore = "pictures"
                )
        }
)
@TemplateDef(
        code = "DEFAULT",
        outputType = ReportOutputType.PDF,
        isDefault = true,
        filePath = "com/company/library/reports/new/Template-AllBooks.html",
        outputNamePattern = "All Books.pdf",
        htmlTemplateType = HtmlTemplateType.FREEMARKER
)
public class AllBooksReport {
}
