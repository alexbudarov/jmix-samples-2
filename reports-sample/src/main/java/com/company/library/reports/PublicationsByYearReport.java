package com.company.library.reports;

import com.company.library.reports.annotation.*;
import io.jmix.reports.entity.*;
import io.jmix.reports.yarg.formatters.CustomReport;
import io.jmix.reports.yarg.structure.BandData;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;


@ReportDef(
        name = "Publications by year",
        code = "BY_YEAR_STATS",
        group = DemoReportGroup.class,
        description = "Example with custom template",
        uuid = "01970c9e-6236-79c1-8297-bd04a2d9a542"
)
@InputParameterDef(
        alias = "startYear",
        name = "Start Year",
        type = ParameterType.NUMERIC,
        parameterClassName = Integer.class,
        required = true,
        defaultValue = "2000"
)
@BandDef(name = "Root", root = true, orientation = Orientation.HORIZONTAL)
@BandDef(name = "header", parent = "Root", orientation = Orientation.HORIZONTAL)
@BandDef(
        name = "stats",
        parent = "Root",
        orientation = Orientation.HORIZONTAL,
        dataSets = @DataSetDef(
                type = DataSetType.SQL,
                query = """
                    select YEAR_ as PUB_YEAR, count(*) as PUB_COUNT
                    from BOOK_PUBLICATION
                    where YEAR_ >= ${startYear}
                    group by YEAR_
                    order by YEAR_ DESC
"""
        )
)
@TemplateDef(
        code = "XML",
        outputType = ReportOutputType.CUSTOM,
        isDefault = true,
        outputNamePattern = "Stats from ${Root.startYear}.xml",
        custom = @CustomTemplateParameters(
                enabled = true,
                definedBy = CustomTemplateDefinedBy.SCRIPT // todo add constant CODE
        )
)
public class PublicationsByYearReport {

    @RelatesTo(template = "XML")
    public CustomReport customTemplate() {
        return (report, rootBand, params) -> {
            return renderXml(rootBand);
        };
    }

    private byte[] renderXml(BandData rootBand) {
        Document document = DocumentHelper.createDocument();
        document.addElement(rootBand.getName());
        renderBandData(document.getRootElement(), rootBand.getData());

        for (BandData bandData : rootBand.getChildrenList()) {
            renderSubBand(document.getRootElement(), bandData);
        }

        byte[] byteContent = convertToByteArray(document);
        return byteContent;
    }

    private byte[] convertToByteArray(Document document) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer;
        try {
            writer = new XMLWriter(baos, format);
            writer.write(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return baos.toByteArray();
    }

    private void renderSubBand(Element rootElement, BandData bandData) {
        if (bandData.getData().isEmpty() && bandData.getChildrenBands().isEmpty()) {
            return;
        }
        Element bandElement = rootElement.addElement(bandData.getName());
        renderBandData(bandElement, bandData.getData());

        for (BandData subSubBand : bandData.getChildrenList()) {
            renderSubBand(bandElement, subSubBand);
        }
    }

    private void renderBandData(Element bandElement, Map<String, Object> data) {
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String formattedValue = String.valueOf(entry.getValue());
            bandElement.addAttribute(entry.getKey(), formattedValue);
        }
    }
}

