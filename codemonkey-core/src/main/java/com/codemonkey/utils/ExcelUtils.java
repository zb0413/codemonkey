package com.codemonkey.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.IEntity;
import com.codemonkey.error.SysError;

public class ExcelUtils {

	public static void writeRow(HSSFRow row, List<?> list){
		writeRow(row , list , null);
	}
	
	public static void writeRow(HSSFRow row, List<?> list, HSSFCellStyle style) {

		if (row == null || SysUtils.isEmpty(list)) {
			return;
		}

		int start = 0;

		for (Object value : list) {
			HSSFCell cell = row.createCell(start);

			if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof String) {
				cell.setCellValue((String) value);
			} else if (value instanceof Date) {
				cell.setCellValue((Date) value);
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			}
			
			if(style != null){
				cell.setCellStyle(style);
			}

			start++;
		}
	}
	
	public static List<Map<String, Object>> readExcel(InputStream is){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			
			if(is == null){
				return list;
			}
			
			HSSFWorkbook wb = new HSSFWorkbook(is); // 从文件流中获取Excel工作区对象（WorkBook）
			HSSFSheet sheet = wb.getSheetAt(0); // 从工作区中取得页（Sheet）

			HSSFRow firstrow = sheet.getRow(0);
			DecimalFormat df = new DecimalFormat(
					"###############.#############");
			for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 循环打印Excel表中的内容
				HSSFRow row = sheet.getRow(i);
				Map<String, Object> rowMap = new HashMap<String, Object>();

				for (int j = 0; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);
					if (cell != null && firstrow.getCell(j) != null) {
						if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()
								|| HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								rowMap.put(SysUtils.trimString(firstrow
										.getCell(j).getStringCellValue()), cell
										.getDateCellValue());
							} else {
								if(cell.getCellStyle().getDataFormat() == 10){
									//百分比
									rowMap.put(SysUtils.trimString(firstrow.getCell(j).getStringCellValue()), df.format(cell.getNumericCellValue() * 100));
								}else{
									rowMap.put(SysUtils.trimString(firstrow.getCell(j).getStringCellValue()), df.format(cell.getNumericCellValue()));
								}
							}

						} else if(HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()){
							rowMap.put(SysUtils.trimString(firstrow.getCell(j).getStringCellValue()),
									cell.getBooleanCellValue());
						}else {
							rowMap.put(SysUtils.trimString(firstrow.getCell(j).getStringCellValue()), 
									SysUtils.trimString(cell.getStringCellValue()));
						}
					}
				}
				list.add(rowMap);
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysError("文件不正确或格式异常！");
		}
		return list;
	}

	/**
	 * 方法描述：读取excel数据
	 * 
	 * @param: path
	 * @return: List
	 * @author: wy
	 * @throws Exception 
	 */
	public static List<Map<String, Object>> readExcel(MultipartFile file){
		try {
			return readExcel(file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法描述：把数据导入到excel
	 * 
	 * @param: workBook
	 * @param: cols
	 * @param: list
	 * @return: void
	 * @author: wy
	 */
	public static void exportExcel(HSSFWorkbook workBook, JSONArray cols,
			List<IEntity> list) {

		HSSFSheet sheet = workBook.createSheet();
		generateHeaderRow(sheet, cols);

		if (CollectionUtils.isNotEmpty(list)) {
			generateDataRow(sheet, cols, list);
		}
	}

	/**
	 * 方法描述：把数据写入excel
	 * 
	 * @param: pathname
	 * @param: cols
	 * @param: data
	 * @return: void
	 */
	public static void writeExcel(String filename, JSONArray cols,
			JSONArray data) {

		try {
			// 创建excel工作簿
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet();
			generateHeader(sheet, cols);

			if (data.length() > 0) {
				generateData(sheet, cols, data);
			}
			FileOutputStream fileOut = new FileOutputStream(filename);
			// 把上面创建的工作簿输出到文件中
			wb.write(fileOut);
			// 关闭输出流
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void generateHeader(HSSFSheet sheet, JSONArray cols) {
		HSSFRow headerRow = sheet.createRow(0);
		short columnNum = 0;
		for (short i = 0; i < cols.length(); i++) {
			JSONObject headerObj = cols.getJSONObject(i);

			HSSFCell cell = headerRow.createCell(columnNum);
			try {
				cell.setCellValue(headerObj.getString("text"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			columnNum++;

		}
	}

	private static void generateData(HSSFSheet sheet, JSONArray cols,
			JSONArray datas) {

		for (short i = 0; i < datas.length(); i++) {
			JSONObject detailJson = datas.getJSONObject(i);
			HSSFRow row = sheet.createRow(i + 1);
			short columnNum = 0;
			for (short j = 0; j < cols.length(); j++) {
				JSONObject headerObj = cols.getJSONObject(j);
				String key = headerObj.getString("dataIndex");

				HSSFCell cell = row.createCell(columnNum);
				String value = detailJson.optString(key);
				cell.setCellValue(value);

				columnNum++;

			}
		}
	}

	private static void generateDataRow(HSSFSheet sheet, JSONArray cols,
			List<IEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		for (short i = 0; i < list.size(); i++) {
			JSONObject detailJson = list.get(i).detailJson();
			HSSFRow row = sheet.createRow(i + 1);
			short columnNum = 0;
			for (short j = 0; j < cols.length(); j++) {
				if (cols.getJSONObject(j).has("hidden")
						&& !cols.getJSONObject(j).getBoolean("hidden")
						&& !"rownumberer".equals(cols.getJSONObject(j)
								.getString("xtype"))) {
					HSSFCell cell = row.createCell(columnNum);
					if ("linkedcolumn".equals(cols.getJSONObject(j).getString(
							"xtype"))) {
						String data = detailJson.getString(cols
								.getJSONObject(j).getString("dataIndex"));
						JSONObject jo;
						try {
							jo = new JSONObject(data);
							cell.setCellValue(jo.getString("linkText"));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						cell.setCellValue(detailJson.optString(cols
								.getJSONObject(j).getString("dataIndex")));
					}
					columnNum++;
				}
			}
		}
	}

	private static void generateHeaderRow(HSSFSheet sheet, JSONArray cols) {
		HSSFRow headerRow = sheet.createRow(0);
		short columnNum = 0;
		if(cols != null){
			for (short i = 0; i < cols.length(); i++) {
				JSONObject headerObj = cols.getJSONObject(i);
				if (headerObj.has("hidden") && !headerObj.getBoolean("hidden")
						&& !"rownumberer".equals(headerObj.getString("xtype"))) {
					HSSFCell cell = headerRow.createCell(columnNum);
					try {
						cell.setCellValue(SysUtils.switchCharset(headerObj
								.getString("header")));
					} catch (Exception e) {
						e.printStackTrace();
					}
					columnNum++;
				}

			}
		}
		
	}

	/**
	 * 复制一个单元格样式到目的单元格样式
	 * 
	 * @param fromStyle
	 * @param toStyle
	 */
	public static void copyCellStyle(HSSFCellStyle fromStyle,
			HSSFCellStyle toStyle) {
		toStyle.setAlignment(fromStyle.getAlignment());
		// 边框和边框颜色
		toStyle.setBorderBottom(fromStyle.getBorderBottom());
		toStyle.setBorderLeft(fromStyle.getBorderLeft());
		toStyle.setBorderRight(fromStyle.getBorderRight());
		toStyle.setBorderTop(fromStyle.getBorderTop());
		toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
		toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
		toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
		toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

		// 背景和前景
		toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
		toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

		toStyle.setDataFormat(fromStyle.getDataFormat());
		toStyle.setFillPattern(fromStyle.getFillPattern());
		// toStyle.setFont(fromStyle.getFont(null));
		toStyle.setHidden(fromStyle.getHidden());
		toStyle.setIndention(fromStyle.getIndention());// 首行缩进
		toStyle.setLocked(fromStyle.getLocked());
		toStyle.setRotation(fromStyle.getRotation());// 旋转
		toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
		toStyle.setWrapText(fromStyle.getWrapText());

	}

	/**
	 * Sheet复制
	 * 
	 * @param fromSheet
	 * @param toSheet
	 * @param copyValueFlag
	 */
	@SuppressWarnings("rawtypes")
	public static void copySheet(HSSFWorkbook wb, HSSFSheet fromSheet,
			HSSFSheet toSheet, boolean copyValueFlag) {
		// 合并区域处理
		mergerRegion(fromSheet, toSheet);
		Iterator rowIt = fromSheet.rowIterator();
		while (rowIt.hasNext()) {
			HSSFRow tmpRow = (HSSFRow) rowIt.next();
			HSSFRow newRow = toSheet.createRow(tmpRow.getRowNum());
			// 行复制
			copyRow(wb, tmpRow, newRow, copyValueFlag);
		}
	}

	/**
	 * 行复制功能
	 * 
	 * @param fromRow
	 * @param toRow
	 */
	@SuppressWarnings("rawtypes")
	public static void copyRow(HSSFWorkbook wb, HSSFRow fromRow, HSSFRow toRow,
			boolean copyValueFlag) {
		Iterator cellIt = fromRow.cellIterator();
		while (cellIt.hasNext()) {
			HSSFCell tmpCell = (HSSFCell) cellIt.next();
			HSSFCell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(wb, tmpCell, newCell, copyValueFlag);
		}
	}

	/**
	 * 复制原有sheet的合并单元格到新创建的sheet
	 * 
	 * @param sheetCreat
	 *            新创建sheet
	 * @param sheet
	 *            原有的sheet
	 */
	public static void mergerRegion(HSSFSheet fromSheet, HSSFSheet toSheet) {
		int sheetMergerCount = fromSheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergerCount; i++) {
			CellRangeAddress mergedRegionAt = fromSheet.getMergedRegion(i);
			toSheet.addMergedRegion(mergedRegionAt);
		}
	}

	/**
	 * 复制单元格
	 * 
	 * @param srcCell
	 * @param distCell
	 * @param copyValueFlag
	 *            true则连同cell的内容一起复制
	 */
	public static void copyCell(HSSFWorkbook wb, HSSFCell srcCell,
			HSSFCell distCell, boolean copyValueFlag) {
//		HSSFCellStyle newstyle = wb.createCellStyle();
//		copyCellStyle(srcCell.getCellStyle(), newstyle);
		// 样式
//		distCell.setCellStyle(newstyle);
		// 评论
		// if (srcCell.getCellComment() != null) {
		// distCell.setCellComment(srcCell.getCellComment());
		// }
		// 不同数据类型处理
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (copyValueFlag) {
			if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
				if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
					distCell.setCellValue(srcCell.getDateCellValue());
				} else {
					distCell.setCellValue(srcCell.getNumericCellValue());
				}
			} else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
				distCell.setCellValue(srcCell.getStringCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
				// nothing21
			} else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
				distCell.setCellValue(srcCell.getBooleanCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
				distCell.setCellErrorValue(srcCell.getErrorCellValue());
			} else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
				distCell.setCellFormula(srcCell.getCellFormula());
			} else { // nothing29
			}
		}
	}

	public static void buildWorkbook(Map<String, Object> data, File file) {

		HSSFWorkbook wb = null;
		try {
			InputStream is = new FileInputStream(file);
			wb = new HSSFWorkbook(is);
			HSSFSheet sheet = wb.getSheetAt(0);

			for (int i = 0; i <= sheet.getLastRowNum(); i++) { // 循环打印Excel表中的内容
				HSSFRow row = sheet.getRow(i);

				for (short j = 0; row != null && j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);

					if (cell != null) {
						if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
							String str = cell.getStringCellValue().trim();
							if (str.indexOf("#") != -1) {
								String key = str.replaceAll("#", "");
								Object obj = data.get(key);
								if (obj instanceof String) {
									cell.setCellValue(String.valueOf(obj));
								} else {
									if (obj != null) {
										cell.setCellValue(Double
												.valueOf(String.valueOf(obj)));
									} else {
										cell.setCellValue(0);
									}
								}

							}

						}
					}
				}

			}

			FileOutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
