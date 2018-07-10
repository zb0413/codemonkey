package com.codemonkey.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.joda.time.DateTime;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.codemonkey.annotation.SkipBuild;
import com.codemonkey.domain.AbsEntity;

/**
 * 类描述：class工具类
 */
@Component
public final class ClassHelper {

	private static Logger logger = LoggerFactory.getLogger(ClassHelper.class);

	private ClassHelper() {
	}

	
	/**
	  * 方法描述：取得实体属性值
	  * @param: entity 实体
	  * @param: fieldName 属性名
	  * @return: 属性值
	  * @author: wy
	  */
	public static Object getFieldValue(AbsEntity entity, String fieldName) {

		Field field = null;
		try {
			field = getFieldFromClazz(entity.getClass(), fieldName);
			return callGetter(entity, field);
		} catch (Exception e) {
			logger.error(" call get" + StringUtils.capitalize(field.getName())
					+ " FAILED!!!!! ");
		}
		return null;
	}

	/**
	  * 方法描述：设置对象属性值
	  * @param: obj 实体
	  * @param: field 属性
	  * @param: value 属性值
	  * @return: void
	  * @author: wy
	  */
	public static void callSetter(Object obj, Field field, Object value) {
		callSetter(obj, field.getName(), value);
	}

	/**
	  * 方法描述：设置对象属性值
	  * @param: obj 实体
	  * @param: field 属性
	  * @param: value 属性值
	  * @return: void
	  * @author: wy
	  */
	public static void callSetter(Object obj, String fieldName, Object value) {

		Method setter = null;

		try {
			String setterName = "set" + StringUtils.capitalize(fieldName);
			Field field = getFieldFromClazz(obj.getClass(), fieldName);
			if (field != null) {
				setter = ClassUtils.getMethodIfAvailable(obj.getClass(),
						setterName, field.getType());

				if (setter != null && field != null) {
					setter.invoke(obj, value);
				} else {
					logger.info(setterName + " is NOT FOUND !!!");
				}
			}

		} catch (Exception e) {
			logger.error("call " + setter.getName() + " IN "
					+ obj.getClass().getSimpleName() + " FAILED!!!! ");
		}
	}

	/**
	  * 方法描述：取得对象属性
	  * @param: clazz 类
	  * @param: fieldName 属性名
	  * @return: 属性
	  * @author: wy
	  */
	public static Field getFieldFromClazz(Class<?> clazz, String fieldName) {
		Class<?> clazz1 = clazz;
		while (!clazz1.equals(Object.class)) {
			Field[] fields = clazz1.getDeclaredFields();
			if (fields != null) {
				for (int i = 0; i < fields.length; i++) {
					if (fields[i].getName().equals(fieldName)) {
						return fields[i];
					}
				}
			}
			clazz1 = clazz1.getSuperclass();
		}
		return null;
	}

	public static Object callGetter(Object obj, Field field) {
		return callGetter(obj, field.getName());
	}

	public static Object callGetter(Object obj, String fieldName) {
		Method getter = null;
		Object value = null;
		if (StringUtils.isBlank(fieldName)) {
			return null;
		}
		try {
			getter = ClassUtils.getMethodIfAvailable(obj.getClass(), "get"
					+ StringUtils.capitalize(fieldName));

			if (getter != null) {
				value = getter.invoke(obj);
			}
			return value;

		} catch (Exception e) {
			logger.error("call " + getter.getName() + " FAILED!!!! ");
		}
		return null;
	}

	public static Object getFieldValue(String columnName, Class<?> type,
			SqlRowSet rowSet) {
		Object value = rowSet.getObject(columnName);
		if (value != null && value.getClass().equals(BigDecimal.class)) {
			value = ((BigDecimal) value).doubleValue();
		}
		return value;
	}

	public static List<String> getAllFieldNames(Class<?> type) {
		Class<?> type1 = type;
		List<String> list = new ArrayList<String>();
		try {
			while (!type1.equals(Object.class)) {
				Field[] fields = type1.getDeclaredFields();
				if (fields != null) {
					for (int i = 0; i < fields.length; i++) {
						String name = fields[i].getName();
						if (!"serialVersionUID".equals(name)) {
							list.add(name);
						}
					}
				}
				type1 = type1.getSuperclass();
			}
		} catch (Exception e) {
			logger.error("call getAllFieldNames " + type1 + " FAILED!!!! ");
		}
		return list;
	}

