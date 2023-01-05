package vn.com.hust.report;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.hust.common.CommonConstant;
import vn.com.hust.dto.ReportRequestObject;
import java.sql.Connection;
import java.util.*;

public class ReportService
{

	private static final Logger LOG = LoggerFactory.getLogger(ReportService.class);

	public ReportRequestObject generatorReport(ReportRequestObject input, Map<String, Object> parameters, Connection connection) throws JRException
	{
		String nomeMetodo = ".generatorReport()";
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.BEGIN_LOG);

		ReportRequestObject response = new ReportRequestObject(input.getTemplateFile(), input.getFileType(), input.getFileName(), input.getDestinationPath(),
				input.getTypeOfReport());
		try
		{
			LOG.info("input.getTemplateFile(): " + input.getTemplateFile());
			JasperReport jasperReport = JasperCompileManager.compileReport(input.getTemplateFile());
			LOG.info("jasperReport: " + jasperReport);
			LOG.info("parameters: " + jasperReport);

			for (Object obj : parameters.entrySet())
			{
				try
				{
					Map.Entry<String, String> entry = (Map.Entry) obj;
					LOG.info(entry.getKey() + " : " + entry.getValue());
				} catch (Exception ex)
				{
					continue;
				}
			}
			if (input.getFileType().equals(".xls") || input.getFileType().equals(".xlsx"))
			{
				parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			}

			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, connection);
			exportToFile(print, input.getFileType(), input.getDestinationPath(), input.getFileName());
			response.setDownloadLink(input.getDestinationPath() + input.getFileName() + input.getFileType());
		} catch (JRException ex)
		{
			LOG.error(ex.getMessage(), ex);
			LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
			throw ex;
		}
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
		return response;
	}

	public void exportToFile(JasperPrint print, String fileType, String destinationPath, String fileName) throws JRException
	{
		String nomeMetodo = ".exportToFile()";
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);

		ExporterInput exporterInput = new SimpleExporterInput(print);

		if (fileType.equalsIgnoreCase(".pdf"))
		{
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
			exporter.setExporterOutput(exporterOutput);

			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		if (fileType.equalsIgnoreCase(".xlsx"))
		{
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
			exporter.setExporterOutput(exporterOutput);
			SimpleXlsxExporterConfiguration configuration = new SimpleXlsxExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		if (fileType.equalsIgnoreCase(".xls"))
		{
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
			exporter.setExporterOutput(exporterOutput);
			SimpleXlsExporterConfiguration configuration = new SimpleXlsExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		if (fileType.equalsIgnoreCase(".docx"))
		{
			JRDocxExporter exporter = new JRDocxExporter();
			exporter.setExporterInput(exporterInput);
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(destinationPath + fileName + fileType);
			exporter.setExporterOutput(exporterOutput);
			SimpleDocxExporterConfiguration configuration = new SimpleDocxExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		}
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
	}
}
