package com.example.demo.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
  // String url = "jdbc:postgresql://vps229753.vps.ovh.ca:5432/db_presupuesto";
  // String contrasena = "123456Zxcv";

  String url = "jdbc:postgresql://localhost:5432/db_presupuesto";
  // String contrasena = "postgres";
  String contrasena = "Sistemas1";

  public ReportsResource() {
  }

  @CrossOrigin
  @GetMapping("/gastosdiarios")
  public void gastosdiarios(HttpServletResponse response, @RequestParam String fechaini, @RequestParam String fechafin,
      @RequestParam Integer cod_caja) throws Exception {
    try {
      
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_gastos_diarios.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

      HashMap params = new HashMap();
      params.put("fec_ini", fechaini);
      params.put("fec_fin", fechafin);
      params.put("cod_caja", cod_caja);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }
  @CrossOrigin
  @GetMapping("/gastosdiariosporfactura")
  public void gastosdiariosporfactura(HttpServletResponse response, @RequestParam String fechaini, @RequestParam String fechafin,
      @RequestParam Integer cod_caja) throws Exception {
    try {
      
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_gastos_diarios_por_factura.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

      HashMap params = new HashMap();
      params.put("fec_ini", fechaini);
      params.put("fec_fin", fechafin);
      params.put("cod_caja", cod_caja);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }


  @CrossOrigin
  @GetMapping("/gastosdiariosporarea")
  public void gastosdiariosporarea(HttpServletResponse response, @RequestParam String fechaini, @RequestParam String fechafin,
      @RequestParam Integer cod_area) throws Exception {
    try {
      
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_gastos_diarios_por_area.jrxml");
      InputStream subReportinputStream = this.getClass().getResourceAsStream("/reports/rpt_gastos_diarios_por_area_ingresos.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
      JasperReport jasperSubReport = JasperCompileManager.compileReport(subReportinputStream);

      HashMap params = new HashMap();
      params.put("fec_ini", fechaini);
      params.put("fec_fin", fechafin);
      params.put("cod_area", cod_area);
      params.put("subReportIngresosCaja", jasperSubReport);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }


  @CrossOrigin
  @GetMapping("/presupuestoasignado")
  public void presupuestoasignado(HttpServletResponse response, @RequestParam Integer cod_mes,
      @RequestParam Integer cod_area, @RequestParam Integer cod_subarea) throws Exception {
    try {
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
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
      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }

  @CrossOrigin
  @GetMapping("/presupuestoaprobado")
  public void presupuestoaprobado(HttpServletResponse response, @RequestParam Integer cod_mes,
      @RequestParam Integer cod_area, @RequestParam Integer cod_subarea) throws Exception {
    try {
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_presupuesto_segun_aprobado.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

      HashMap params = new HashMap();
      params.put("cod_mes", cod_mes);
      params.put("cod_area", cod_area);
      params.put("cod_subarea", cod_subarea);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }

  @CrossOrigin
  @GetMapping("/presupuestoaprobadogerencial")
  public void presupuestoaprobadogerencial(HttpServletResponse response, @RequestParam Integer cod_mes,
      @RequestParam Integer cod_area, @RequestParam Integer cod_subarea) throws Exception {
    try {
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_presupuesto_segun_aprobado_gerencial.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

      HashMap params = new HashMap();
      params.put("cod_mes", cod_mes);
      params.put("cod_area", cod_area);
      params.put("cod_subarea", cod_subarea);
      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }

  }

  @CrossOrigin
  @GetMapping("/presupuestoclasificacion")
  public void presupuestoclasificacion(HttpServletResponse response, @RequestParam Integer cod_mes,
      @RequestParam Integer cod_clasificacion) throws Exception {
    try {
      Properties props = new Properties();
      props.setProperty("user", "postgres");
      props.setProperty("password", contrasena);
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(url, props);
      response.setContentType("application/pdf");
      InputStream inputStream = this.getClass().getResourceAsStream("/reports/rpt_presupuesto_por_clasificacion.jrxml");
      JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

      HashMap params = new HashMap();
      params.put("cod_mes", cod_mes);
      params.put("cod_clasificacion", cod_clasificacion);

      JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
      // JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
      // conn);
      JRPdfExporter exporter = new JRPdfExporter();

      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      // exporter.setExporterOutput(new
      // SimpleOutputStreamExporterOutput("userReport.pdf")); //genera un pdf en la
      // ruta raiz
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
      SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
      reportConfig.setSizePageToContent(true);
      reportConfig.setForceLineBreakPolicy(false);

      SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
      exportConfig.setMetadataAuthor("baeldung");
      exportConfig.setEncrypted(true);
      exportConfig.setAllowedPermissionsHint("PRINTING");

      exporter.setConfiguration(reportConfig);
      exporter.setConfiguration(exportConfig);

      exporter.exportReport();
    } catch (Exception exc) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error al generar el reporte", null);
    }
  }
}
