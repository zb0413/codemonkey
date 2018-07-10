package com.codemonkey.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codemonkey.utils.SysUtils;

public class Excel2007Writer implements IExcelWriter{

    public Excel2007Writer(){}

	@Override
    public void process(OutputStream outputStream , String sheetName , List<String> headers , List<Map<String,Object>> rowData) {
		XSSFWorkbook wb = null;
        try {
        	 // 创建excel2003对象  
    		wb = new XSSFWorkbook();
            // 创建新的表单
    		XSSFSheet sheet;
    		if(SysUtils.isNotEmpty(sheetName)){
    			sheet = wb.createSheet(sheetName);
    		}else{
    			sheet = wb.getSheetAt(0);
    		}
    		int rownum = 0;
            // 创建新行 
    		if(SysUtils.isNotEmpty(rowData)){
				if(SysUtils.isNotEmpty(headers)){
					writeRow(sheet.createRow(rownum++) , headers);
				}
    			for(Map<String , Object> singleRow : rowData){
    				if(singleRow != null){
    					XSSFRow row = sheet.createRow(rownum++);
    					
    					List<Object> data = new ArrayList<Object>();
    					for(String h : headers){
    						data.add(singleRow.get(h));
    					}
    					writeRow(row , data);
    				}
    			}
    		}
			wb.write(outputStream);
			wb.close();
			outputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	public static void writeRow(XSSFRow row, List<?> list) {

		if (row == null || SysUtils.isEmpty(list)) {
			return;
		}

		short start = 0;

		for (Object value : list) {
			XSSFCell cell = row.createCell(start);
//			cell.setEncoding(HSSFCell.ENCODING_UTF_16);

			if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Date) {
				cell.setCellValue((Date) value);
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			}
			start++;
		}
	}

	@Override
	public void process(OutputStream outputStream , String sheetName, List<List<?>> rowData) {
		XSSFWorkbook wb = null;
        try {
        	 // 创建excel2003对象  
    		wb = new XSSFWorkbook();
            // 创建新的表单
    		XSSFSheet sheet;
    		if(SysUtils.isNotEmpty(sheetName)){
    			sheet = wb.createSheet(sheetName);
    		}else{
    			sheet = wb.getSheetAt(0);
    		}
    		int rownum = 0;
            // 创建新行 
    		if(SysUtils.isNotEmpty(rowData)){
    			for(List<?> r : rowData){
    				writeRow(sheet.createRow(rownum++) , r);
    			}
    		}
			wb.write(outputStream);
			wb.close();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
