package com.example.demo.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
@RestController
@RequestMapping("/rest/reports")
public class ReportsResource {
	public ReportsResource() {	
	}

	 @GetMapping("/gastosdiarios")
	 public void gastosdiarios(HttpServletResponse response, @RequestParam String fechaini, @RequestParam String fechafin) throws Exception {
			 String url = "jdbc:postgresql://vps229753.vps.ovh.ca:5432/db_presupuesto";
			 Properties props = new Properties();
			 props.setProperty("user","postgres");
			 props.setProperty("password","123456Zxcv");
			 Class.forName("org.postgresql.Driver");
			 Connection conn = DriverManager.getConnection(url, props);
			 response.setContentType("application/pdf");
			InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_gastos_diarios.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

			 HashMap params = new HashMap();
       params.put("fec_ini", fechaini);
       params.put("fec_fin", fechafin);
			
       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
			JRPdfExporter exporter = new JRPdfExporter();
			 
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			// exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la ruta raiz
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); 
			SimplePdfReportConfiguration reportConfig
			  = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);
			 
			SimplePdfExporterConfiguration exportConfig
			  = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("baeldung");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");
			 
			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);
			 
			exporter.exportReport();
        }
        @GetMapping("/presupuestoasignado")
        public void presupuestoasignado(HttpServletResponse response, @RequestParam Integer cod_mes, @RequestParam Integer cod_area,@RequestParam Integer cod_subarea) throws Exception {
                
                String url = "jdbc:postgresql://vps229753.vps.ovh.ca:5432/db_presupuesto";
                Properties props = new Properties();
                props.setProperty("user","postgres");
                props.setProperty("password","123456Zxcv");
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(url, props);
                response.setContentType("application/pdf");
               InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_presupuesto_segun_asignado.jrxml");
               JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
   
                HashMap params = new HashMap();
                params.put("cod_mes", cod_mes);
                params.put("cod_area", cod_area);
                params.put("cod_subarea", cod_subarea);
              JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
               // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
               JRPdfExporter exporter = new JRPdfExporter();
                
               exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
               // exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la ruta raiz
               exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream())); 
               SimplePdfReportConfiguration reportConfig
                 = new SimplePdfReportConfiguration();
               reportConfig.setSizePageToContent(true);
               reportConfig.setForceLineBreakPolicy(false);
                
               SimplePdfExporterConfiguration exportConfig
                 = new SimplePdfExporterConfiguration();
               exportConfig.setMetadataAuthor("baeldung");
               exportConfig.setEncrypted(true);
               exportConfig.setAllowedPermissionsHint("PRINTING");
                
               exporter.setConfiguration(reportConfig);
               exporter.setConfiguration(exportConfig);
                
               exporter.exportReport();
           }
}
