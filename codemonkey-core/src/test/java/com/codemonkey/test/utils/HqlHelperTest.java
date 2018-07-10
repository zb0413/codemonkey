package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.codemonkey.domain.FunctionNode;
import com.codemonkey.utils.HqlHelper;

public class HqlHelperTest {

	@Test
	public void testBuildHql(){
		
		List<String> params = new ArrayList<String>();
		String hql = HqlHelper.findBy(FunctionNode.class, "name_IN" , params);
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And  1=1 " , hql);
		
		params.add("test1");
		params.add("test2");
		hql = HqlHelper.findBy(FunctionNode.class, "name_IN" , params);
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name IN ( ?0 ,  ?1 ) " , hql);
		
		params = new ArrayList<String>();
		params.add("test1");
		params.add("test2");
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex&&name_IN" , 2 , params );
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex = ?0 And E.name IN ( ?1 ,  ?2 ) " , hql);
		
		
		hql = HqlHelper.findBy(FunctionNode.class, "name");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name = ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "name_EQ");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name = ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_LE");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex <= ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_LT");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex < ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_GE");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex >= ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_GT");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex > ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "name_Like");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name like ?0" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_isNull");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex is null " , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_isNotNull");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex is not null " , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_notEQ");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.sortIndex <> ?0" , hql);
		
//		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_notEQ&&Bars.name_Like");
//		
//		assertEquals("SELECT E FROM com.codemonkey.domain.FunctionNode E  LEFT JOIN E.bars as bars where 1=1   And E.sortIndex <> ?  And bars.name like ? " , hql);
	
//		hql = HqlHelper.findBy(FunctionNode.class, "sortIndex_notEQ&&Bars.name_Like||Bars.code_Like");
//		
//		assertEquals("SELECT E FROM com.codemonkey.domain.FunctionNode E  LEFT JOIN E.bars as bars where 1=1   And E.sortIndex <> ?  And  ( bars.name like ?  OR bars.code like ?  ) " , hql);
	
		hql = HqlHelper.findBy(FunctionNode.class, "name_EQ-OrderBy-name_DESC");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name = ?0 Order By E.name DESC" , hql);
		
		hql = HqlHelper.findBy(FunctionNode.class, "name_EQ-OrderBy-name_DESC&&sortIndex_ASC");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And E.name = ?0 Order By E.name DESC , E.sortIndex ASC" , hql);
	
		hql = HqlHelper.findBy(FunctionNode.class, "name||sortIndex");
		
		assertEquals("SELECT DISTINCT E FROM com.codemonkey.domain.FunctionNode E  where 1=1   And  ( E.name = ?0 OR E.sortIndex = ?1 ) " , hql);
		
//		hql = HqlHelper.findBy(FunctionNode.class, "parent.bars.name_Like");
//		
//		assertEquals("SELECT E FROM com.codemonkey.domain.FunctionNode E  LEFT JOIN E.parent as parent LEFT JOIN parent.bars as bars where 1=1   And bars.name like ? " , hql);
		
	}
	
}
