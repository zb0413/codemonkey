package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.utils.OgnlUtils;

@Entity
@Label("功能菜单")
public class FunctionNode extends AbsEE implements TreeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Label("父节点")
	@ManyToOne
	private FunctionNode parent;
	
	@Label("视图类")
	private String viewClass;
	
	private String viewConfig;
	
	@Label("报表名称")
	private String reportName;
	
//	@Label("查询参数")
//	private String searchParams;
//	
//	@Label("页面参数")
//	private String pageParams;
	
	@Label("排序")
	private  Integer sortIndex;
	
	@Label("模块")
	private String moduleId;
	
	private String iconClass;
	
	@Label("访问路径")
	private String url;
	
	@Override
	public JSONObject listJson() {
		JSONObject jo = super.listJson();
		jo.put("parent", OgnlUtils.stringValue("parent.id", this));
		jo.put("parent_name", OgnlUtils.stringValue("parent.name", this));
		jo.put("viewClass", OgnlUtils.stringValue("viewClass", this));
		jo.put("reportName", OgnlUtils.stringValue("reportName", this));
		jo.put("searchParams", OgnlUtils.stringValue("searchParams", this));
		jo.put("pageParams", OgnlUtils.stringValue("pageParams", this));
		jo.put("sortIndex", OgnlUtils.stringValue("sortIndex", this));
		jo.put("moduleId", OgnlUtils.stringValue("moduleId", this));
		jo.put("iconClass", OgnlUtils.stringValue("iconClass", this));
		jo.put("viewConfig", OgnlUtils.stringValue("viewConfig", this));
		jo.put("url", OgnlUtils.stringValue("url", this));
		return jo;
	}
	
	public FunctionNode getParent() {
		return parent;
	}

	public void setParent(FunctionNode parent) {
		this.parent = parent;
	}

	public String getViewClass() {
		return viewClass;
	}

	public void setViewClass(String viewClass) {
		this.viewClass = viewClass;
	}
	
//	public String getPageParams() {
//		return pageParams;
//	}
//
//	public void setPageParams(String pageParams) {
//		this.pageParams = pageParams;
//	}
//
//	public String getSearchParams() {
//		return searchParams;
//	}
//
//	public void setSearchParams(String searchParams) {
//		this.searchParams = searchParams;
//	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getViewConfig() {
		return viewConfig;
	}

	public void setViewConfig(String viewConfig) {
		this.viewConfig = viewConfig;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
