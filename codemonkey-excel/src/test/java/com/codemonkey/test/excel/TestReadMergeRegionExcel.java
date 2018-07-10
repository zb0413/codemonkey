package com.codemonkey.test.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.Resource;

import com.codemonkey.excel.writer.Excel2003Writer;
import com.codemonkey.excel.writer.IExcelWriter;
import com.codemonkey.utils.SysUtils;

public class TestReadMergeRegionExcel {

	public static void main(String[] args) {
		
		Resource r = SysUtils.getResource("classpath:merged.xls");
		TestReadMergeRegionExcel p = new TestReadMergeRegionExcel();
		try {
			List<Map<String , String>> list = p.readExcelToObj(r.getInputStream());
			
			List<List<?>> result = buildResult(list);
			
			OutputStream out = new FileOutputStream("result.xls");
			
			IExcelWriter writer = new Excel2003Writer();
			writer.process(out,"结果", result);
			System.out.println("done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<List<?>> buildResult(List<Map<String, String>> list) {
		if(SysUtils.isEmpty(list)){
			return null;
		}
		
		List<List<?>> result = new ArrayList<List<?>>();
		for(Map<String, String> m : list){
//			String key = m.get("病种名称");
			String key = m.get("操作名称");
			List<String> row = (List<String>) getOrCreate(result , key);
			
			//0-病种
			//1-描述
			String description = (String) row.get(1);
			if(SysUtils.isNotEmpty(description)){
				description += m.get("ICD编码") ;
//				description += m.get("分类名称") + '\n';
				description += m.get("名称") + '\n';
			}else{
				description = m.get("ICD编码"); 
//				description += m.get("分类名称") + '\n';
				description += m.get("名称") + '\n';
			}
			row.remove(1);
			row.add(1, description);
			
			//2-正则表达式
			String reg = (String) row.get(2);
			if(SysUtils.isNotEmpty(reg)){
				reg += '|' + m.get("ICD编码") ;
			}else{
				reg = m.get("ICD编码");
			}
			row.remove(2);
			row.add(2, reg);
		}
		
		for(List<?> e :  result){
			List<String> p = (List<String>) e;
			String s = p.get(2);
			if(SysUtils.isNotEmpty(s)){
				s = s.replace(".", "\\.");
				s = s.replace("+", "\\+");
				s = s.replace("*", "\\*");
				s = s.replace("/", "\\/");
			}
			
			p.remove(2);
			p.add(2 , s);
		}
		return result;
	}

	private static List<?> getOrCreate(List<List<?>> result, String key) {
		if(SysUtils.isEmpty(result)){
			List<String> list = new ArrayList<String>();
			list.add(0 , key);
			list.add(1 , null);
			list.add(2 , null);
			result.add(list);
			return list;
		}
		
		for(List<?> p : result){
			if(p.get(0) == null){
				continue;
			}
			if(p.get(0).equals(key)){
				return p;
			}
		}
		
		List<String> list = new ArrayList<String>();
		list.add(0 , key);
		list.add(1 , null);
		list.add(2 , null);
		result.add(list);
		return list;
	}

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	private List<Map<String , String>> readExcelToObj(InputStream inputStream) {

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inputStream);
			List<Map<String , String>> list = readExcel(wb, 0, 0, 0);
			
			return list;
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 读取excel文件
	 * 
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行
	 */
	private List<Map<String , String>> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		List<Map<String , String>> list = new ArrayList<Map<String , String>>();
		List<String> header = new ArrayList<String>();
		
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			if(i == startReadLine){
				for (Cell c : row) {
					boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
						// 判断是否具有合并单元格
						if (isMerge) {
							String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
							header.add(c.getColumnIndex(), rs);
						} else {
							header.add(c.getColumnIndex(), c.getRichStringCellValue() + "");
						}
					}
			}else{
				if(row != null){
					Map<String , String> map = new HashMap<String , String>();
					
					for (Cell c : row) {
						boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
						// 判断是否具有合并单元格
						if (isMerge) {
							String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
							if(SysUtils.isNotEmpty(rs)){
								map.put(header.get(c.getColumnIndex()), rs);
							}
						} else {
							if(c.getColumnIndex() < header.size()){
								try{
									String t = getCellValue(c);
									if(SysUtils.isNotEmpty(t)){
										map.put(header.get(c.getColumnIndex()), t);
									}
								}catch(Exception e){
									e.printStackTrace();
								}
							}
						}
					}
					list.add(map);
				}
				
			}
		}	
		return list;
	}

	/**
	 * 获取合并单元格的值
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public String getMergedRegionValue(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();

		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {

				if (column >= firstColumn && column <= lastColumn) {
					Row fRow = sheet.getRow(firstRow);
					Cell fCell = fRow.getCell(firstColumn);
					return getCellValue(fCell);
				}
			}
		}

		return null;
	}

	/**
	 * 判断合并了行
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private boolean isMergedRow(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row == firstRow && row == lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 * 
	 * @param sheet
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 * @return
	 */
	private boolean isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断sheet页中是否含有合并单元格
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean hasMerged(Sheet sheet) {
		return sheet.getNumMergedRegions() > 0 ? true : false;
	}

	/**
	 * 合并单元格
	 * 
	 * @param sheet
	 * @param firstRow
	 *            开始行
	 * @param lastRow
	 *            结束行
	 * @param firstCol
	 *            开始列
	 * @param lastCol
	 *            结束列
	 */
	private void mergeRegion(Sheet sheet, int firstRow, int lastRow,
			int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol,
				lastCol));
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell) {

		if (cell == null)
			return "";

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

			return cell.getStringCellValue();

		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {

			return String.valueOf(cell.getBooleanCellValue());

		} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			return cell.getCellFormula();

		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			DecimalFormat df = new DecimalFormat("##############################################.####");
			return df.format(cell.getNumericCellValue());
		}
		return "";
	}
}
