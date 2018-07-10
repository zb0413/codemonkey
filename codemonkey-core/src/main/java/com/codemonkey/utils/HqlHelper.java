package com.codemonkey.utils;
/**
 * 类描述：hql工具类
 */
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codemonkey.error.FieldValidation;
import com.codemonkey.error.FormFieldValidation;
import com.codemonkey.error.SysError;
import com.codemonkey.error.ValidationError;


/*
	查询后缀
 	_LE 小于等于 
 	_LT 小于
 	_GE 大于等于
 	_GT 大于
 	_Like 模糊查询
 	_isNull 为空
 	_isNotNull 不为空
 	_notEQ 不等于
 	_EQ 等于(默认)
 	_IN  包含
 	_notIN 不包含
*/

public final class HqlHelper {

	public static final String DIRECTION = "direction";

	public static final String PROPERTY = "property";

	public static final String OR_REG = "\\|\\|";

	public static final String AND = "&&";
	
//	private static final String OR = "||";

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";
	
	private static final String _ASC = "_ASC";

	private static final String _DESC = "_DESC";

	public static final String ORDER_BY = "-OrderBy-";

	private static final String ILIKE = "_Ilike";

	private static final String LIKE = "_Like";

	private static final String BETWEEN = "_Between";

	private static final String GE = "_GE";

	private static final String GT = "_GT";

	private static final String LT = "_LT";
	
	private static final String LE = "_LE";

	private static final String IS_NULL = "_isNull";

	private static final String IS_NOT_NULL = "_isNotNull";

	private static final String EQUAL = "_EQ";

	private static final String NOT_EQUAL = "_notEQ";

	public static final String SELECT_FROM = "SELECT DISTINCT E FROM ";
	
	public static final String SELECT_COUNT_FROM = "SELECT COUNT(DISTINCT E.id) FROM ";
	
	private static final String LEFT_JOIN = " LEFT JOIN ";
	
	private static final String JOINS = "JOINS";

	private static final String LINK_PREFIX = "LINK_";
	
	private static final String IN = "_IN";
	
	private static final String NOT_IN = "_notIN";
	
	private HqlHelper(){}
	
	
	/**
	 * 
	 * @param type
	 * @param findbyQuery
	 * @return
	 */
	public static String findBy(Class<?> type , String query , Object... params ){
		
 		return findBy(SELECT_FROM , type , query , params);
	}
	
