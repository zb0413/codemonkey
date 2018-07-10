package com.codemonkey.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.service.AppRoleService;

@Component
public class AppRoleValidator extends DefaultValidator<AppUser>{

	@Autowired private AppRoleService appRoleService;
	
	public void validate(AppRole role, BindingResult bindingResult) {
		
		long count = appRoleService.countBy("name", role.getName());
		
		if(count > 0){
			bindingResult.rejectValue("name", "", "name is not unique");
		}
	}
}
