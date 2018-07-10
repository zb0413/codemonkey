package com.codemonkey.domain.cms;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEE;
import com.codemonkey.domain.BasicTypeIf;
import com.codemonkey.domain.UploadEntry;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("文章表")
public class Article extends AbsEE {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Label("发布日期")
	private Date publishedDate;
	
	@Label("文章来源")
	private String source;
	
	@Label("简要描述")
	@Type(type="text")
	private String keyWord; 
	
	@ManyToOne
	@Label("文件/缩略图")
	private UploadEntry thumb;
	
	@ManyToOne
	@Label("所属栏目")
	private Menu menu;
	
	@ManyToOne
	@Label("所属栏目子列表")
	private MenuSubList subList; 
	 
	@Enumerated(EnumType.STRING)
	@Label("发布状态")
	private ArticleSatus status = ArticleSatus.未发布;
	
	@Enumerated(EnumType.STRING)
	@Label("用途")
	private ArticleCategory category;
	
	@Enumerated(EnumType.STRING)
	@Label("是否置顶")
	private BasicTypeIf topFlag = BasicTypeIf.否;
	
	 
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("publishedDate", OgnlUtils.dateValue("publishedDate", this, "yyyy-MM-dd"));
		jo.put("source", OgnlUtils.stringValue("source", this));
		jo.put("keyWord", OgnlUtils.stringValue("keyWord", this));
		jo.put("thumb", OgnlUtils.stringValue("thumb.id", this));
		jo.put("thumb_name", OgnlUtils.stringValue("thumb.name", this));
		jo.put("menu", OgnlUtils.stringValue("menu.id", this));
		jo.put("menu_name", OgnlUtils.stringValue("menu.name", this));
		jo.put("subList", OgnlUtils.stringValue("subList.id", this));
		jo.put("subList_name", OgnlUtils.stringValue("subList.name", this));
		jo.put("status", OgnlUtils.stringValue("status", this));
		jo.put("category", OgnlUtils.stringValue("category", this));
		jo.put("topFlag", OgnlUtils.stringValue("topFlag", this));
		
		return jo;
	}
  
	public ArticleCategory getCategory() {
		return category;
	} 

	public void setCategory(ArticleCategory category) {
		this.category = category;
	}
 
	public ArticleSatus getStatus() {
		return status;
	}

	public void setStatus(ArticleSatus status) {
		this.status = status;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public UploadEntry getThumb() {
		return thumb;
	}

	public void setThumb(UploadEntry thumb) {
		this.thumb = thumb;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public MenuSubList getSubList() {
		return subList;
	}

	public void setSubList(MenuSubList subList) {
		this.subList = subList;
	}

	public BasicTypeIf getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(BasicTypeIf topFlag) {
		this.topFlag = topFlag;
	}
	 
}
