package com.codemonkey.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.codemonkey.domain.AbsEntity;

/**
 * 类描述：db工具类
 */
public class DBHelper {

	private Logger logger = SysUtils.getLog(DBHelper.class);
	
	private static final String[] PROPS_PRIFIX = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	private static final String[] COLUMN_PRIFIX = {"_a","_b","_c","_d","_e","_f","_g","_h","_i","_j","_k","_l","_m","_n","_o","_p","_q","_r","_s","_t","_u","_v","_w","_x","_y","_z"};
	
	private static final String[] ESCAPE_PROPS = {"serialVersionUID"}; 
	
	private JdbcTemplate jdbcTemplate;
	
	public void saveOrUpdate(AbsEntity entity){
		if(entity.getId() == null){
			save(entity);
		}else{
			update(entity);
		}
	}
	
	private void update(AbsEntity entity) {
		String sql = " UPDATE " + getTableName(entity.getClass());
		sql += buildUpdateSettersSql(entity);
		List<Object> values = extractUpdateValues(entity);
		jdbcTemplate.update(sql, values.toArray());
		
	}

	private String buildUpdateSettersSql(AbsEntity entity) {
		String sql = " SET ";
		List<Field> fields = getAvailableFields(entity.getClass());
		for(int i = 0 ; i < fields.size() ; i++ ){
			if(i != fields.size() - 1 ){
				sql += getColumnName(fields.get(i).getName()) + " = ?, ";
			}else{
				sql += getColumnName(fields.get(i).getName()) + " = ? ";
			}
		}
		sql += " WHERE id = ? " ;
		return sql;
	}

	private void save(AbsEntity entity) {
		String sql = " INSERT INTO " + getTableName(entity.getClass());
		sql += buildInsertValuesSql(entity.getClass());
		List<Object> values = extractInsertValues(entity);
		jdbcTemplate.update(sql, values.toArray());
	}

	private List<Object> extractInsertValues(AbsEntity entity) {
		List<Object> values = new ArrayList<Object>();
		List<Field> fields = getAvailableFields(entity.getClass());
		
		for(Field field : fields){
			Object value = ClassHelper.callGetter(entity , field);
			values.add(value);
		}
		
		return values;
	}
	
	private List<Object> extractUpdateValues(AbsEntity entity) {
		List<Object> values = extractInsertValues(entity);
		Object id = entity.getId();
		values.add(id);
		return values;
	}

	private String buildInsertValuesSql(Class<?> clazz) {
		String sql = " VALUES ( ";
		List<Field> fields = getAvailableFields(clazz);
		for(int i = 0 ; i < fields.size() ; i++ ){
			if(i != fields.size()-1 ){
				sql += " ?, ";
			}else{
				sql += " ? ";
			}
		}
		sql += " ) ";
		return sql;
	}
	
	private List<Field> getAvailableFields(Class<?> clazz){
		List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
		List<Field> availableFields = new ArrayList<Field>();
		if(fields == null || fields.isEmpty()) {
			return new ArrayList<Field>();
		}
		for(Field field : fields){
			if(!Arrays.asList(ESCAPE_PROPS).contains(field.getName())){
				availableFields.add(field);
			}
		}
		return availableFields;
	}

	public List<AbsEntity> all(Class<?> clazz){
		 
		String sql = buildQuerySql(clazz);
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
		List<AbsEntity> list = buildResultList(clazz , rowSet);
		
		return list;
	
	}
	
	/**
	 * @param clazz
	 * @param query
	 * @param values
	 * @return List<AbsEntity>
	 * 
	 *  supported operations :
	 *  
	 *  Equal , NotEqual , IsNull , IsNotNull , LessThan , LessThanEquals , 
	 *  GreaterThan , GreaterThanEquals , Between , Like , Ilike
	 *  
	 *  examples:
	 * 
	 *  select * from api.glaccount where account_number = '1000'
	 *	accounts = dbHelper.findAll(Glaccount.class, "accountNumber", "1000");
	 *
	 *  select * from api.glaccount where account_number Like '10%'
	 *	accounts = dbHelper.findAll(Glaccount.class, "accountNumberLike", "10%");
	 *
	 *  select * from api.glaccount where company='01' and sub_type = 'CA'
	 *	accounts = dbHelper.findAll(Glaccount.class, "companyAndSubType", "01" , "CA");
	 */
	public List<AbsEntity> findAll(Class<?> clazz , String query , Object... values ){
		
		String sql = buildQuerySql(clazz , query);
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql , values);
		
		List<AbsEntity> list = buildResultList(clazz , rowSet);
		
