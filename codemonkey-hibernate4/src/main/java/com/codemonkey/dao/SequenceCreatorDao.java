package com.codemonkey.dao;

import com.codemonkey.domain.seq.SequenceCreator;

/**
 * 类描述：编码生成Dao
 * 
 */
public interface SequenceCreatorDao {

	/**
	 * 方法描述：通过规则生成编码
	 * 
	 * @param:sequenceCreator 规则引擎
	 * @return:生成的编码
	 * @author: wy
	 */
	String fetch(SequenceCreator sequenceCreator);
	
	/**
	 * 方法描述：通过规则生成编码
	 * 
	 * @param:sequenceCreator 规则引擎
	 * @return:生成的编码
	 * @author: wy
	 */
	 void save(SequenceCreator sequenceCreator);

}
