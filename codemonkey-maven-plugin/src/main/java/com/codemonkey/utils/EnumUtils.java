package com.codemonkey.utils;

import java.util.Arrays;
import java.util.Collection;

import org.json.JSONArray;

public final class EnumUtils {

	private EnumUtils(){}
	
	public static JSONArray getEnmuDataByClazz(Class<?> clazz){
		JSONArray ja = new JSONArray();
		
		if(clazz == null){
			return ja;
		}
		
		if(clazz.isEnum()){
			Object[] enumConstants = clazz.getEnumConstants();
			ja = buildEnmuCoboxData(Arrays.asList(enumConstants));
		}
		return ja;
	}

	public static JSONArray buildEnmuCoboxData(Collection<?> coll) {
		JSONArray array = new JSONArray();
		try {
			if (coll != null && !coll.isEmpty()) {
				for (Object obj : coll) {
					JSONArray jso = new JSONArray();
					jso.put(ClassHelper.callGetter(obj, "name"));
					jso.put(ClassHelper.callGetter(obj, "name"));
					array.put(jso);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
}
