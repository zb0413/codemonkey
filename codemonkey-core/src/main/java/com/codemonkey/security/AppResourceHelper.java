package com.codemonkey.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.codemonkey.annotation.Label;
import com.codemonkey.domain.AppPermission;
import com.codemonkey.domain.UrlPermission;
import com.codemonkey.utils.ExtConstant;

public final class AppResourceHelper {
	
	private AppResourceHelper(){} 
	
	public static List<UrlPermission> formPermissions(Class<?> clazz){
		List<UrlPermission> permissions = new ArrayList<UrlPermission>();
		permissions.add(formPermission(clazz , Operation.CREATE));
		permissions.add(formPermission(clazz , Operation.READ));
		permissions.add(formPermission(clazz , Operation.UPDATE));
//		permissions.add(formPermission(clazz , Operation.EDIT));
		return permissions;
	}
	
	public static List<UrlPermission> listPermissions(Class<?> clazz){
		List<UrlPermission> permissions = new ArrayList<UrlPermission>();
		permissions.add(listPermission(clazz , Operation.READ));
		permissions.add(listPermission(clazz , Operation.DESTROY));
//		permissions.add(listPermission(clazz , Operation.LIST));
//		permissions.add(listPermission(clazz , Operation.NEW));
		return permissions;
	}
	
	public static UrlPermission formPermission(Class<?> clazz , Operation op){
		String permission = formPermissionString(clazz, op);
		String url = formUrl(clazz, op);
		RequestType requestType = op.getRequestType();
		return new UrlPermission(permission , url , requestType , getDescription(clazz, op));
	}

	public static String getDescription(Class<?> clazz, Operation op) {
		String description = "";
		if(clazz.getAnnotation(Label.class) != null){
			description += clazz.getAnnotation(Label.class).value();
			description += op.getText();
		}
		return description;
	}
	
	private static UrlPermission listPermission(Class<?> clazz , Operation op){
		String permission = listPermissionString(clazz, op);
		String url = listUrl(clazz, op);
		RequestType requestType = op.getRequestType();
		
		return new UrlPermission(permission , url , requestType , getDescription(clazz, op));
	}

	private static String formUrl(Class<?> clazz, Operation op) {
		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("/");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString();
	}

	private static String listPermissionString(Class<?> clazz , Operation op) {
		StringBuffer buffer = new StringBuffer(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("List:");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString() ;
	}
	
	private static String formPermissionString(Class<?> clazz , Operation op) {
		StringBuffer buffer = new StringBuffer(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append(":");
		buffer.append(op.toString().toLowerCase());
		return buffer.toString() ;
	}
	
	private static String listUrl(Class<?> clazz, Operation op) {
		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
		buffer.append("List/");
		buffer.append(op.toString().toLowerCase());
		
		return buffer.toString() ;
	}

	public static List<AppPermission> mmPermissions(Class<?> clazz){
		List<AppPermission> permissions = new ArrayList<AppPermission>();
		permissions.add(formPermission(clazz , Operation.CREATE));
		permissions.add(formPermission(clazz , Operation.UPDATE));
		permissions.add(formPermission(clazz , Operation.DESTROY));
		permissions.add(formPermission(clazz , Operation.READ));
//		permissions.add(formPermission(clazz , Operation.LIST));
		return permissions;
	}

//	private static String mmUrl(Class<?> clazz, Operation op) {
//		StringBuffer buffer = new StringBuffer(ExtConstant.APP_ROOT);
//		buffer.append(StringUtils.uncapitalize(clazz.getSimpleName()));
//		buffer.append("/");
//		buffer.append(op.toString().toLowerCase());
//		return buffer.toString() ;
//	}
	
}
