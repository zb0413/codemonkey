package com.codemonkey.web.controller;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codemonkey.domain.DataExportEntry;
import com.codemonkey.domain.DataExportEntryHolder;
import com.codemonkey.error.SysError;
import com.codemonkey.service.GenericService;
import com.codemonkey.utils.SysUtils;

@Controller
@RequestMapping("/*/dataExportEntryList/**")
public class DataExportEntryListContoller extends
		AbsExtController<DataExportEntry> {

	@Autowired
	@Resource(name = "dataSource")
	private DataSource dataSource;

	@RequestMapping("read")
	@ResponseBody
	public String read() {
		return buildJson(DataExportEntryHolder.getAll());
	}

	@RequestMapping("exportXml")
	public void exportXml(@RequestParam String tableName,
			HttpServletResponse response) throws IOException {
		DataExportEntry entry = DataExportEntryHolder.get(tableName);
		if (entry != null) {
			String fileName = entry.getExportFileName("xml");
			setDownloadAttribute(response, fileName);
			SysUtils.exportDataToFile(dataSource, tableName,
					entry.getSqlQquery(), response.getOutputStream(), "xml");
		}
	}

	private void setDownloadAttribute(HttpServletResponse response,
			String fileName) {
//		response.setContentType("text/html;charset=UTF-8");
		try {
			fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
			response.setHeader("Content-disposition", "attachment; filename="
					+ fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("exportExcel")
	public void exportExcel(@RequestParam String tableName,
			HttpServletResponse response) throws IOException {
		DataExportEntry entry = DataExportEntryHolder.get(tableName);
		if (entry != null) {
			String fileName = entry.getExportFileName("xls");
			setDownloadAttribute(response, fileName);
			SysUtils.exportDataToFile(dataSource, tableName,
					entry.getSqlQquery(), response.getOutputStream(), "xls");
		}
	}

	@RequestMapping("exportExcelByPath")
	public void exportExcelByPath(@RequestParam String filename,
			@RequestParam String pathname, HttpServletResponse response)
			throws IOException {

		File file = new File(pathname);
		if(!file.exists()){
			throw new SysError(pathname + "不存在)");
		}
		setDownloadAttribute(response, filename);
		SysUtils.exportPathToFile(response.getOutputStream(), pathname);
	}
	
	
	@RequestMapping("importExcel")
	@ResponseBody
	public String importExcel(
			@RequestParam String tableName, HttpServletResponse response)
			throws IOException {

		SysUtils.loadDataToDB(dataSource, "classpath:default-data/*-default-data-*.xls", tableName);
		return success();
	}

	@Override
	protected GenericService<DataExportEntry> service() {
		return null;
	}
}
