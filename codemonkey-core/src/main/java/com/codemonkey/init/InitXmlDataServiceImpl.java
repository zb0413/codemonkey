package com.codemonkey.init;

import java.io.File;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codemonkey.SysProp;
import com.codemonkey.utils.SysUtils;

@Component
@InitOrder(7)
@Transactional
public class InitXmlDataServiceImpl implements InitDataBean{

	@Autowired
	@Resource(name="dataSource")
	private DataSource datasource;
	
	@Autowired private SysProp sysProp;
	
	private Logger logger = SysUtils.getLog(InitXmlDataServiceImpl.class);
	
	@Override
	public void doInit() {
		
		if(sysProp.isLoadInitData()){
			
			if(!SysUtils.fileExists(SysUtils.DATA_INITED)){
				logger.info("***********************************************************");
				logger.info("*            start importing xml files                    *");
				logger.info("***********************************************************");
				
				SysUtils.loadDataToDB(datasource, "classpath:init-data/*-default-data-*.xml");
				
				logger.info("***********************************************************");
				logger.info("*              end importing xml files                    *");
				logger.info("***********************************************************");
				
				File f = SysUtils.createFile(SysUtils.DATA_INITED);
				if(f != null){
					logger.info("create dataInitFile : " + f.getAbsolutePath());
				}
			}
		}
	}
}
