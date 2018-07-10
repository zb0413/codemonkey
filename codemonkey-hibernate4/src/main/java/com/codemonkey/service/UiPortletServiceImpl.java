package com.codemonkey.service;

import org.springframework.stereotype.Service;

import com.codemonkey.domain.UiPortlet;

@Service
public class UiPortletServiceImpl extends LogicalServiceImpl<UiPortlet> implements UiPortletService{

	@Override
	public UiPortlet createEntity() {
		return new UiPortlet();
	}

}
