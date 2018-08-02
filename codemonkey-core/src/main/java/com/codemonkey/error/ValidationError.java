package com.codemonkey.error;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.SysUtils;


public class ValidationError extends SysError{

	private static final String ERROR_CODE = "702";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Collection<FieldValidation> errorSet ;
	
	private ValidationError(String detailMsg) {
		super(detailMsg);
		setErrorCode(ERROR_CODE);
	}
	
	public ValidationError(FieldValidation fv) {
		this(new HashSet<FieldValidation>());
		errorSet.add(fv);
	}
	
	public ValidationError(Collection<FieldValidation> set) {
		super("");
		setErrorCode(ERROR_CODE);
		this.errorSet = set;
	}
	
	@Override
	public JSONObject json(){
		JSONObject jo = super.json();
		if(CollectionUtils.isNotEmpty(errorSet)){
			JSONArray errors = new JSONArray();
			for(FieldValidation fv : errorSet){
				errors.put(fv.json());
			}
			jo.put(ExtConstant.ERROR_FIELDS , errors);
		}
		
		return jo;
	}

	public String getDetailMessage() {
//		StringBuffer buffer = new StringBuffer("validateion error : ");
//		buffer.append("<br>");
		StringBuffer buffer = new StringBuffer();
		
		if(SysUtils.isNotEmpty(super.getDetailMessage())){
			buffer.append(super.getDetailMessage());
			return buffer.toString();
		}
		
		if(CollectionUtils.isNotEmpty(errorSet)){
			for(FieldValidation fv : errorSet){
//				buffer.append(fv.getFieldName());
//				buffer.append(" : ");
				buffer.append(fv.getMessage());
				buffer.append("<br>");
			}
		}
		
		return buffer.toString();
	}
	
}
