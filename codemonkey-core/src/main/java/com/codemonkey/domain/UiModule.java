package com.codemonkey.domain;

public class UiModule {

	String moduleId;
	
	String img;
	
	private String icon;
	
	private boolean hidden = false;
	
	int priority = 0;
	
	UiModule(){}
	
	public UiModule(String moduleId){
		this.moduleId = moduleId;
	}
	
	public UiModule(String moduleId , int priority){
		this(moduleId);
		this.priority = priority;
	}
	
	public UiModule(String moduleId , int priority , String img){
		this(moduleId , priority);
		this.img = img;
	}
	
	public UiModule(String moduleId , int priority , String img , String icon){
		this(moduleId , priority , img);
		this.setIcon(icon);
	}
	
	public UiModule(String moduleId , String moduleName , int priority , String img , String icon){
		this(moduleId , priority , img);
		this.setIcon(icon);
	}
	
	public UiModule(String moduleId , int priority , String img , String icon , boolean hidden){
		this(moduleId , priority , img , icon);
		this.setHidden(hidden);
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
