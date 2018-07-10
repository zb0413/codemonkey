package com.codemonkey.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

/**
 * 类描述：json转换器
 */
public class JsonArrayConverter<T> {

	public List<T> convert(JSONObject params, String arrayKey, Class<?> clazz,
			FormattingConversionServiceFactoryBean ccService) {
		return convert(params, arrayKey, ExtConstant.ID, clazz, ccService);
	}

	public List<T> convert(JSONObject params, String arrayKey, String idKey,
			Class<?> clazz, FormattingConversionServiceFactoryBean ccService) {
		if (params.has(arrayKey)
				&& StringUtils.isNotBlank(params.getString(arrayKey))) {
			JSONArray p = params.optJSONArray(arrayKey);

			if (p == null) {
				try {
					p = new JSONArray(params.getString(arrayKey));
				} catch (NoSuchElementException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return convertArray(p, idKey, clazz, ccService);
		}
		return new ArrayList<T>();
	}

	public List<T> convert(JSONArray array, Class<?> clazz,
			FormattingConversionServiceFactoryBean ccService) {
		return convertArray(array, ExtConstant.ID, clazz, ccService);
	}

	@SuppressWarnings("unchecked")
	public List<T> convertArray(JSONArray array, String idKey, Class<?> clazz,
			FormattingConversionServiceFactoryBean ccService) {

		if (array == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < array.length(); i++) {
			if (!JSONObject.NULL.equals(array.get(i))) {
				JSONObject jo = array.getJSONObject(i);
				T t = null;

				if (SysUtils.isNotEmpty(jo.getString(idKey))) {
					if (jo.getString(idKey).equals("-1")) {
						continue;
					}

					t = (T) ClassHelper.convert(jo.getString(idKey), clazz,
							ccService);
				} else {
					t = createNewInstance(clazz);
				}
				ClassHelper.build(jo, t, ccService);
				list.add(t);
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private T createNewInstance(Class<?> clazz) {
		try {
			return (T) clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
