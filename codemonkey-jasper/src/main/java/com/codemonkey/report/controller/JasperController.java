package com.codemonkey.report.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.codemonkey.report.service.ReportService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/**/jasper/**")
public class JasperController {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ReportService reportService;

	@RequestMapping("/pdf")
	public ModelAndView doReport(
			@RequestParam(defaultValue = "template") String reportName,
			@RequestParam(required = false) JSONObject parameters,
			HttpServletRequest request) throws IOException, SQLException {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("format", "pdf");
		model.put(JRParameter.REPORT_CONNECTION, dataSource.getConnection());
		if (parameters != null) {
			Iterator<?> it = parameters.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				model.put(key, parameters.get(key));
			}
		}

		return new ModelAndView(reportName, model);

	}

	@RequestMapping("/pdfdown")
	public void pdfdown(
			@RequestParam(defaultValue = "template") String reportName,
			@RequestParam(required = false) JSONObject parameters,
			HttpServletResponse response, HttpSession session,
			HttpServletRequest request) throws IOException, SQLException {

		String path = session.getServletContext().getRealPath("") + "/upload/";
		File directory = new File(path);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdir();
		}

		Resource resources = SysUtils.getResource(reportName);
		String pathname = "";
		String[] sname = reportName.split("/");
		if (sname.length > 1) {
			pathname = path + sname[1];
		} else {
			pathname = path + reportName;
		}
		File destFile = new File(pathname);
		if (!destFile.exists()) {
			FileUtils.copyInputStreamToFile(resources.getInputStream(), destFile);
		}
		

		Map<String, Object> param = new HashMap<String, Object>();
		if (parameters != null) {
			Iterator<?> it = parameters.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				param.put(key, parameters.get(key));
			}
		}

		response.setHeader("Content-disposition",
				"attachment; filename=resume.pdf");
		reportService.down(response.getOutputStream(), destFile, param);

	}

	@RequestMapping("/print")
	public void print(
			@RequestParam(defaultValue = "template") String reportName,
			@RequestParam(required = false) JSONObject parameters) {

		Map<String, Object> param = new HashMap<String, Object>();
		if (parameters != null) {
			Iterator<?> it = parameters.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				param.put(key, parameters.get(key));
			}
		}
		reportService.print(reportName, param);
	}

}
