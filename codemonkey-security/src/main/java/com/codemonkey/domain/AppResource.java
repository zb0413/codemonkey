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

	@Label("url")
	private String resourceUrl;
	
	@Label("资源所对应的方法名")
	private String methodName;
	
	@Label("资源所对应的包路径")
	private String methodPath;

}
