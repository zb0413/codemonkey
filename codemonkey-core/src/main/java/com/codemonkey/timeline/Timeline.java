package com.codemonkey.timeline;

import java.util.Date;

import org.json.JSONObject;

import com.codemonkey.utils.OgnlUtils;

public class Timeline {

	private Date date;
	
	private String title;
	
	private String content;
	
	private String icon;
	
	private String user;
	
	public Timeline(Date date , String title , String content){
		this.date = date;
		this.title = title;
		this.content = content;
	}
	
	public Timeline(Date date , String title , String content , String icon , String user){
		this(date , title , content);
		this.icon = icon;
		this.user = user;
	}
	
	public JSONObject toJson(){
		JSONObject jo = new JSONObject();
		jo.put("date", OgnlUtils.dateValue("date", this, "yyyy-MM-dd HH:mm:ss"));
		jo.put("title", OgnlUtils.dateValue("date", this, "yyyy-MM-dd HH:mm:ss") +"&nbsp;" + OgnlUtils.stringValue("title", this));
		jo.put("content", OgnlUtils.stringValue("content", this));
		jo.put("user", OgnlUtils.stringValue("user", this));
		jo.put("to_now", toNow());
		jo.put("date_date", OgnlUtils.dateValue("date", this, "yyyy-MM-dd"));
		jo.put("date_time", OgnlUtils.dateValue("date", this, "HH:mm:ss"));
		jo.put("icon", OgnlUtils.stringValue("icon", this));
		
		return jo;
	}

	private Object toNow() {
		String toNow = "";
		
		if(getDate() == null){
			return toNow;
		}
		
		Date now = new Date();
		Date happenDate = getDate();
		
		long oneMinute = 60*1000;
		long oneHour = oneMinute * 60; 
		long oneDay = oneHour * 24; 
		long oneWeek = oneDay * 7;
		
		long gap = now.getTime() - happenDate.getTime();
		
		if(gap >= oneWeek){
			toNow = Math.ceil(gap / oneWeek) + " 周前";
		}else if(gap >= oneDay){
			toNow = Math.ceil(gap / oneDay) + " 天前";
		}else if(gap >= oneHour){
			toNow = Math.ceil(gap / oneHour) + " 小时前";
		}else if(gap >= oneMinute && gap < oneHour){
			toNow = Math.ceil(gap / oneMinute) + " 分钟前";
		}
		
		return toNow;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
