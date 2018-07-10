package com.codemonkey.domain;

import javax.persistence.Entity;

@Entity
public class SecurityComponent extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SecurityComponent(){}
	
	public SecurityComponent(String code , String description){
		this.setCode(code);
		this.setDescription(description);
	}
	
}
