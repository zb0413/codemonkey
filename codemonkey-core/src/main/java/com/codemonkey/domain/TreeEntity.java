package com.codemonkey.domain;


public interface TreeEntity<T> extends IEntity<T>{

	TreeEntity<T> getParent();
	
}
