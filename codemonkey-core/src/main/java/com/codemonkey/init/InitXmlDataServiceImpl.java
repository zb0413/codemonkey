package com.codemonkey.init;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(7)
@Transactional
public class InitXmlDataServiceImpl implements InitDataBean{

	@Autowired
	@Resource(name="dataSource")
	private DataSource datasource;
	
	private Logger logger = SysUtils.getLog(InitXmlDataServiceImpl.class);
	
	@Override
	public void doInit() {
		
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			logger.info("***********************************************************");
			logger.info("*            start importing xml files                    *");
			logger.info("***********************************************************");
			
			SysUtils.loadDataToDB(datasource, "classpath:init-data/*-default-data-*.xml");
			
			logger.info("***********************************************************");
			logger.info("*              end importing xml files                    *");
			logger.info("***********************************************************");
			
		}
	}
}
