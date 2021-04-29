package com.kl.demo.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * jasperPath：模板路径
 * params：向报表传递的参数
 * datasource：JRBeanCollectionDataSource处理不同来源的数据，统一输出为JasperReport格式的数据
 */
public class UtilJasperExport
{
    private static final String ENCODING = "utf-8";
    private static final String XLS_CONTENT_TYPE = "application/x-msdownload";

    /**
     * 输出报表的格式为html
     */
    public static void exportToHtml(String jasperPath, Map<String, Object> params, JRDataSource datasource, HttpServletRequest request, HttpServletResponse response) throws JRException, IOException
    {
        ClassPathResource classPathResource = new ClassPathResource(jasperPath);
        InputStream inputStream=classPathResource.getInputStream();
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, datasource);
        request.setCharacterEncoding(ENCODING);
        response.setCharacterEncoding(ENCODING);

        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
        exporter.exportReport();
    }

    /**
     * 输出报表的格式为Excel(可以默认设置为.xls或 .xlsx)
     */
    public static void exportToXls(String jasperPath, String fileName, Map<String, Object> params, JRDataSource datasource, HttpServletResponse response) throws IOException, JRException
    {
        File reportFile = new File(new ClassPathResource(jasperPath).getURI());
        JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, datasource);
        response.setCharacterEncoding(ENCODING);
        response.setContentType(XLS_CONTENT_TYPE);
        //下面一行的设置作用：浏览器会提示保存还是打开，如果下保存，会提供一个默认的文件名
        response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO-8859-1") + ".xls");

        JRAbstractExporter exporter=new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

}
