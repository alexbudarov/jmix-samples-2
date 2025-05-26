package com.company.library.reports.old;

import io.jmix.reports.yarg.formatters.CustomReport;
import io.jmix.reports.yarg.structure.BandData;
import io.jmix.reports.yarg.structure.Report;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Sample abstract XML renderer for runtime Reports.
 */
@SuppressWarnings("unused")
public class XmlReportRenderer implements CustomReport {

    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {
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
