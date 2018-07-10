package com.codemonkey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SysProp {

	@Value("${DEV_MODE}")
	private String devMode;
	
	@Value("${OS_VERIFY_CODE}")
	private String osVerifyCode;
	
	@Value("${LOAD_INIT_DATA}")
	private boolean loadInitData;
	
	@Value("${DEFAULT_MODULE}")
	private String defaultModule;
	
	@Value("${SMTP_HOST}")
	private String smtpHost;
	
	@Value("${SMTP_PORT}")
	private int smtpPort;
	
	@Value("${SMTP_USER}")
	private String smtpUser;
	
	@Value("${SMTP_PASSWORD}")
	private String smtpPassword;
	
	@Value("${shiro.successUrl}")
	private String successUrl;
	
	@Value("${shiro.rootUrl}")
	private String rootUrl;
	
	@Value("${shiro.loginUrl}")
	private String loginUrl;
	
	@Value("${LOG_SESSION}")
	private boolean logSession;
	
	@Value("${LOG_REQUEST}")
	private boolean logRequest;
	
	@Value("${PASSWORD_ENCRYPT}")
	private String passwordEncrypt;
	

	public String getDevMode() {
		return devMode;
	}

	public void setDevMode(String devMode) {
		this.devMode = devMode;
	}

	public String getOsVerifyCode() {
		return osVerifyCode;
	}

	public void setOsVerifyCode(String osVerifyCode) {
		this.osVerifyCode = osVerifyCode;
	}

	public boolean isLoadInitData() {
		return loadInitData;
	}

	public void setLoadInitData(boolean loadInitData) {
		this.loadInitData = loadInitData;
	}

	public String getDefaultModule() {
		return defaultModule;
	}

	public void setDefaultModule(String defaultModule) {
		this.defaultModule = defaultModule;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public boolean isLogRequest() {
		return logRequest;
	}

	public void setLogRequest(boolean logRequest) {
		this.logRequest = logRequest;
	}

	public boolean isLogSession() {
		return logSession;
	}

	public void setLogSession(boolean logSession) {
		this.logSession = logSession;
	}

	public String getPasswordEncrypt() {
		return passwordEncrypt;
	}

	public void setPasswordEncrypt(String passwordEncrypt) {
		this.passwordEncrypt = passwordEncrypt;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getRootUrl() {
		return rootUrl;
	}

	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
}
