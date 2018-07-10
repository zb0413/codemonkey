package com.codemonkey.web.validator;

import org.springframework.validation.BindingResult;

public interface EntityValidator<T> {

	void validate(T target, BindingResult bindingResult);
	
}
