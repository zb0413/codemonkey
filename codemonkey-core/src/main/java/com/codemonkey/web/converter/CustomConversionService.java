package com.codemonkey.web.converter;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

@Service
@Lazy(false)
public class CustomConversionService extends FormattingConversionServiceFactoryBean {

	@Autowired private FormattingConversionService conversionService;
	
	@Autowired private Set<Converter<?, ?>> converterList;
	
	public void afterPropertiesSet(){	
		setConverters(converterList);
		super.afterPropertiesSet();
	}
}
