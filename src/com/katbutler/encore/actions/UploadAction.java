package com.katbutler.encore.actions;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.katbutler.encore.model.EncoreError;
import com.katbutler.encore.xml.XmlWriter;
import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = 2056187312874913258L;
	
	private HttpServletRequest request;
	
	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
 
	private String xml = "";
	private String errorXml = "";

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getErrorXml() {
		return errorXml;
	}

	public void setErrorXml(String errorXml) {
		this.errorXml = errorXml;
	}

	
	public String getFileUploadContentType() {
		return fileUploadContentType;
	}
 
	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}
 
	public String getFileUploadFileName() {
		return fileUploadFileName;
	}
 
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}
 
	public File getFileUpload() {
		return fileUpload;
	}
 
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpServletRequest getServletRequest() {
		return request;
	}
	
	/**
	 * This method is called after the file is uploaded
	 */
	public String execute() throws Exception{

		if (getFileUpload() != null) {
			System.out.println("Uploaded File: " + getFileUpload().getAbsolutePath());
			
			String imageName = "/images/Image-" + new Date().getTime() + ".encore";
			File destFile = new File(getServletRequest().getServletContext().getRealPath("/") + imageName);
			FileUtils.copyFile(getFileUpload(), destFile);
			
			setXml("<File><FilePath>" + imageName + "</FilePath></File>");
		} else {
			System.out.println("Error: Did not upload file...");

			EncoreError error = new EncoreError("Could not upload file.");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));

			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
}
