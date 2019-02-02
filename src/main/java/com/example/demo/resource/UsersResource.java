package com.example.demo.resource;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mapper.UsersMapper;
import com.example.demo.model.Users;
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
@RequestMapping("/rest/users")
public class UsersResource {
	private UsersMapper userMappers;
	public UsersResource(UsersMapper userMappers) {
		this.userMappers = userMappers;
	}
	@GetMapping("/all")
	public List<Users> getAll(){
		return userMappers.findAll();
	}
	
	 @GetMapping("/saludo")
	   public String get()  throws Exception{
		 JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userMappers.findAll());
			InputStream inputStream = this.getClass().getResourceAsStream("/reports/user.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
	        return "hello world";
	  }
	 @GetMapping("/report")
	 public void report(HttpServletResponse response) throws Exception {
			response.setContentType("text/html");
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userMappers.findAll());
			InputStream inputStream = this.getClass().getResourceAsStream("/reports/reportuser.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
			HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
			exporter.exportReport();
		}
	 @GetMapping("/reporthtml")
	 public void reportsql(HttpServletResponse response) throws Exception {
			 String url = "jdbc:postgresql://localhost:5432/dbportal";
			 Properties props = new Properties();
			 props.setProperty("user","postgres");
			 props.setProperty("password","123456Zxcv");
			 // props.setProperty("ssl","true");
			 Class.forName("org.postgresql.Driver");
			 Connection conn = DriverManager.getConnection(url, props);
			response.setContentType("text/html");
			// Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbportal", "postgres", "123456Zxcv");
			// JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(userMappers.findAll());
			InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
			HtmlExporter exporter = new HtmlExporter(DefaultJasperReportsContext.getInstance());
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
			// SimpleHtmlExporterOutput
			exporter.exportReport();
		}
	 @GetMapping("/reportpdf")
	 public void reportpdf(HttpServletResponse response) throws Exception {
			 String url = "jdbc:postgresql://localhost:5432/dbportal";
			 Properties props = new Properties();
			 props.setProperty("user","postgres");
			 props.setProperty("password","123456Zxcv");
			 Class.forName("org.postgresql.Driver");
			 Connection conn = DriverManager.getConnection(url, props);
			 response.setContentType("application/pdf");
			InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
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
