package com.codemonkey.dao;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.domain.seq.YearedSequenceCreator;

@Component
public class YearedSequenceCreatorDaoImpl extends GenericDao<YearedSequenceCreator> implements YearedSequenceCreatorDao {

	@Autowired
	public YearedSequenceCreatorDaoImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public String fetch(YearedSequenceCreator sc) {
		
		String query = sc.getQuery();
		
		long count = countBy(query, sc.getQueryParams());
		
		if(count == 0){
			save(sc);
		}
		
		YearedSequenceCreator sc2 = findBy(query, sc.getQueryParams());
		sc2.setCurrent(sc2.getCurrent()+ sc2.getStep());
		save(sc2);
		return sc2.getSequence();
	}
	
}
