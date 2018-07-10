package com.codemonkey;

import org.apache.log4j.Logger;


public final class CUtils {
	private static Logger log = Logger.getLogger(CUtils.class);
	
	private CUtils(){}
	
	public static void print(Object obj){
		log.info(obj);
	}
}
