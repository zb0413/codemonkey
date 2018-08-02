package com.codemonkey.domain;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import com.codemonkey.annotation.Label;

@Entity
@Label("基础码表信息")
@Getter
@Setter
public class BaseDictionary extends AbsEE{

	private static final long serialVersionUID = 1L;
	
	@Label("分类")
	private String fieldType;
	
	@Label("码值")
	private String codeValue;
	
	@Label("顺序")
	private Integer sortIndex;
	
}
