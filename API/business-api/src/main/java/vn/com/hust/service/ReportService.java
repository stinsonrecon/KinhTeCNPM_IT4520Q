package vn.com.hust.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.hust.common.AppException;
import vn.com.hust.common.CommonConstant;
import vn.com.hust.dto.ReportRequestObject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class ReportService
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportService.class);

	public ReportRequestObject generatorReport(ReportRequestObject input, Map<String, Object> parameters) throws AppException, SQLException
	{
		String nomeMetodo = ".generatorReport()";
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.BEGIN_LOG);
		
		ReportRequestObject response = new ReportRequestObject(input.getTemplateFile(), input.getFileType(), input.getFileName(), input.getDestinationPath(),
				input.getTypeOfReport());
		Connection connection = null;
		try
		{
			connection = jdbcTemplate.getDataSource().getConnection();
			vn.com.hust.report.ReportService reportService = new vn.com.hust.report.ReportService();
			response = reportService.generatorReport(input, parameters, connection);
		}
		catch (Exception ex)
		{

			LOG.error(ex.getMessage(), ex);
			LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
			
			throw new AppException(CommonConstant.CREATE_REPORT_ERROR_CODE, CommonConstant.CREATE_REPORT_ERROR_CODE, CommonConstant.CREATE_REPORT_ERROR_DES);
		}
		finally
		{
			if(connection != null) {
				connection.close();
			}
		}
		LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
		return response;
	}

	public String getMediaType(String fileType) throws AppException
	{
		if (fileType.equalsIgnoreCase(".pdf"))
		{
			return "application/pdf";
		}
		else if (fileType.equalsIgnoreCase(".xlsx"))
		{
			return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		}
		else if (fileType.equalsIgnoreCase(".xls"))
		{
			return "application/vnd.ms-excel";
		}
		else if (fileType.equalsIgnoreCase(".docx"))
		{
			return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		}
		else
		{
			throw new AppException(CommonConstant.CREATE_REPORT_INV_FILETYPE_ERROR_CODE, CommonConstant.CREATE_REPORT_INV_FILETYPE_ERROR_CODE,
					CommonConstant.CREATE_REPORT_INV_FILETYPE_ERROR_DES);
		}
	}
}
