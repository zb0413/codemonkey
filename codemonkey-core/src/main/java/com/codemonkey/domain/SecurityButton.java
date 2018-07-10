package com.codemonkey.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BUTTON")
public class SecurityButton extends SecurityComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SecurityButton(){}
	
	public SecurityButton(String code , String description){
		super(code , description);
	}
	
}
