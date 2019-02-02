package com.example.demo.report;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;


public interface ReportService {
	/**
	 * Generates a HTML report with the given input file. Uses a JREmptyDataSource
	 * 
	 * @param inputFileName
	 *            report source file without extension
	 * @param params
	 *            report parameters
	 * @return the byte[] containing the PDF
	 */
	byte[] generatePDFReport(String inputFileName, Map<String, Object> params);

	/**
	 * Generates a HTML report with the given input file
	 * 
	 * @param inputFileName
	 *            report source file without extension
	 * @param params
	 *            report parameters
	 * @param dataSource
	 *            the source of data
	 * @return the byte[] containing the PDF
	 */
	byte[] generatePDFReport(String inputFileName, Map<String, Object> params, JRDataSource dataSource);
}