	public static String findBy(String selectFrom , Class<?> type , String query , Object... params ){
		
		String entityName =  type.getName();
		
		StringBuffer buffer = new StringBuffer(selectFrom).append(entityName).append(" E ");
		
		if (query == null || query.trim().length() == 0) {
            return buffer.toString();
        }
		
		Map<String , String> joinsProps = new HashMap<String , String>();
		
		buildLeftJoins(buffer , joinsProps , query , type);
		
		buffer.append(" where 1=1  ");
		
//		Collections.sort(joinsProps , new Comparator<String>(){
//			@Override
//			public int compare(String o1, String o2) {
//				return o1.length() > o2.length() ? -1 : 1;
//			}
//			
//		});
		
		buffer.append(findByToJPQL(query , joinsProps , params));
		buffer.append(orderByToJPQL(query , joinsProps));
		
        return buffer.toString();
	}
	
	
	private static void buildLeftJoins(StringBuffer buffer, Map<String , String> joinsProps, String query , Class<?> type) {

		Set<String> props = new HashSet<String>();
		
		String[] parts = query.split(ORDER_BY);
		
		Set<String> partsSet = new HashSet<String>();
		
		for(int i = 0 ; i < parts.length ; i++){
			if(StringUtils.isNotBlank(parts[i])){
				
				String[] subParts1 = parts[i].split(AND);
				
				for(int k = 0 ; k < subParts1.length ; k++){
					if(StringUtils.isNotBlank(subParts1[k])){
						String[] subParts2 = subParts1[k].split(OR_REG);
						for(int j = 0 ; j < subParts2.length ; j++){
							if(StringUtils.isNotBlank(subParts2[j])){
								partsSet.add(subParts2[j]);
							}
						}
					}
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(partsSet)){
			for(String p : partsSet){
				props.add(extractPartFromQueryString(p));
			}
		}
			
		
		if(CollectionUtils.isNotEmpty(props)){
			for(String prop : props ){
				buildLeftJoin(prop , type , buffer , joinsProps , "E.");
			}
		}
	}

	private static void buildLeftJoin(String prop, Class<?> type , StringBuffer buffer , Map<String , String> joinsProps , String prefix) {
		
		if(prop.indexOf('.') < 0){
			Field field = ClassHelper.getFieldFromClazz(type, prop);
			
			if(field == null){
				throw new RuntimeException(prop + " is not found on class" + type);
			}
			
			if(field.getAnnotation(ManyToOne.class) != null 
					|| field.getAnnotation(OneToMany.class) != null
					|| field.getAnnotation(OneToOne.class) != null
					|| field.getAnnotation(ManyToMany.class) != null){
				
				 buffer.append(LEFT_JOIN);
				 buffer.append(prefix);
				 buffer.append(prop);
				 buffer.append(" as ");
				 buffer.append(prop);
				 
				 joinsProps.put(prefix + prop , prop);
			}
			
		}else{
			String prop0 = prop.substring(0 , prop.indexOf('.'));
			String prop1 = prop.substring(prop.indexOf('.') + 1);
			Field field1 = ClassHelper.getFieldFromClazz(type, prop0);
			
			if(field1 == null){
				throw new RuntimeException("can not find " + prop0 + " on " + type);
			}
			
			Class<?> type1 = field1.getType();
			
			if(type1.equals(List.class) || type1.equals(Set.class)){
				
				if(joinsProps.get(prop0) == null){
					 buffer.append(LEFT_JOIN);
					 buffer.append(prefix);
					 buffer.append(prop0);
					 buffer.append(" as ");
					 buffer.append(prop0);
					 
					 if("E.".equals(prefix)){
						 joinsProps.put(prop0, prop0);
					 }else{
						 joinsProps.put(prefix + prop0 , prop0);
					 }
					
				}
			}else{
				Field field = ClassHelper.getFieldFromClazz(type, prop0);
				
				if(field.getAnnotation(ManyToOne.class) != null 
						|| field.getAnnotation(OneToMany.class) != null
						|| field.getAnnotation(OneToOne.class) != null
						|| field.getAnnotation(ManyToMany.class) != null){
					
					 buffer.append(LEFT_JOIN);
					 buffer.append(prefix);
					 buffer.append(prop0);
					 buffer.append(" as ");
					 buffer.append(prop0);
					 
					 joinsProps.put(prefix + prop , prop);
					 
					 buildLeftJoin(prop1 , type1 , buffer , joinsProps , prop0 + ".");
				}else{
					buildLeftJoin(prop1 , type1 , buffer , joinsProps , prop0 + ".");
				}
			}
		}
	}


	private static String extractPartFromQueryString(String queryString) {
		return StringUtils.uncapitalize(queryString.split("_")[0]);
	}
	
	private static String orderByToJPQL(String query , Map<String , String> joinsProps) {
		
		StringBuffer jpql = new StringBuffer("");
		
		String orderBy = query;
		if(query.indexOf(ORDER_BY) >= 0){
			orderBy = query.substring(query.indexOf(ORDER_BY) + ORDER_BY.length());
			
			if(StringUtils.isNotBlank(orderBy)){
				
				jpql.append(" Order By ");
				
				String[] parts = orderBy.split(AND);
				for (int i = 0; i < parts.length; i++) {
					String part = parts[i];
			            
			        if (part.endsWith(_DESC)) {
		                String prop = extractProp(part, _DESC , joinsProps);
		                jpql.append(prop);
		                jpql.append(" ");
		                jpql.append(DESC);
		            } else if (part.endsWith(_ASC)) {
		            	String prop = extractProp(part, _ASC , joinsProps);
		                jpql.append(prop);
		                jpql.append(" ");
		                jpql.append(ASC);
	            	}
			        
			        if(i < parts.length - 1){
			        	 jpql.append(" , ");
			        }
	            }
			}
		}
		
		return jpql.toString();
	}

	public static String countBy(Class<?> type , String findbyQuery , Object... params ){
		String hql = findBy(type , findbyQuery , params);
		return  hql.replace(SELECT_FROM , SELECT_COUNT_FROM);
	}
	
	public static String sumBy(Class<?> type, String findbyQuery , String field , Object... params ) {
		String hql = findBy(type , findbyQuery , params);
		String sumField = field;
		if(field.indexOf('.') < 0){
			sumField = "E." + field;
		}
		return  hql.replace(SELECT_FROM , "SELECT SUM(" + sumField + ") FROM " );
	}
	
	public static String findByQueryInfo(Class<?> type , JSONObject queryAndSort){
		
		String findBy = queryInfoToFindBy(queryAndSort);
		
		List<Object> list = extractParamsFromQueryInfo(type , queryAndSort);
		
		return findBy(type , findBy , list.toArray());
	}
	
	public static String countByQueryInfo(Class<?> type , JSONObject queryAndSort){
		
		JSONObject queryAndSort1 = new JSONObject();
		queryAndSort1.put(ExtConstant.QUERY, queryAndSort.get(ExtConstant.QUERY));
		
		String findBy = queryInfoToFindBy(queryAndSort1);
		
		List<Object> list = extractParamsFromQueryInfo(type , queryAndSort1);
		
		return countBy(type , findBy , list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> extractParamsFromQueryInfo(Class<?> type , JSONObject queryAndSort){
		List<Object> params = new ArrayList<Object>();
		if(queryAndSort == null){
			return params;
		}
		
		if(!queryAndSort.has(ExtConstant.QUERY)){
			return params;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject(ExtConstant.QUERY);
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			if(key.equals(JOINS)){
				continue;
			}
			
			String[] orProps = key.split(OR_REG);
			if(orProps.length > 1){
				for(int i = 0 ; i < orProps.length ; i++){
					extractValue(key , orProps[i] , queryInfo , type ,  params);
				}
			}else{
				extractValue(key , orProps[0] , queryInfo , type ,  params);
			}
			
			
		}
		return params;
	}
	
	private static void extractValue(String key , String prop , JSONObject queryInfo , Class<?> type , List<Object> params){
		String value = queryInfo.getString(key);
		if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){

			Field field = null;
			
			if(prop.indexOf('_') > 0){
				prop = prop.split("_")[0];
			}
					
			if(prop.indexOf('.') < 0){
				field = ClassHelper.getFieldFromClazz(type , prop);
			}else{
				Class<?> type2 = type;
				while(prop.indexOf('.') > 0){
					
					String[] p = prop.split("\\.");
					
					field = ClassHelper.getFieldFromClazz(type2 , p[0]);
					
					if(field == null){
						throw new SysError("未找到属性" + p[0]);
					}
					
					if(field.getType().equals(List.class) || field.getType().equals(Set.class)){
						
						ParameterizedType integerListType = (ParameterizedType) field.getGenericType();
					    Class<?> clazz = (Class<?>) integerListType.getActualTypeArguments()[0];
						
					    field = ClassHelper.getFieldFromClazz(clazz , p[1]);
					    if(field == null){
							throw new SysError("未找到属性" + p[1]);
						}
					    type2 = clazz;
					}else{
						type2 = field.getType();
						field = ClassHelper.getFieldFromClazz(field.getType() , p[1]);
						if(field == null){
							throw new SysError("未找到属性" + p[1]);
						}
					}
					prop = prop.substring(prop.indexOf('.')+1);
				}
			}
			
			try{
				
				appendParams(params, value , field.getType() , key);
				
			}catch(Exception e){
				Set<FieldValidation> set = new HashSet<FieldValidation>();
				set.add(new FormFieldValidation(field.getName(), "bad format"));
				if(CollectionUtils.isNotEmpty(set)){
					throw new ValidationError(set);
				}
			}
		}
	}

	private static void appendParams(List<Object> params, String value , Class<?> clazz , String key) {
		
		if(key.endsWith(HqlHelper.IN) || key.endsWith(HqlHelper.NOT_IN)){
			try {
				JSONArray ja = new JSONArray(value);
				if(SysUtils.isNotEmpty(ja)){
					List<Object> list = new ArrayList<Object>();
					for(int i = 0 ; i < ja.length() ; i++){
						Object p = parseParam(ja.getString(i) , clazz , key);
						list.add(p);
					}
					params.add(list);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			Object p = parseParam(value , clazz , key);
			params.add(p);
		}
	}
	
	private static Object parseParam(String value , Class<?> clazz , String key) {
		Object v = null;
		if(clazz.equals(String.class)){
			if(key.endsWith(LIKE) || key.endsWith(ILIKE)){
				v = '%' + value + '%';
			}else{
				v = value;
			}
		}else if(clazz.equals(Boolean.class)){
			v = Boolean.valueOf(value);
		}else if(clazz.equals(Date.class)){
			v = SysUtils.toDate(value);
		}else if(clazz.equals(Integer.class)){
			v = Integer.valueOf((value));
		}else if(clazz.equals(Double.class)){
			v = Double.valueOf((value));
		}else if(clazz.equals(Float.class)){
			v = Float.valueOf((value));
		}else if(clazz.equals(Long.class)){
			v = Long.valueOf((value));
		}else if(clazz.equals(Short.class)){
			v = Short.valueOf((value));
		}else if(clazz.isEnum()){
			v = ClassHelper.stringToEnum(clazz , value);
		}else{
			//entity id
			v = Long.valueOf((value));
		}
		return v;
	}
	
	//---------------------------------------//
	//             private functions         //
	//---------------------------------------//
	
	@SuppressWarnings("unchecked")
	private static String queryInfoToFindBy(JSONObject queryAndSort){
		StringBuffer buffer = new StringBuffer();
		if(queryAndSort == null){
			return null;
		}
		
		if(!queryAndSort.has(ExtConstant.QUERY)){
			return null;
		}
		
		JSONObject queryInfo = queryAndSort.getJSONObject(ExtConstant.QUERY);
		
		Iterator<String> it = queryInfo.keys();
		
		while(it.hasNext()){
			String key = it.next();
			
			if(key.equals(JOINS)){
				continue;
			}
			
			if(isNoParameter(key)){
				buffer.append(key);
				buffer.append(AND);
			}else{
				String value = queryInfo.getString(key);
				if(StringUtils.isNotBlank(value) && !"null".equalsIgnoreCase(value)){
					buffer.append(key);
					buffer.append(AND);
				}
			}
			
			
		}
		
		JSONArray sortArray = queryAndSort.optJSONArray(ExtConstant.SORT);
		
		if(sortArray != null){
			buffer.append(ORDER_BY);
			for(int i = 0 ; i < sortArray.length() ; i++){
				JSONObject sort = sortArray.getJSONObject(i);
				
				String prop = sort.getString(PROPERTY);
				
				if(prop.startsWith(LINK_PREFIX)){
					prop = prop.substring(LINK_PREFIX.length());
				}
				
				prop = prop.replace("_", ".");
				
				buffer.append(prop);
				buffer.append('_');
				buffer.append(sort.getString(DIRECTION).toUpperCase());
				if(i < sortArray.length() - 1 ){
					buffer.append(AND);
				}
			}
		}
		return buffer.toString();
	}
	
	private static boolean isNoParameter(String key) {
		if(SysUtils.isNotEmpty(key)){
			if(key.endsWith(IS_NULL) || key.endsWith(IS_NOT_NULL)){
				return true;
			}
		}
		return false;
	}

	private static String findByToJPQL(String query , Map<String , String> joinsProps , Object... params ) {

		PositionHolder positionHolder = new PositionHolder();
		String conditions = "";
		String findBy = query;
		if(query.indexOf(ORDER_BY) >= 0){
			findBy = query.substring( 0 , query.indexOf(ORDER_BY));
		}
		if(StringUtils.isNotBlank(findBy)){
			StringBuffer jpql = new StringBuffer();
			 
	        String[] parts = findBy.split(AND);
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            
	            if (i <= parts.length - 1) {
	                jpql.append(" And ");
	            }
	            
	            jpql.append(extractHql(joinsProps, positionHolder , part , i , params));
	        }
	        return jpql.toString();
		}
		
		return conditions;
	}


	private static String extractHql(Map<String , String> joinsProps, PositionHolder positionHolder , String part , int index , Object... params) {
		
		StringBuffer jpql = new StringBuffer();
		
		String[] orParts = part.split(OR_REG);
		if(orParts.length > 1){
			jpql.append(" ( ");
			for(int i = 0 ; i < orParts.length ; i++){
				jpql.append(extractHql(joinsProps, positionHolder , orParts[i] , index + i , params));
				if( i < orParts.length - 1){
					jpql.append(" OR ");
				}
			}
			jpql.append(" ) ");
		}else{
			if (part.endsWith(IN)) {
				String prop = extractProp(part, IN , joinsProps);
				buildCollectionQuery(jpql , prop , positionHolder , IN , index , params);
				
			} else if (part.endsWith(NOT_IN)) {
				String prop = extractProp(part  , NOT_IN , joinsProps);
				buildCollectionQuery(jpql , prop , positionHolder , NOT_IN , index , params);
				
			} else if (part.endsWith(NOT_EQUAL)) {
			    String prop = extractProp(part, NOT_EQUAL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" <> ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(EQUAL)) {
			    String prop = extractProp(part, EQUAL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" = ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(IS_NOT_NULL)) {
			    String prop = extractProp(part, IS_NOT_NULL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" is not null ");
			} else if (part.endsWith(IS_NULL)) {
			    String prop = extractProp(part, IS_NULL , joinsProps);
			    jpql.append(prop);
			    jpql.append(" is null ");
			} else if (part.endsWith(LT)) {
			    String prop = extractProp(part, LT , joinsProps);
			    jpql.append(prop);
			    jpql.append(" < ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(LE)) {
			    String prop = extractProp(part, LE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" <= ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(GT)) {
			    String prop = extractProp(part, GT , joinsProps);
			    jpql.append(prop);
			    jpql.append(" > ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(GE)) {
			    String prop = extractProp(part, GE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" >= ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(BETWEEN)) {
			    String prop = extractProp(part, BETWEEN , joinsProps);
			    jpql.append(prop);
			    jpql.append("  < ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			    jpql.append(" AND ");
			    jpql.append(prop);
			    jpql.append(" > ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			    
			} else if (part.endsWith(LIKE)) {
			    String prop = extractProp(part, LIKE , joinsProps);
			    jpql.append(prop);
			    jpql.append(" like ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			} else if (part.endsWith(ILIKE)) {
			    String prop = extractProp(part, ILIKE , joinsProps);
			    jpql.append("LOWER(");
			    jpql.append(prop);
			    jpql.append(") like LOWER(?");
			    jpql.append(positionHolder.getCurPosition());
			    jpql.append(")");
			    positionHolder.add();
			} else {
			    String prop = extractProp(part, "" , joinsProps);
			    jpql.append(prop);
			    jpql.append(" = ?");
			    jpql.append(positionHolder.getCurPosition());
			    positionHolder.add();
			}
		}
		
		return jpql.toString();
	}
	
	private static void buildCollectionQuery(StringBuffer jpql, String prop , PositionHolder positionHolder, String operator , int index , Object... params) {
		if(params == null || params.length - 1 < index || params[index] == null){
			jpql.append(" 1=1 ");
			return;
		}
		
		if(params[index] instanceof Collection){
			Collection<?> coll = (Collection<?>) params[index];
			if(coll.isEmpty()){
				jpql.append(" 1=1 ");
				return;
			}
			jpql.append(prop);
			
			if(IN.equals(operator)){
				jpql.append(" IN (");
			}else if(NOT_IN.equals(operator)){
				jpql.append(" NOT IN (");
			}
			
			for(int i = 0 ; i < coll.size() ; i++){
				jpql.append(" ?");
				jpql.append(positionHolder.getCurPosition());
				positionHolder.add();
				
				if(i < coll.size() - 1){
					jpql.append(" , ");
				}
			}
			jpql.append(" ) ");
		}
		
	}


	private static String extractProp(String part, String end , Map<String , String> joinsProps) {
		String prop = part.substring(0, part.length() - end.length());
		prop = StringUtils.uncapitalize(prop);
		
		if(prop.indexOf('.') > 0){
			
			String prop2 = prop;
			String[] prop2Array = prop2.split("\\.");
			do{
	            if(joinsProps.get(prop2) != null){
	            	return prop.replace(prop2 , joinsProps.get(prop2));
	            }else if(prop2.lastIndexOf('.') > 0){
	            	prop2 = prop2.substring(0 , prop2.lastIndexOf('.'));
	            }else if(prop2.lastIndexOf('.') <= 0){
	            	break;
	            }
	        }while(prop2Array.length >= 1);
		}else if(joinsProps.get(prop) != null){
        	return joinsProps.get(prop);
		}	
        
        return "E." + prop;
    }
}
class PositionHolder{
	Integer curPosition = 0;
	public void add(){
		this.curPosition++;
	}
	
	public Integer getCurPosition(){
		return this.curPosition;
	}
}
