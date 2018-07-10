package com.codemonkey.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.GlobalConfig;
import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.utils.SysUtils;

@Service
public class GlobalConfigServiceImpl extends LogicalServiceImpl<GlobalConfig> implements GlobalConfigService{

	@Override
	public GlobalConfig createEntity() {
		return new GlobalConfig();
	}
	
	@Override
	public String stringValue(String code) {
		
		if(SysUtils.isNotEmpty(code)){
			GlobalConfig c = findBy("code", code);
			if(c != null){
				return c.getValue();
			}
		}
		return null;
	}

	@Override
	public Double doubleValue(String code) {
		String value = stringValue(code);
		if(SysUtils.isNotEmpty(value)){
			return Double.valueOf(value);
		}
		return null;
	}

	@Override
	public Integer intValue(String code) {

		String value = stringValue(code);
		if(SysUtils.isNotEmpty(value)){
			return Integer.valueOf(value);
		}
		return null;
	}

	@Override
	protected Set<FieldValidation> validate(GlobalConfig globalConfig) {
		Set<FieldValidation> set = super.validate(globalConfig);
		
		if(SysUtils.isEmpty(globalConfig.getCode())){
			set.add(new FormFieldValidation("code" , "编码不能为空！"));
		}else if(!isUnique(globalConfig, "code" , globalConfig.getCode())){
			set.add(new FormFieldValidation("code" , "该编码已经存在！"));
		}
		return set;
	}
}
