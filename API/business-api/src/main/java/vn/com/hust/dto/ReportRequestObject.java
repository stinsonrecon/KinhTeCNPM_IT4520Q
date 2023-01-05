package vn.com.hust.dto;

import java.io.File;

public class ReportRequestObject 
{

	private String code ="";
	private String templateFile = "";
	private String fileType = "";
	private String fileName = "";
	private String destinationPath = "";
	private String staffName = "";
	private String shopName = "";
	private String typeOfReport = "";
	private String downloadLink = "";
	private String mimeType = "";
	private long fileSize = 0L;
	private File file;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ReportRequestObject()
	{
		
	}

	public ReportRequestObject(String templateFile, String fileType, String fileName, String destinationPath,
                               String typeOfReport)
	{
		this.templateFile = templateFile;
		this.fileType = fileType;
		this.fileName = fileName;
		this.destinationPath = destinationPath;
		this.typeOfReport = typeOfReport;
	}
	
	public ReportRequestObject(String templateFile, String fileType, String fileName, String destinationPath,
                               String typeOfReport, String code)
	{
		this.templateFile = templateFile;
		this.fileType = fileType;
		this.fileName = fileName;
		this.destinationPath = destinationPath;
		this.typeOfReport = typeOfReport;
		this.code = code;
	}

	public ReportRequestObject(String templateFile, String fileType, String fileName, String destinationPath,
                               String staffName, String shopName, String typeOfReport) {
		this.templateFile = templateFile;
		this.fileType = fileType;
		this.fileName = fileName;
		this.destinationPath = destinationPath;
		this.staffName = staffName;
		this.shopName = shopName;
		this.typeOfReport = typeOfReport;
	}

	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDestinationPath() {
		return destinationPath;
	}
	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTypeOfReport() {
		return typeOfReport;
	}
	public void setTypeOfReport(String typeOfReport) {
		this.typeOfReport = typeOfReport;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
