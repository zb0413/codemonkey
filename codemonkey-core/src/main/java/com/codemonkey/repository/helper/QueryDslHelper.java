package com.codemonkey.repository.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import cn.hutool.core.util.ClassUtil;

import com.codemonkey.error.SysError;
import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.ExtConstant;
import com.codemonkey.utils.OgnlUtils;
import com.codemonkey.utils.SysUtils;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Constant;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.ListPath;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.SimpleExpression;

public class QueryDslHelper {
	
	EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
	
	public static final String DIRECTION = "direction";

	public static final String PROPERTY = "property";

	public static final String OR_REG = "\\|\\|";
	
	private static final String AND_REG = "\\&\\&";

	public static final String AND = "&&";
	
//	private static final String OR = "||";

	public static final String ASC = "ASC";

	public static final String DESC = "DESC";
	
	public static final String ORDER_BY = "-OrderBy-";
	
	private static final String _ASC = "_ASC";

	private static final String _DESC = "_DESC";

	private static final String ILIKE = "_Ilike";

	private static final String LIKE = "_Like";

	private static final String GE = "_GE";

	private static final String GT = "_GT";

	private static final String LT = "_LT";
	
	private static final String LE = "_LE";

	private static final String IS_NULL = "_isNull";

	private static final String IS_NOT_NULL = "_isNotNull";

	private static final String EQUAL = "_EQ";

	private static final String NOT_EQUAL = "_notEQ";

	private static final String IN = "_IN";
	
	private static final String NOT_IN = "_notIN";

	public Predicate createPredicate (Class<?> type , JSONObject queryJo){
		BooleanBuilder builder = new BooleanBuilder();
		if(queryJo == null){
			return builder;
		}
		
		Iterator<?> keys = queryJo.keys();
		while(keys.hasNext()){
			String key = (String) keys.next();
			
			Object value =  queryJo.get(key);
			
			String[] orProps = key.split(OR_REG);
			
			if(orProps.length > 1){
				BooleanBuilder sub = new BooleanBuilder();
				for(int i = 0 ; i < orProps.length ; i++){
					sub.or(buildExpressions(type, orProps[i], value));
				}
				
				builder.and(sub);
				
			}else{
				builder.and(buildExpressions(type, key, value));
			}
		}
		
		Predicate p = builder.getValue();
		return p;
	}
	

	private Predicate buildExpressions(Class<?> type, String key, Object value) {
		Predicate p = null;
		String prop = key.split("_")[0];
		
		EntityPath<?> root = resolver.createPath(type);
		SimpleExpression<?> path = toStringPath(root, prop);
		Constant<?> c = ConstantImpl.create(value);
		if(key.endsWith(ILIKE)){
			c = ConstantImpl.create( '%'+ value.toString() + '%' );
			p = Expressions.booleanOperation(Ops.LIKE_IC , path , c);
		}else if(key.endsWith(LIKE)){
			c = ConstantImpl.create( '%'+ value.toString() + '%' );
			p = Expressions.booleanOperation(Ops.LIKE , path , c);
		}else if(key.endsWith(GE)){
			p = Expressions.booleanOperation(Ops.GOE , path , c);
		}else if(key.endsWith(GT)){
			p = Expressions.booleanOperation(Ops.GT , path , c);
		}else if(key.endsWith(LT)){
			p = Expressions.booleanOperation(Ops.LT , path , c);
		}else if(key.endsWith(LE)){
			p = Expressions.booleanOperation(Ops.LOE , path , c);
		}else if(key.endsWith(IS_NULL)){
			p = Expressions.booleanOperation(Ops.IS_NULL , path );
		}else if(key.endsWith(IS_NOT_NULL)){
			p = Expressions.booleanOperation(Ops.IS_NOT_NULL , path );
		}else if(key.endsWith(EQUAL)){
			p = Expressions.booleanOperation(Ops.EQ , path , c );
		}else if(key.endsWith(NOT_EQUAL)){
			p = Expressions.booleanOperation(Ops.NE , path , c );
		}else if(key.endsWith(IN)){
			p = Expressions.booleanOperation(Ops.IN , path , c );
		}else if(key.endsWith(NOT_IN)){
			p = Expressions.booleanOperation(Ops.NOT_IN , path , c );
		}else{
			p = Expressions.booleanOperation(Ops.EQ , path , c );
		}
		
		return p;
	}
	
