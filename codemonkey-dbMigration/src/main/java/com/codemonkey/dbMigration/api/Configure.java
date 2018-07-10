package com.codemonkey.dbMigration.api;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.codemonkey.utils.SysUtils;

/**
 * Tells DBManager where migration classes are located and how to connect to the
 * database.
 * 
 */
public final class Configure {

	private static Properties propertiesHolder = new Properties();
	
	private static final String APPLICATION_PROPERTY_FILE = "classpath*:dbMigration.properties";
	
	public static final PathMatchingResourcePatternResolver PATTEN_RESOLVER = new PathMatchingResourcePatternResolver();

	private static Logger log = SysUtils.getLog(Configure.class);

	private Configure(){}
	
	public static void configure() {
		try {
			Resource[] resources = PATTEN_RESOLVER.getResources(APPLICATION_PROPERTY_FILE);
			if(resources != null && resources.length > 0){
				log.info("load config from " + resources[0]);
				InputStream in = resources[0].getInputStream();
				if(in != null){
					propertiesHolder.load(in);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getStringProperty(String key){
		return propertiesHolder.getProperty(key);
	}
}
