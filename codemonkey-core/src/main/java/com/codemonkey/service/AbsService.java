package com.codemonkey.service;

import org.apache.log4j.Logger;

import com.codemonkey.utils.SysUtils;

public abstract class AbsService {

	private Logger log = SysUtils.getLog(getClass());

	public Logger getLog() {
		return log;
	}
}
