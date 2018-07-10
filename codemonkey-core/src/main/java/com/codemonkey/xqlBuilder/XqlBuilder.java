package com.codemonkey.xqlBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import com.codemonkey.utils.OgnlUtils;
import com.codemonkey.utils.RegUtils;
import com.codemonkey.utils.SysUtils;

/**
          用于动态构造xql或者hql语句
          动态构造示例:
   <pre>
	String xql = "select * from user where 1=1" +
		"/~ and username = #{username} ~/" +
		"/~ and password = #{password} ~/" +
		"/~ and age = ${age} ~/" +
		"/~ and sex = ${sex} ~/" +
		"/~ and name in #{names} ~/";
	<br/>
	
		Map params = new HashMap();
		params.put("username", "bin");
		params.put("age", "12");
		params.put("sex", "");
		List<String> names = new ArrayList<String>();
		names.add("amy");
		names.add("betty");
		params.put("names", names);
	<br/>
		XqlResult result = XqlBuilder.build(xql,params);
		String sql = result.getXql();
		List<Object> queryParams = result.getParams();
	<br/>
	
	结果sql将会等于
	select * from user where 1=1 and username = ? and age=12 and name in ( ? , ?)
	而/~ and password = {password} ~/"这一段由于在filters中password不存在而没有被构造出来
	/~ and sex = [sex] ~/由于sex的值为空串也没有被构造出来
	<br/>

	queryParams将会等于
	{bin , 12 , amy ,  betty}

	<br/>
	相关符号介绍:
	/~ segment... ~/ 为一个条件代码块
	
	#{key} 过滤器中起标记作用的key,作为后面可以替换为sql的?,或是hql的:username标记
	${key} 将直接替换为key value
	
   </pre>
   
   <pre>
           数据类型转换示例:
   select * from user where and 1=1 /~ age=${age?int} ~/
           将会将Map filters中key=age的值转换为int类型
   </pre>

   @author bin

 */
public class XqlBuilder {
	
	private static final String PROP_START = "{";
	private static final String MARK_KEY_START_CHAR = "#{";
	private static final String REPLACE_KEY_START_CHAR = "${";
	private static final String PROP_END = "}";
	
	private static final String CONDITION_START_CHAR = "/~";
	private static final String CONDITION_END_CHAR = "~/";
	
	public static final String PROP_REG = "\\{.*\\}";

	public static XqlResult build(String xql ,  Object param){
		
		//提取非条件语句
		StringBuffer buffer = new StringBuffer(extractXql(xql));
		
		//拆分 条件语句/~ ~/字符串
		List<String> conditionXqls = splitConditionXqls(xql);
		
		List<Object> queryParams = new ArrayList<Object>();
		
		if(conditionXqls != null){
			for(String s : conditionXqls){
				buildXql(buffer , s , queryParams , param);
			}
		}
		
		XqlResult result = new XqlResult();
		result.setParams(queryParams);
		result.setXql(buffer.toString());
		return result;
	}

	public static List<String> splitConditionXqls(String xql) {
		
		List<String> conditions = new ArrayList<String>();
		String splitReg = CONDITION_START_CHAR + "|" + CONDITION_END_CHAR;
		String s = xql.substring(xql.indexOf(CONDITION_START_CHAR));
		String[] ss = s.split(splitReg);
		if(ss != null){
			for(int i = 0 ; i < ss.length ; i++){
				if(SysUtils.isNotEmpty(ss[i])){
					conditions.add(ss[i]);
				}
			}
		}
		return conditions;
	}

	private static void buildXql(StringBuffer buffer , String conditionXql, List<Object> queryParams, Object param) {
		
		if(RegUtils.find(PROP_REG, conditionXql)){
			
			String prop = extracProp(conditionXql);
			
			if(SysUtils.isEmpty(prop)){
				return;
			}
			
			Object value = getValue(prop, param);
			
			if(value != null && !(value instanceof Collection)){
				//非集合值
				String resultSql = null;
				
				if(conditionXql.indexOf(REPLACE_KEY_START_CHAR) >= 0){
					
					resultSql = conditionXql.replace(REPLACE_KEY_START_CHAR + prop + PROP_END , value.toString());
					
				}else if(conditionXql.indexOf(MARK_KEY_START_CHAR) >= 0 ){
					
					resultSql = conditionXql.replace(MARK_KEY_START_CHAR + prop + PROP_END , "?");
					
					queryParams.add(value);
				}
				
				if(SysUtils.isNotEmpty(resultSql)){
					buffer.append(resultSql);
				}
			}else if(value != null && value instanceof Collection){
				//集合值
				Collection<?> list = (Collection<?>) value;
				
				if(SysUtils.isNotEmpty(list)){
					
					String resultSql = null;
					
					if(conditionXql.indexOf(REPLACE_KEY_START_CHAR) > 0){
						//用值替换
						StringBuffer replace = new StringBuffer("(");
						Iterator<?> it = list.iterator();
						
						StringBuffer b = new StringBuffer(); 
						while(it.hasNext()){
							Object v = it.next();
							
							if(v instanceof String || v instanceof Date){
								b.append("'");
								b.append(v.toString());
								b.append("'");
							}else{
								b.append(v.toString());
							}
							
							b.append(',');
						}
						String replaceString = b.toString();
						//起吊结尾的,
						if(replaceString.endsWith(",")){
							replaceString = replaceString.substring(0, replaceString.lastIndexOf(","));
						}
						replace.append(replaceString);
						replace.append(")");
						resultSql = conditionXql.replace(REPLACE_KEY_START_CHAR + prop + PROP_END , replace.toString());
						
						
					}else if(conditionXql.indexOf(MARK_KEY_START_CHAR) > 0 ){
						//用？替换
						StringBuffer marks = new StringBuffer("(");
						
						for(int i = 0 ; i < list.size() ; i ++){
							marks.append(" ? ");
							if( i < list.size() - 1){
								marks.append(",");
							}
						}
						
						marks.append(")");
						
						resultSql = conditionXql.replace(MARK_KEY_START_CHAR + prop + PROP_END , marks);
						
						Iterator<?> it = list.iterator();
						
						while(it.hasNext()){
							queryParams.add(it.next());
						}
						
					}
					
					if(SysUtils.isNotEmpty(resultSql)){
						buffer.append(resultSql);
					}
				}
			}
		}else{
			buffer.append(conditionXql);
		}
	}

	private static Object getValue(String prop, Object param) {
		Object obj = OgnlUtils.objectValue(prop, param);
		
		if(obj instanceof String && SysUtils.isEmpty((String)obj)){
			return null;
		}
		
		if(obj instanceof Collection && SysUtils.isEmpty((Collection<?>)obj)){
			return null;
		}
		
		return obj;
	}

	public static String extracProp(String conditionXql) {
		
		Matcher matcher = RegUtils.buildMatcher(PROP_REG , conditionXql);
		
		if(matcher.find()){
			String prop = matcher.group();
			return prop.replace(PROP_START, "").replace("}", "");
		}
		return null;
	}

	private static String extractXql(String xql) {
		return xql.substring(0 , xql.indexOf(CONDITION_START_CHAR));
	}
	
}
