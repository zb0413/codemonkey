package com.codemonkey.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbsSE extends AbsEntity<String> implements IEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
