package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import com.codemonkey.annotation.Label;

@Entity
@Getter
@Setter
public class AppPermission extends AbsEE{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Label("角色")
	@ManyToOne
	private AppRole role;
	
	@Label("资源")
	@ManyToOne
	private AppResource resource;

}
