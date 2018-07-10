package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.UploadEntry;
import com.codemonkey.error.SysError;

public class ImportUtils {

	public static List<Map<String, Object>> readFile(MultipartFile file,
			UploadEntry uploadEntry) {

		List<Map<String, Object>> list = null;
		String fileName = file.getOriginalFilename();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

		//TODO 超过50m一样有内存溢出现象！暂时先放放吧
		if ("csv".equals(prefix)) {

			List<Map<String, Object>> dlist = new ArrayList<Map<String, Object>>();

			List<String[]> tmplist = CsvUtils.readeCsv(uploadEntry
					.getFilePath(), "GBK", false);
			if (tmplist.size() <= 1) {
				throw new SysError("文件不正确或格式异常！");
			}
			String[] firstrow = tmplist.get(0);
			for (int i = 1; i < tmplist.size(); i++) {
				Map<String, Object> rowMap = new HashMap<String, Object>();

				String[] data = tmplist.get(i);
				for (int j = 0; j < firstrow.length; j++) {
					rowMap.put(firstrow[j], data[j]);
				}
				dlist.add(rowMap);
			}
			list = dlist;

		} else if ("xls".equals(prefix) || "xlsx".equals(prefix)) {
			list = ExcelUtils.readExcel(file);
		}

		return list;
	}
}
