package com.example.demo.report;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.storage.StorageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
@Slf4j
@Component
@RequiredArgsConstructor
public class JasperReportsService implements ReportService {
	private  StorageService storageService;


	@Override
	public byte[] generatePDFReport(String inputFileName, Map<String, Object> params) {
		return generatePDFReport(inputFileName, params, new JREmptyDataSource());
	}


	@Override
	public byte[] generatePDFReport(String inputFileName, Map<String, Object> params,
			JRDataSource dataSource) {
		byte[] bytes = null;
		JasperReport jasperReport = null;
		try (ByteArrayOutputStream byteArray = new ByteArrayOutputStream()) {
			// Check if a compiled report exists
			if (storageService.jasperFileExists(inputFileName)) {
				jasperReport = (JasperReport) JRLoader
						.loadObject(storageService.loadJasperFile(inputFileName));
			}
			// Compile report from source and save
			else {
				String jrxml = storageService.loadJrxmlFile(inputFileName);
				// log.info("{} loaded. Compiling report");
				jasperReport = JasperCompileManager.compileReport(jrxml);
				// Save compiled report. Compiled report is loaded next time
				JRSaver.saveObject(jasperReport,
						storageService.loadJasperFile(inputFileName));
			}
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
					dataSource);
			bytes = JasperExportManager.exportReportToPdf(jasperPrint);
		}
		catch (JRException | IOException e) {
			e.printStackTrace();
			// log.error("Encountered error when loading jasper file", e);
		}

		return bytes;
	}
}