	public static List<Field> getAllFields(Class<?> clazz) {
		return getAllFields(clazz, Object.class);
	}

	public static List<Field> getAllFields(Class<?> clazz, Class<?> rootClass) {
		List<Field> fields = new ArrayList<Field>();
		Class<?> clazz1 = clazz;
		while (!clazz1.equals(rootClass)) {
			Field[] fs = clazz1.getDeclaredFields();
			if (fs != null) {
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					if (!f.getName().equals("serialVersionUID")
							&& f.getAnnotation(Version.class) == null) {
						fields.add(f);
					}
				}
			}
			clazz1 = clazz1.getSuperclass();
		}
		return fields;
	}

	@SuppressWarnings("unchecked")
	public static void build(JSONObject params, Object model,
			FormattingConversionServiceFactoryBean ccService) {
		Iterator<String> it = params.keys();
		while (it.hasNext()) {
			String key = it.next();
			if (key.endsWith("_text")) {
				continue;
			}

			Field field = ReflectionUtils.findField(model.getClass(), key);
			if (field != null && field.getAnnotation(SkipBuild.class) == null) {
				Object value = getValueFromJson(field, key, params, ccService);
				ClassHelper.callSetter(model, field, value);
			}
		}
	}

	private static Object getValueFromJson(Field field, String name,
			JSONObject params, FormattingConversionServiceFactoryBean ccService) {

		if (StringUtils.isBlank(params.getString(name))
				|| params.getString(name).equals("")
				|| params.getString(name).equals("undefined")) {
			return null;
		}

		if (field == null || field.getType().equals(List.class)
				|| field.getType().equals(Set.class)) {
			return null;
		}

		return convert(params.getString(name), field.getType(), ccService);
	}

	public static Object convert(String id, Class<?> clazz,
			FormattingConversionServiceFactoryBean ccService) {
		return ccService.getObject().convert(id, clazz);
	}

	public static Class<?> getSuperClassGenricType(Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class<?>) params[index];
	}

	public static Object stringToEnum(Class<?> clazz, String value) {
		try {
			Method method = ClassUtils.getMethodIfAvailable(clazz, "valueOf",
					String.class);
			if (method != null) {
				return method.invoke(clazz, value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Class<?> getGenricType4Collection(Class<?> clazz) {

		Type[] genType = clazz.getGenericInterfaces();

		return (Class<?>) genType[0];

	}


	public static Object callMethod(Class<?> clazz, String method) {
		
		try {
			Method m = ClassUtils.getMethodIfAvailable(clazz, method);
			if (m != null ) {
				return m.invoke(clazz);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//geting implement class from proxy
	public static Object getTarget(Object c){
		if(c == null) {
			return null;
		}
		
		if(AopUtils.isAopProxy(c)){
			return AopUtils.getTargetClass(c);
		}
		
		if(c instanceof HibernateProxy) {
			return ((HibernateProxy) c).getHibernateLazyInitializer().getImplementation();
		}	
		return c;
	}
	
	/**  
	  * 判断一个类是否为基本数据类型。  
	  * @param clazz 要判断的类。  
	  * @return true 表示为基本数据类型。  
	  */  
	public static boolean isPrimitive(Class<?> clazz) {
		return (clazz.equals(String.class) || clazz.equals(Integer.class)
				|| clazz.equals(Byte.class) || clazz.equals(Long.class)
				|| clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(Character.class) || clazz.equals(Short.class)
				|| clazz.equals(BigDecimal.class)
				|| clazz.equals(BigInteger.class)
				|| clazz.equals(Boolean.class) || clazz.equals(Date.class)
				|| clazz.equals(DateTime.class) || clazz.isPrimitive());
	}
	
	public static boolean isPrimitive(Object obj) {
		return isPrimitive(obj.getClass());
	}
	
	/**  
	  * 判断一个类是否为枚举。  
	  * @param clazz 要判断的类。  
	  * @return true 表示为枚举。  
	  */  
	public static boolean isEnum(Class<?> clazz){
		return clazz.isEnum();
	}
	
	public static boolean isEnum(Object obj) {
		return isEnum(obj.getClass());
	}
	
}
