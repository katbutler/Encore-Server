package com.katbutler.encore.actions;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;

import com.katbutler.encore.model.EncoreError;
import com.katbutler.encore.model.User;
import com.katbutler.encore.persistence.HibernateDatabaseUserManager;
import com.katbutler.encore.xml.XmlWriter;
import com.opensymphony.xwork2.ActionSupport;

public class AuthenticationAction extends ActionSupport implements ServletRequestAware {
	private static final long serialVersionUID = -3069966988441462567L;
	private static final String EMAIL = "email";
	private static final String PASSWORD = "password";
	
	private HibernateDatabaseUserManager userManager = HibernateDatabaseUserManager.getDefault();
	
	private String xml = "";
	private String errorXml = "";
	private String email;
	private String password;
	
	private HttpServletRequest request;
	
	public String doLogin() throws Exception {
		email = getServletRequest().getParameter(EMAIL); 
		password = getServletRequest().getParameter(PASSWORD);
		
		User user = userManager.getUser(email, password);
		
		if (user != null) {
			setXml(new XmlWriter<User>(User.class).writeXml(user));
		} else {
			EncoreError error = new EncoreError("Incorrect email or password.");
			setErrorXml(new XmlWriter<EncoreError>(EncoreError.class).writeXml(error));
			return ERROR;
		}
		
		return SUCCESS;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpServletRequest getServletRequest() {
		return request;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
