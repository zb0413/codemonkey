package com.codemonkey.utils;

import java.util.Date;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
/**
 * 类描述：ognl工具类
 */
public final class OgnlUtils {

	private OgnlUtils(){}
	
	public static String stringValue(String expressionString , Object obj){
		
		Object value = objectValue(expressionString , obj);
		
		if(value != null){
			
			if(value instanceof Date){
				return SysUtils.formatDate((Date) value);
			}
			
			if(value instanceof Double){
				return numberValue(expressionString , obj , "#.####");
			}
			
			return value.toString();
		}
		return "";
	}
	
	public static Object objectValue(String expressionString , Object obj){
	    Object value = null;
	    
	    if(obj == null){
	    	return value;
	    }
	    
		try {
			value = ClassHelper.callGetter(obj, expressionString);
			
			if(value == null){
				Object expr = Ognl.parseExpression(expressionString);
				OgnlContext ctx = new OgnlContext();
				value = Ognl.getValue(expr, ctx, obj);
			}
			
		} catch (OgnlException e) {
			return null;
		}
	    return value;
	}
	
	public static String numberValue(String expressionString , Object obj){
		Double d = (Double) objectValue(expressionString, obj);
		return SysUtils.formatNumber(d);
	}
	
	public static String numberValue(String expressionString , Object obj , String format){
		Double d = (Double) objectValue(expressionString, obj);
		return SysUtils.formatNumber(d , format);
	}
	
	public static String dateValue(String expressionString , Object obj , String format){
		Date d = (Date) objectValue(expressionString, obj);
		return SysUtils.formatDate(d , format);
	}

	public static Boolean booleanValue(String expressionString, Object obj) {
		return (Boolean) objectValue(expressionString, obj);
	}
	
}
