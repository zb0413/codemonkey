package com.codemonkey.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import com.codemonkey.annotation.Label;

@Entity
@Label("功能菜单")
@Getter
@Setter
public class FunctionNode extends AbsEE implements TreeEntity<Long>{

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
	
}
