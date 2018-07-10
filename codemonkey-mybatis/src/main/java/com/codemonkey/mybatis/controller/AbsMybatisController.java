package com.codemonkey.mybatis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.util.HtmlUtils;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;
import com.codemonkey.web.controller.AbsController;
import com.codemonkey.web.controller.SecurityController;

public class AbsMybatisController extends AbsController implements SecurityController{

	
	protected JSONObject jsonResult(Map<String, Object> t) {
		return toResultJson(t);
	}

	/**
	 * 方法描述：List<Map>转换成json对象
	 * 
	 * @param: list ibais查询结果
	 * @return: json对象
	 * @author: wy
	 */
	public String buildJson(List<Map<String, Object>> list) {
		JSONObject jo = buildResult(list);
		return jo.toString();
	}

	/**
	 * 方法描述：List<Map>转换成json对象并记录数
	 * 
	 * @param: list ibais查询结果
	 * @param: total 记录数
	 * @return: json对象
	 * @author: wy
	 */
	public String buildJson(List<Map<String, Object>> list, long total) {
		JSONObject jo = buildResult(list);
		jo.put(ExtConstant.TOTAL_COUNT, total);
		return jo.toString();
	}

	/**
	 * 方法描述：List<Map>转换成json对象
	 * 
	 * @param: list ibais查询结果
	 * @return: json对象
	 * @author: wy
	 */
	public JSONObject buildResult(List<Map<String, Object>> list) {
		JSONObject result = new JSONObject();
		JSONArray data = new JSONArray();
		if (CollectionUtils.isEmpty(list)) {
			result.put(ExtConstant.SUCCESS, true);
			result.put(ExtConstant.DATA, data);
			return result;
		}
		
		for (Map<String, Object> m : list) {
			JSONObject jo = toResultJson(m);
			JSONObject r = buildRecord(jo);
			data.put(r);
		}
		result.put(ExtConstant.SUCCESS, true);
		result.put(ExtConstant.DATA, data);
		return result;
	}

	protected JSONObject buildRecord(JSONObject jo) {
		return jo;
	}

	protected JSONObject toResultJson(Map<String, Object> m) {
		
		if(m == null){
			return null;
		}
		
		JSONObject jo = new JSONObject();
		Set<Entry<String, Object>> set = m.entrySet();
		
		if(SysUtils.isEmpty(set)){
			return null;
		}
		
		Iterator<Entry<String, Object>> it = set.iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			jo.put(columnToProp(e.getKey().toString()), HtmlUtils
					.htmlEscape(e.getValue() != null ? e.getValue()
							.toString() : ""));
		}
		
		
		
		return jo;
	}

	public static String propToColumn(String prop) {
		if (StringUtils.isBlank(prop)) {
			return "";
		}
		// replace aA to _a
		Pattern p1 = Pattern.compile("[a-z]{1}[A-Z]{1}");
		Matcher m1 = p1.matcher(prop);

		while (m1.find()) {
			prop = prop.replace(m1.group(), m1.group().substring(0, 1) + "_"
					+ m1.group().substring(1).toLowerCase());
		}

		// replace _1 to 1
		Pattern p2 = Pattern.compile("[a-zA-Z]{1}\\d{1}");
		Matcher m2 = p2.matcher(prop);

		while (m2.find()) {
			prop = prop.replace(m2.group(), m2.group().substring(0, 1) + "_"
					+ m2.group().substring(1));
		}
		return prop;
	}

	public static String columnToProp(String col) {

		if (StringUtils.isBlank(col)) {
			return "";
		}
		// replace _a to A
		Pattern p1 = Pattern.compile("_[a-z]{1}");
		Matcher m1 = p1.matcher(col);

		while (m1.find()) {
			col = col
					.replace(m1.group(), m1.group().substring(1).toUpperCase());
		}

		// replace _1 to 1
		Pattern p2 = Pattern.compile("_\\d{1}");
		Matcher m2 = p2.matcher(col);

		while (m2.find()) {
			col = col
					.replace(m2.group(), m2.group().substring(1).toUpperCase());
		}
		return col;

	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toQueryMap(JSONObject queryAndSort) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (queryAndSort == null) {
			return map;
		}

		JSONObject query = queryAndSort.optJSONObject(ExtConstant.QUERY);

		if (query != null) {
			Iterator<String> it = query.keys();
			while (it.hasNext()) {
				String key = it.next();
				if (!"null".equalsIgnoreCase(query.getString(key))) {

					String value = query.getString(key);

					if (SysUtils.isNotEmpty(value)) {

						Pattern p = Pattern
								.compile("(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2})");
						// 创建 Matcher 对象
						Matcher m = p.matcher(query.getString(key));
						if (m.matches()) {
							// 替换
							map.put(ExtConstant.QUERY + '_' + key,
									m.replaceAll("$1 $2"));
						} else {
							map.put(ExtConstant.QUERY + '_' + key,
									query.get(key));
						}
					}
				}
			}
		}

		JSONArray sort = queryAndSort.optJSONArray(ExtConstant.SORT);

		List<String> sortList = new ArrayList<String>();

		StringBuffer buffer = new StringBuffer();
		if (sort != null && sort.length() > 0) {
			for (int i = 0; i < sort.length(); i++) {
				JSONObject jo = sort.getJSONObject(i);

				buffer.append(propToColumn(jo.getString("property")));
				buffer.append(" ");
				buffer.append(jo.getString("direction"));

				sortList.add(buffer.toString());
			}
			map.put(ExtConstant.SORT, sortList);
		}

		return map;
	}
}
