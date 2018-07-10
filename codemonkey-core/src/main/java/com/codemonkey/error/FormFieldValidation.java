package com.codemonkey.error;

import javax.validation.ConstraintViolation;


public class FormFieldValidation extends FieldValidation{

	public FormFieldValidation(String message){
		this.setMessage(message);
	}
	
	public FormFieldValidation(String fieldName , String message){
		this.setFieldName(fieldName);
		this.setMessage(message);
	}

	public FormFieldValidation(ConstraintViolation<?> c) {
		this(c.getPropertyPath().toString() , c.getMessage());
	}

	@Override
	String getType() {
		return "FormField";
	}
	
}
