package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.Audited;

@Entity
@Audited
@Getter
@Setter	
public class AppUserGroup extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<AppRole> roles = new HashSet<AppRole>();
	
	
}