		return list;
	}
	
	public AbsEntity find(Class<?> clazz , String query , Object... values ){
		
		String sql = buildQuerySql(clazz , query);
		
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql , values);
		
		AbsEntity entity = buildEntity(clazz ,rowSet);
		
		return entity;
	}
	
	public long count(Class<?> clazz , String query , Object... values ){
		
		String sql = buildQuerySql(clazz , query);
		
		String countSql = StringUtils.replace(sql, "SELECT *", "SELECT COUNT(*)");
		
		long totalCount = jdbcTemplate.queryForObject(countSql, Long.class, values);
		
		return totalCount;
	}
	
	public long count(Class<?> clazz){
		return count(clazz , "");
	}
	
	protected String getPageCondition(int pageIndex, int pageSize){
		return " LIMIT " + pageSize + " OFFSET " + (pageSize * (pageIndex - 1));
	}

	private List<AbsEntity> buildResultList(Class<?> clazz, SqlRowSet rowSet) {
		
		List<AbsEntity> list = new ArrayList<AbsEntity>();
		
		while(!rowSet.isLast()){
			AbsEntity entity = buildEntity(clazz ,rowSet);
			if(entity != null){
				list.add(entity);
			}
		}
		return list;
	}
	
	AbsEntity buildEntity(Class<?> clazz , SqlRowSet rowSet){
		try {
			rowSet.next();
			AbsEntity entity = (AbsEntity) clazz.newInstance();
			List<Field> fields = getAvailableFields(clazz);
			
			for(Field field : fields){
				String columnName = getColumnName(field.getName());
				Object value = ClassHelper.getFieldValue(columnName , field.getType() , rowSet);
				ClassHelper.callSetter(entity , field , value);
			}
			return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getColumnName(String fieldName) {
		
		String columnName = StringUtils.replaceEach(fieldName, PROPS_PRIFIX, COLUMN_PRIFIX);
		
		if(columnName.startsWith("_")){
			columnName = columnName.substring(1);
		}
		return columnName;
	}

	String buildQuerySql(Class<?> clazz){
		
		return "SELECT * FROM " + getTableName(clazz) + " WHERE 1=1 ";
	
	}
	
	String buildQuerySql(Class<?> clazz , String query ){
		
		String sql = buildQuerySql(clazz);
		sql += buildConditions(query);
		
		logger.info(sql);
		return sql;
	}
	
	private String buildConditions(String query) {
		String conditions = "";
		String findBy = query;
		if(StringUtils.isNotBlank(query)){
			StringBuffer jpql = new StringBuffer();
			 
	        String[] parts = findBy.split("And");
	        for (int i = 0; i < parts.length; i++) {
	            String part = parts[i];
	            
	            if (i <= parts.length - 1) {
	                jpql.append(" AND ");
	            }
	            
	            if (part.endsWith("NotEqual")) {
	                String prop = extractProp(part, "NotEqual");
	                jpql.append(prop);
	                jpql.append( " <> ? ");
	            } else if (part.endsWith("Equal")) {
	                String prop = extractProp(part, "Equal");
	                jpql.append(prop);
	                jpql.append(" = ? ");
	            } else if (part.endsWith("IsNotNull")) {
	                String prop = extractProp(part, "IsNotNull");
	                jpql.append(prop);
	                jpql.append(" is not null");
	            } else if (part.endsWith("IsNull")) {
	                String prop = extractProp(part, "IsNull");
	                jpql.append(prop);
	                jpql.append(" is null");
	            } else if (part.endsWith("LessThan")) {
	                String prop = extractProp(part, "LessThan");
	                jpql.append(prop);
	                jpql.append(" < ?");
	            } else if (part.endsWith("LessThanEquals")) {
	                String prop = extractProp(part, "LessThanEquals");
	                jpql.append(prop);
	                jpql.append(" <= ?");
	            } else if (part.endsWith("GreaterThan")) {
	                String prop = extractProp(part, "GreaterThan");
	                jpql.append(prop);
	                jpql.append(" > ?");
	            } else if (part.endsWith("GreaterThanEquals")) {
	                String prop = extractProp(part, "GreaterThanEquals");
	                jpql.append(prop);
	                jpql.append(" >= ?");
	            } else if (part.endsWith("Between")) {
	                String prop = extractProp(part, "Between");
	                jpql.append(prop);
	                jpql.append(" < ? AND ");
	                jpql.append(prop);
	                jpql.append( " > ?");
	            } else if (part.endsWith("Like")) {
	                String prop = extractProp(part, "Like");
	                jpql.append(prop);
	                jpql.append(" like ?");
	            } else if (part.endsWith("Ilike")) {
	                String prop = extractProp(part, "Ilike");
	                jpql.append("LOWER(");
	                jpql.append(prop);
	                jpql.append(") like LOWER(?)");
	            } else {
	                String prop = extractProp(part, "");
	                jpql.append(prop);
	                jpql.append(" = ?");
	            }
	        }
	        return jpql.toString();
		}
		
		return conditions;
	}
	
	private String extractProp(String part, String end) {
        String prop = part.substring(0, part.length() - end.length());
        prop = getColumnName(prop);
        return prop;
    }

	private String getTableName(Class<?> clazz) {
		return "api." + clazz.getSimpleName().toLowerCase();
	}
	
}
