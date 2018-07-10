package com.codemonkey.dao;

import com.codemonkey.domain.seq.YearedSequenceCreator;

/**
 * 类描述：编码生成Dao
 * 
 */
public interface YearedSequenceCreatorDao {

	/**
	 * 方法描述：通过规则生成编码
	 * 
	 * @param:sequenceCreator 规则引擎
	 * @return:生成的编码
	 * @author: wy
	 */
	String fetch(YearedSequenceCreator sequenceCreator);

}
