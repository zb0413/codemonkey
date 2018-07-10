package com.codemonkey.utils;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.RowFieldValidation;
/**
 * 类描述：json验证类
 */
public class JsonValidator {

	public static FieldValidation validate(String key , JSONObject body , ValidateType validateType , String label){
		
		String value = SysUtils.getString(body, key);
		
		if(!validateType.validate(value)){
			return new FormFieldValidation(key , label + validateType.getMessage());
		}
		
		return null;
	}
	
	public static Set<FieldValidation> validate(String key , JSONArray body , ValidateType validateType , String label , String rowType){
		Set<FieldValidation> set = new HashSet<FieldValidation>();
		
		for(int i = 0 ; i < body.length() ; i++){
			JSONObject jo = body.getJSONObject(i);
			String value = jo.getString(key);
			if(!validateType.validate(value)){
				set.add(new RowFieldValidation(key, rowType , label + validateType.getMessage()));
			}
		}
		return set;
	}
}
