package com.codemonkey.domain;

import java.util.Date;

import org.json.JSONObject;

import com.codemonkey.domain.seq.SequenceCreator;

public interface IEntity {

	Long getId();
	
	boolean isNew();
	
	String getCode();
	
	void setCode(String code);
	
	void setModificationDate(Date modificationDate);
	
	void setCreationDate(Date creationDate);
	
	void setCreatedUser(AppUser user);
	
	void setModifiedUser(AppUser user);
	
	JSONObject listJson();
	
	JSONObject detailJson();
	
	Integer getVersion();
	
	SequenceCreator getSequenceCreator();
	
	Boolean getDelFlg();
	
	void setDelFlg(Boolean delFlg);
	
	String getSyncNamingSpace();
	
	boolean equals(IEntity obj);
	
}
