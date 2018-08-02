package com.codemonkey.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.codemonkey.annotation.Label;
import com.codemonkey.annotation.SkipBuild;

@Entity
@Audited
@Label("角色表")
@Getter
@Setter	
public class AppRole extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="app_role_function_node", 
		joinColumns={@JoinColumn(name="app_role")},
		inverseJoinColumns={@JoinColumn(name="function_node")})
	@NotAudited
	@SkipBuild
	private Set<FunctionNode> functionNodes = new HashSet<FunctionNode>();

	
}
