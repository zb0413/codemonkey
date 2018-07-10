package com.codemonkey.excel.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.codemonkey.error.SysError;
import com.codemonkey.utils.SysUtils;


public class Excel2003Writer implements IExcelWriter{  
	
	public  Excel2003Writer(){}

	 /** 
     * 写入excel并填充内容,一个sheet只能写65536行以下，超出会报异常，写入时建议使用Excel2007Writer 
     * @param fileName 文件名
     * @param rowData 数据
     */  
	@Override
    public void process(OutputStream outputStream , String sheetName , List<String> headers , List<Map<String,Object>> rowData) {
		HSSFWorkbook wb = null;
        try {
        	 // 创建excel2003对象  
    		wb = new HSSFWorkbook();
            // 创建新的表单
    		HSSFSheet sheet;
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
				if(rownum > 65535){
					throw new SysError("写入行数大于65535,请使用2007版本写入!");
				}
    			for(Map<String , Object> singleRow : rowData){
    				if(singleRow != null){
    					HSSFRow row = sheet.createRow(rownum++);
    					
    					List<Object> data = new ArrayList<Object>();
    					for(String h : headers){
    						data.add(singleRow.get(h));
    					}
    					writeRow(row , data);
    				}
    			}
    		}
			wb.write(outputStream);
			outputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
	public static void writeRow(HSSFRow row, List<?> list) {

		if (row == null || SysUtils.isEmpty(list)) {
			return;
		}

		short start = 0;

		for (Object value : list) {
			HSSFCell cell = row.createCell(start);
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
		HSSFWorkbook wb = null;
        try {
        	 // 创建excel2003对象  
    		wb = new HSSFWorkbook();
            // 创建新的表单
    		HSSFSheet sheet;
    		if(SysUtils.isNotEmpty(sheetName)){
    			sheet = wb.createSheet(sheetName);
    		}else{
    			sheet = wb.getSheetAt(0);
    		}
    		int rownum = 0;
            // 创建新行 
    		if(SysUtils.isNotEmpty(rowData)){
    			for(List<?> r : rowData){
    				if(rownum > 65535){
						throw new SysError("写入行数大于65535,请使用2007版本写入!");
					}
    				writeRow(sheet.createRow(rownum++) , r);
    			}
    		}
			wb.write(outputStream);
			wb.close();
			outputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}  
