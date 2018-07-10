package com.codemonkey.generator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectGenerator {
	
	private Logger logger = LoggerFactory.getLogger(ProjectGenerator.class);

	private String packageProjectName;
	
	private String projectName;
	
	private String databaseName;
	
	private String subPackageName;
	
	private String templateType;
	
	private void run() {
		Map<String, Object> binding = new HashMap<String,Object>();
		binding.put("packageProjectName", getPackageProjectName());
		binding.put("projectName", getProjectName());
		binding.put("subPackageName", getSubPackageName());
		binding.put("SubPackageName", StringUtils.capitalize(getSubPackageName()));
		binding.put("databaseName", getDatabaseName());
		binding.put("templateType", getTemplateType());
		
		String projectTempRoot = "project-templates-" + getTemplateType();
		FileExporter exporter = new FileExporter(projectTempRoot , "../");
		exporter.run(binding);
		
		//复制codeGenerator模板
		String codeTempRoot = "code-templates-" + getTemplateType();
		String codeTempTarget = "../" + File.separator+getProjectName() + File.separator + "code-templates";
		try {
			
			File srcDir = new File(codeTempRoot);
			File destDir = new File(codeTempTarget);
			if(srcDir.exists()){
				FileUtils.copyDirectory(srcDir, destDir);
			}
		} catch (IOException e) {
			logger.error(ProjectGenerator.class.getName() + " 发生错误",e);
		}
		
	}
	
	public static void main(String[] args){
		ProjectGenerator generator = new ProjectGenerator();
		generator.setProjectName("codemonkey-training-web");
		generator.setSubPackageName("training");
		generator.setTemplateType("extjs");
		generator.setPackageProjectName("codemonkey-training");
		generator.setDatabaseName("training");
		generator.run();
	}
	
	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getPackageProjectName() {
		return packageProjectName;
	}

	public void setPackageProjectName(String packageProjectName) {
		this.packageProjectName = packageProjectName;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	public String getSubPackageName() {
		return subPackageName;
	}

	public void setSubPackageName(String subPackageName) {
		this.subPackageName = subPackageName;
	}

}
