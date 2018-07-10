package com.codemonkey.generator;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.URL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AbsEntity;
import com.codemonkey.utils.ClassHelper;

public class CodeGenerator {
	
	private static final String MODEL = "modelName";
	private static final String XTYPE = "xtype";
	private static final int OFF_SET = 16;
	private static final String TEMPLATE_ROOT = "code-templates"; 
	
	private String templateDir = "default";
	
	private String entityPackage = "com.codemonkey.domain";
	
	private String subPackage;
	
	private String projectName;

	public void run(Class<?> clazz){
		Map<String, Object> binding = buildBinding(clazz);
		File baseDirectory = new File(".");
		String templateRoot = baseDirectory.getAbsolutePath() + File.separator + TEMPLATE_ROOT +  File.separator + getTemplateDir() ;
		FileExporter exporter = new FileExporter(templateRoot , baseDirectory.getPath());
		exporter.run(binding);
	}
	
	public Map<String, Object> buildBinding(Class<?> clazz) {
		Map<String, Object> binding = new HashMap<String, Object>();
	
		binding.put("subPackage", getSubPackage());
		binding.put("projectName", getProjectName());
		
		String entityName = clazz.getSimpleName();
		binding.put("entityName", StringUtils.uncapitalize(entityName));
		binding.put("EntityName", StringUtils.capitalize(entityName));
		binding.put("entityname", entityName.toLowerCase());
		binding.put("entityFullName", clazz.getName());
		
		binding.put("entityPackage" , getEntityPackage());
		
		List<Field> fields = ClassHelper.getAllFields(clazz , AbsEntity.class);
		binding.put("fields", fields);
		binding.put("labels", buildLabels(fields));
		
		//ext related
		JSONArray fieldsJa = new JSONArray();
		
		List<Field> stringFields = new ArrayList<Field>();
		List<Field> numberFields = new ArrayList<Field>();
		List<Field> dateFields = new ArrayList<Field>();
		List<Field> booleanFields = new ArrayList<Field>();
		List<Field> enumFields = new ArrayList<Field>();
		List<Field> relationFields = new ArrayList<Field>();
		
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				if(f.getType().equals(String.class)){
					stringFields.add(f);
				}else if(f.getType().equals(Date.class)){
					dateFields.add(f);
				}else if(f.getType().equals(Boolean.class)){
					booleanFields.add(f);
				}else if(f.getType().isEnum()){
					enumFields.add(f);
				}else if(f.getType().equals(Double.class) || f.getType().equals(Integer.class) 
						|| f.getType().equals(Long.class)){
					numberFields.add(f);
				}else if( f.getType().equals(Short.class) || f.getType().equals(Float.class) ){
					numberFields.add(f);
				}else if(f.getAnnotation(ManyToOne.class) != null){
					relationFields.add(f);
				}
			}
		}
		
		buildStringFields(stringFields , fieldsJa);
		buildNumberFields(numberFields , fieldsJa);
		buildDateFields(dateFields , fieldsJa);
		buildBooleanFields(booleanFields , fieldsJa);
		buildEnumFields(enumFields , fieldsJa);
		buildRelationFields(relationFields , fieldsJa);
		
		binding.put("fieldsJson", formatJSON(unescapeUnicode(fieldsJa.toString())));
		
		//build columns
		binding.put("columnsJson", formatJSON(unescapeUnicode(buildColumnsJson(fields))));
		
		return binding;
	}
	
	private Map<String , String> buildLabels(List<Field> fields) {
		Map<String , String> map = new HashMap<String , String>();
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				String label = label(f);
				map.put(f.getName(), label);
			}
		}
		return map;
	}

	public String label(String fieldName) {
		
		String regex = "[A-Z]{1}[a-z]*[0-9]*";
		String label = fieldName;
		Pattern p = Pattern.compile(regex);  
		Matcher m = p.matcher(fieldName);
		while(m.find()){
			String part = m.group();
			label = label.replace(part, " " + StringUtils.uncapitalize(part));
		}
		return label;
	}
	
	public String label(Field f) {
		Label label = null;
		if(f.getAnnotation(Label.class) != null){
			label = f.getAnnotation(Label.class);
		}else if(f.getType().getAnnotation(Label.class) != null){
			label = f.getType().getAnnotation(Label.class);
		}
		if(label != null ){
			return label.value();
		}
		return label(f.getName());
	}
	
	public String unescapeUnicode(String str){
        StringBuffer sb=new StringBuffer();
        Matcher matcher = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(str);
        while(matcher.find()){
            matcher.appendReplacement(sb, (char)Integer.parseInt(matcher.group(1) , OFF_SET)+"");  
        }
        matcher.appendTail(sb);
        return sb.toString().replace("\\", "");
    }

	private String formatJSON(String json) {
		Pattern p = Pattern.compile("\"(\\w*)\"(:)"); 
		Matcher m = p.matcher(json); 
		return m.replaceAll("$1$2");
	}

	private String buildColumnsJson(List<Field> fields) {
		JSONArray columnsJson = new JSONArray();
		if(CollectionUtils.isNotEmpty(fields)){
			for(Field f : fields){
				JSONObject jo = new JSONObject();
				jo.put("header" , label(f));
				jo.put("dataIndex" , f.getName());
				jo.put("flex" , 1);
				columnsJson.put(jo);
			}
		}
		return columnsJson.toString();
	}

	private void buildRelationFields(List<Field> fields, JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "searchingselect");
				
				if(isImplementsOfEE(f.getType())){
					fieldjo.put(MODEL, f.getType().getSimpleName() + "List");
				}else{
					fieldjo.put(MODEL, f.getType().getSimpleName());
				}
				
				fieldsJson.put(fieldjo);
			}
		}
	}

	private boolean isImplementsOfEE(Class<?> type) {
		Class<?> type1 = type;
		while(!type1.equals(Object.class)){
			Class<?>[] interfaces = type1.getInterfaces();
			if(interfaces != null){
				for(int i = 0 ; i < interfaces.length ; i++ ){
					if(interfaces[i].getSimpleName().equals("IEntity")){
						return true;
					}
				}
			}
			type1 = type1.getSuperclass();
		}
		return false;
	}

	private void buildEnumFields(List<Field> fields, JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "enumselect");
				fieldjo.put("className", f.getType().getSimpleName());
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildBooleanFields(List<Field> fields, JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "checkbox");
				fieldsJson.put(fieldjo);
			}
		}
	}

	private void buildDateFields(List<Field> fields, JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "datefield");
				fieldjo.put("format", "Y-m-d");
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildNumberFields(List<Field> fields, JSONArray fieldsJson) {
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "numberfield");
				fieldsJson.put(fieldjo);
			}
		}
		
	}

	private void buildStringFields(List<Field> fields, JSONArray fieldsJson) {
		
		if(fields != null && !fields.isEmpty()){
			for(Field f : fields){
				JSONObject fieldjo = buildField(f);
				fieldjo.put(XTYPE, "textfield");
				fieldsJson.put(fieldjo);
			}
		}
	}

	private JSONObject buildField(Field f) {
		JSONObject jo = new JSONObject();
//		jo.put("id", f.getName());
		jo.put("name", f.getName());
		jo.put("fieldLabel", label(f));
		buildValidation(jo , f);
		return jo;
	}

	private void buildValidation(JSONObject jo, Field field) {
		
		if(field.getAnnotation(NotNull.class) != null){
			jo.put("allowBlank", false);
		}
		if(field.getAnnotation(Min.class) != null){
			jo.put("minValue", field.getAnnotation(Min.class).value());
		}
		if(field.getAnnotation(Max.class) != null){
			jo.put("maxValue", field.getAnnotation(Max.class).value());
		}
		if(field.getAnnotation(Email.class) != null){
			jo.put("vtype", "email");
		}
		if(field.getAnnotation(URL.class) != null){
			jo.put("vtype", "url");
		}
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getSubPackage() {
		return subPackage;
	}

	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
