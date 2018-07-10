package com.codemonkey.web.view;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.codemonkey.domain.IEntity;
import com.codemonkey.utils.ExcelUtils;
import com.codemonkey.utils.SysUtils;

public class ExcelView extends AbstractExcelView {

	private String fileName;

	private List<IEntity> list;

	private JSONArray cols;

	private HSSFWorkbook workBook;

	public ExcelView(HSSFWorkbook workBook, String fileName) {
		this.workBook = workBook;
		this.fileName = StringUtils.isBlank(fileName) ? String
				.valueOf(new Date().getTime()) + ".xls" : fileName + ".xls";
	}

	public ExcelView(JSONArray cols, List<IEntity> list, String fileName) {
		this.cols = cols;
		this.list = list;
		this.fileName = StringUtils.isBlank(fileName) ? String
				.valueOf(new Date().getTime()) + ".xls" : fileName + ".xls";
	}

	@Override
	public void buildExcelDocument(Map<String, Object> map,
			HSSFWorkbook workBook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setHeader("Content-Disposition", "attachment;filename="
				+ SysUtils.encode(fileName.toString()));

		if (this.workBook != null) {
			ExcelUtils.copySheet(workBook, this.workBook.getSheetAt(0),
					workBook.createSheet(), true);
		} else {
			ExcelUtils.exportExcel(workBook, cols, list);
		}
	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

}
