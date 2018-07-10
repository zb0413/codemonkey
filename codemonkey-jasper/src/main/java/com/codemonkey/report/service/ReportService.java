package com.codemonkey.report.service;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public interface ReportService {

	void pdf(String destinationFile, String templatePath, Map<String , Object> param);
	
	void pdf(String destinationFile, String templatePath);

	void xml(String destinationFile, String templatePath);
	
	void print(String templatePath, Map<String , Object> param);

	void down(OutputStream outputStream, File templatePath, Map<String, Object> param);

	void down(OutputStream outputStream, File templatePath);
}