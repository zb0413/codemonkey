package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
/**
 * 类描述：List转换器
 */
public class ListConverter<T> {

	@SuppressWarnings("unchecked")
	public List<T> convert(List<?> listFrom){
		List<T> list = new ArrayList<T>();
		if(CollectionUtils.isNotEmpty(listFrom)){
			for(Object obj : listFrom){
				list.add((T) obj);
			}
		}
		return list;
	}
	
}
