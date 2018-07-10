package com.codemonkey.domain.cms;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEE;
import com.codemonkey.domain.UploadEntry;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("轮播图")
public class CarouselFigure extends AbsEE{

	/**
	 * 轮播图
	 */
	private static final long serialVersionUID = 1L;
	 
	@ManyToOne
	@Label("图片")
	private UploadEntry picture; 

	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("picture", OgnlUtils.stringValue("picture.id", this));
		jo.put("picture_name", OgnlUtils.stringValue("picture.name", this));
		jo.put("filePath", OgnlUtils.stringValue("picture.filePath", this));
		
		return jo;
	}

	public UploadEntry getPicture() {
		return picture;
	}


	public void setPicture(UploadEntry picture) {
		this.picture = picture;
	}
	 
}
