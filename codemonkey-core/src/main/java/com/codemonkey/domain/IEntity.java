package com.codemonkey.domain;


public interface IEntity<T> {

	T getId();
	
	boolean isNew();
	
	String getCode();
	
	Integer getVersion();
	
	Boolean getDelFlg();
	
	void setDelFlg(Boolean delFlg);
	
	boolean equals(IEntity<T> obj);
	
}
