package com.codemonkey.service;

import com.codemonkey.domain.BaseDictionary;

public interface BaseDictionaryService extends GenericService<BaseDictionary>{

	Long getId(String codeValue , String fieldType);
	
	BaseDictionary get(String codeValue , String fieldType);
	
	BaseDictionary getBycode(String name , String fieldType);
}
