package com.codemonkey.utils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.domain.IEntity;
import com.codemonkey.domain.IEnum;
/**
 * 类描述：ognl工具类
 */
public final class OgnlConvertUtils {

	private OgnlConvertUtils(){}
	
	@SuppressWarnings("unchecked")
	public static String stringValue(Object value){
		
		if(value != null){
			
			if(value instanceof Date){
				return dateValue((Date)value) ;
			}
			if(value instanceof Number){
				return value.toString() ;
			}
			if(value instanceof IEntity){
				return entityValue((IEntity<?>)value);
			}
			if(value instanceof IEnum){
				return enumValue((IEnum)value);
			}
			
			if(value instanceof Collection){
				return collectionValue((Collection<?>)value);
			}
			if(value instanceof Map){
				return mapValue((Map<String , Object>)value);
			}
			
			return value.toString();
		}
		return "";
	}
	
	
	private static String mapValue(Map<String , Object> map) {
		JSONObject jo = new JSONObject();
		Set<String> keys = map.keySet();
		
		if(SysUtils.isNotEmpty(keys)){
			for(String key : keys){
				jo.put(key, stringValue(map.get(key)));
			}
		}
		
		return jo.toString();
	}

	private static String collectionValue(Collection<?> c) {
		JSONArray ja = new JSONArray();
		if(SysUtils.isNotEmpty(c)){
			for(Object obj : c){
				ja.put(stringValue(obj));
			}
		}
		return ja.toString();
	}

	private static String enumValue(IEnum em) {
		return em.getName();
	}

	private static String entityValue(IEntity<?> entity) {
		return entity.getId().toString();
	}

	public static String dateValue(Date date){
		return SysUtils.formatDate(date , "yyyy-MM-dd HH:mm:ss");
	}
	
}
