package com.codemonkey.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.UserDashboard;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;

@Service
public class UserDashboardServiceImpl extends PhysicalServiceImpl<UserDashboard> implements UserDashboardService{

	@Override
	public UserDashboard createEntity() {
		return new UserDashboard();
	}
	
	@Override
	protected Set<FieldValidation> validate(UserDashboard entity) {
		Set<FieldValidation> set = super.validate(entity);
		
		if(entity.getUiPortlet() == null){
			set.add(new FormFieldValidation("uiPortlet" , "uiPortlet 不能为空" ));
		}
		
		if(entity.getSortIndex() == null){
			set.add(new FormFieldValidation("sortIndex" , "sortIndex 不能为空" ));
		}
		
		if(entity.getColIndex() == null){
			set.add(new FormFieldValidation("colIndex" , "colIndex 不能为空" ));
		}
		return set;
	}

}
