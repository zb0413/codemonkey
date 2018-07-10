package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：json转换器
 */
public class ListDividor<T> {

	public List<List<T>> divide(List<T> list , int size){
		
		if(SysUtils.isEmpty(list)){
			return null;
		}
		
		if(size < 0){
			return null;
		}
		
		List<List<T>> subLists = new ArrayList<List<T>>();
		
		int totalSize = list.size();
		int capacity = totalSize / size;
	
		int currentSize = subLists.size();
		
		int firstIndex = 0;
		int lastIndex = capacity;
		
		while(currentSize < size){
			
			if(currentSize == size - 1){
				List<T> sub = list.subList(firstIndex, totalSize);
				subLists.add(sub);
			}else{
				List<T> sub = list.subList(firstIndex, lastIndex);
				subLists.add(sub);
				firstIndex = lastIndex;
				lastIndex = firstIndex + capacity;
			}
			
			currentSize = subLists.size();
		}
		
		if(firstIndex < totalSize && lastIndex > totalSize){
			subLists.add(list.subList(firstIndex, totalSize));
		}
		
		return subLists;
	} 
}
