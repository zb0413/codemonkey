package com.codemonkey.web.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.validation.BindingResult;

public class DefaultValidator<T> implements EntityValidator<T>{

	public void validate(T target, BindingResult bindingResult) {
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(target);
		
		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
		    String propertyPath = constraintViolation.getPropertyPath().toString();
		    String message = constraintViolation.getMessage();
		    bindingResult.rejectValue(propertyPath, "", message);
		}
	}
}
