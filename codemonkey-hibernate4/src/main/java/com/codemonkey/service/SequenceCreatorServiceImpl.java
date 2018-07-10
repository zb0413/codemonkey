package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.seq.SequenceCreator;

@Service
public class SequenceCreatorServiceImpl extends PhysicalServiceImpl<SequenceCreator> implements SequenceCreatorService{

	@Override
	public SequenceCreator createEntity() {
		return new SequenceCreator();
	}
	

	
}
