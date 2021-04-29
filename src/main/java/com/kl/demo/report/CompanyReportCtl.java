package com.kl.demo.report;

import com.kl.demo.service.CompanyService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("CompanyReport")
public class CompanyReportCtl
{
    @Resource(name="companyService")
    private CompanyService cs;

    @RequestMapping("showHtml")
    public String showIndexPage()
    {
        /**
         * 用于公共页面中的公司报表网页导航菜单
         */
        return "report/CompanyReport.html";
    }

    @RequestMapping("/html")
    public void reportHtml(@RequestBody(required = false) Map<String,Object> reqMap, HttpServletRequest request, HttpServletResponse response) throws IOException, JRException
    {
        /**
         * 报表以【网页html】的方式进行输出
         */
        JRDataSource datasource=new JRBeanCollectionDataSource(cs.findAll());
        UtilJasperExport.exportToHtml("static/ReportTemplates/company5.jasper", reqMap, datasource, request, response);
    }

    @RequestMapping("/excel")
    public void reportExcel(@RequestBody(required = false) Map<String,Object> reqMap,HttpServletRequest request,HttpServletResponse response) throws IOException, JRException
    {
        /**
         * 报表以【Excel】的方式进行输出
         */
        JRDataSource datasource=new JRBeanCollectionDataSource(cs.findAll());
        UtilJasperExport.exportToXls("static/ReportTemplates/company5.jasper","公司列表", reqMap, datasource, response);
    }

}
