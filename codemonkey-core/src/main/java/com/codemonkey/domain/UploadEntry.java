package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.hibernate.validator.constraints.Length;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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
	
	// 覆盖或重载方法
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("fileName", OgnlUtils.stringValue("fileName", this));
		jo.put("filePath", OgnlUtils.stringValue("filePath", this));
		jo.put("fileType", OgnlUtils.stringValue("fileType", this));
		jo.put("fileSize", OgnlUtils.stringValue("fileSize", this));
		jo.put("tempName", OgnlUtils.stringValue("tempName", this));
		jo.put("tempPath", OgnlUtils.stringValue("tempPath", this));
		jo.put("ftpPath", OgnlUtils.stringValue("ftpPath", this));
		return jo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

}
