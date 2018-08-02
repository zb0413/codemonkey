package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.Length;

import com.codemonkey.annotation.Label;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public class UploadEntry extends AbsEE{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("文件名")
	@Length(max = 255)
	private String fileName;

	@Label("文件路径")
	@Length(max = 255)
	private String filePath;
	
	@Label("文件类型")
	@Length(max = 255)
	private String fileType;
	
	@Label("存储空间")
	private Long fileSize;
	
	@Label("文件临时")
	@Length(max = 255)
	private String tempName;
	
}
