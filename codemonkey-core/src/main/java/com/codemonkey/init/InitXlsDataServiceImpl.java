package com.codemonkey.init;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(6)
@Transactional
public class InitXlsDataServiceImpl implements InitDataBean{

	@Autowired
	@Resource(name="dataSource")
	private DataSource datasource;
	
	private Logger logger = SysUtils.getLog(InitXlsDataServiceImpl.class);
	
	@Override
	public void doInit() {
		
			
		if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
			logger.info("***********************************************************");
			logger.info("*            start importing xls files                    *");
			logger.info("***********************************************************");
			
			SysUtils.loadDataToDB(datasource, "classpath:init-data/*-default-data-*.xls");
			
			logger.info("***********************************************************");
			logger.info("*              end importing xls files                    *");
			logger.info("***********************************************************");
			
			
		}
	}
}
