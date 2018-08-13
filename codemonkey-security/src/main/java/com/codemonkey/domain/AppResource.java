package com.codemonkey.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import com.codemonkey.annotation.Label;

@Entity
@Label("系统资源")
@Getter
@Setter
public class AppResource extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Label("urlPattern")
	private String urlPattern;
	
	@Label("提交方法")
	private String methodName;
	

}