	public List<OrderSpecifier<?>> createOrders(Class<?> type , JSONArray sort) {
		
		List<OrderSpecifier<?>> orders = new ArrayList<OrderSpecifier<?>>();
		
		if(SysUtils.isEmpty(sort)){
			return null;
		}
		
		for(int i = 0 ; i < sort.length() ; i++){
			JSONObject jo = sort.getJSONObject(i);
			
			String direction = jo.getString(DIRECTION);
			String property = jo.getString(PROPERTY);
			
			EntityPath<?> root = resolver.createPath(type);
			SimpleExpression<?> path = toStringPath(root, property);
			
			ComparableExpression<?> p = (ComparableExpression<?>) path;
			
			if(ASC.equalsIgnoreCase(direction)){
				
				orders.add(p.asc());
				
			}else if(DESC.equalsIgnoreCase(direction)){
				
				orders.add(p.desc());
				
			}
			
		}
		
		return orders;
	}
	
	public SimpleExpression<?> toStringPath(EntityPath<?> path, String query) {
		
		if(query.indexOf(".") < 0){
			return (SimpleExpression<?>) OgnlUtils.objectValue(query, path);
		}
		
		String prop = query.substring(0, query.indexOf("."));
		String subQuery = query.substring(query.indexOf(".") + 1);
		Object subValue = OgnlUtils.objectValue(prop, path);
		
		Field f = ClassUtil.getDeclaredField(path.getClass(), prop);
		if(f.getType().equals(SetPath.class) || f.getType().equals(ListPath.class)){
			Object obj = ClassHelper.callMethod(subValue, "any");
			return toStringPath((EntityPath<?>) obj,  subQuery);
		}
		throw new RuntimeException(f.getName() + " can not find solutions on " + path.getClass() );
	}
	
	public JSONObject toQueryAndSort(String query, Object... params) {
		JSONObject queryAndSort = new JSONObject();
		
		String[] temp = query.split(QueryDslHelper.ORDER_BY);
		String queryStr = temp[0];
		String sortStr = "";
		
		if(temp.length > 1){
			sortStr = temp[1];
		}
		
		JSONObject queryJo = buildQueryJo(queryStr , params);
		JSONArray sortJo = buildSortJo(sortStr);
		queryAndSort.put(ExtConstant.QUERY, queryJo);
		queryAndSort.put(ExtConstant.SORT, sortJo);
		
		if(SysUtils.isEmpty(query)){
			return queryAndSort;
		}
		
		return queryAndSort;
	}
	
	private JSONObject buildQueryJo(String query , Object... params) {
		JSONObject queryJo = new JSONObject();
		String[] parts = query.split(AND_REG);
		int currentIndex = 0;
		for(int i = 0 ; i < parts.length ; i++){
			if(parts[i].endsWith(IS_NULL) || parts[i].endsWith(IS_NOT_NULL)){
				queryJo.put(parts[i], "");
			}else{
				queryJo.put(parts[i], params[currentIndex++]);
			}
		}
		checkParams(queryJo);
		return queryJo;
	}


	private JSONArray buildSortJo(String sortStr) {
		JSONArray sort = new JSONArray();
		if(SysUtils.isEmpty(sortStr)){
			return null;
		}
		
		String[] parts = sortStr.split(AND);
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
	            
	        if (part.endsWith(_DESC)) {
                String prop = extractProp(part, _DESC);
                JSONObject jo = new JSONObject();
                jo.put(PROPERTY, prop);
                jo.put(DIRECTION, "DESC");
                sort.put(jo);
                
            } else if (part.endsWith(_ASC)) {
        		String prop = extractProp(part, _ASC);
    			JSONObject jo = new JSONObject();
    			jo.put(PROPERTY, prop);
    			jo.put(DIRECTION, "DESC");
    			sort.put(jo);
        	}
        }
		return sort;
	}


	private String extractProp(String part, String end) {
		String prop = part.substring(0, part.length() - end.length());
		prop = SysUtils.uncapitalize(prop);
		return prop;
	}


	private void checkParams(JSONObject queryJo) {
		Iterator<?> keys = queryJo.keys();
		while(keys.hasNext()){
			String key = (String) keys.next();
			if(!key.endsWith(IS_NULL) || !key.endsWith(IS_NOT_NULL)){
				Object value = queryJo.get(key);
				if(value == null){
					throw new SysError(key + "没有对应值");
				}
			}
		}
	}

}
