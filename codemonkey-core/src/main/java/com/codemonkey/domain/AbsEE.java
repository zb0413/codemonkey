package com.codemonkey.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbsEE extends AbsEntity<Long> implements IEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
