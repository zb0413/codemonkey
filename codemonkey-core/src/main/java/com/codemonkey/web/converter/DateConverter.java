package com.codemonkey.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.codemonkey.utils.RegUtils;

@Component
public class DateConverter implements Converter<String , Date> {

	public Date convert(String source) {
		
		String dateFormat = null;
		Date date = null;
		
		if(StringUtils.isBlank(source)){
			return null;
		}
		
		if(RegUtils.matches(RegUtils.DATE_REG , source)){
			dateFormat = "yyyy-MM-dd";
		}else if(RegUtils.matches(RegUtils.DATE_TIME_REG , source)){
			dateFormat = "yyyy-MM-dd hh:mm:ss";
		}else if(RegUtils.matches(RegUtils.T_DATE_TIME_REG , source)){
			dateFormat = "yyyy-MM-dd hh:mm:ss";
			source = source.replace('T', ' ');
		}
		
		if(StringUtils.isNotBlank(dateFormat)){
			try {
				date = new SimpleDateFormat(dateFormat).parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return date;
	}

	public static String date2String(Date date)
	{	
		if (date == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			return sdf.format(date);
		}
	}
}
