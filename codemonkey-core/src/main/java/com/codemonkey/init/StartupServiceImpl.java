package com.codemonkey.init;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.codemonkey.utils.ClassHelper;
import com.codemonkey.utils.SysUtils;

@Component
@Lazy(false)
@Transactional
public class StartupServiceImpl implements StartupService{

	@Autowired private List<InitDataBean> list;
	
	private static Logger logger = SysUtils.getLog(StartupServiceImpl.class);
	
	@PostConstruct
	public void onStartup(){
		
		if(SysUtils.isNotEmpty(list)){
		
			Collections.sort(list, new Comparator <InitDataBean>(){
	            public int compare(InitDataBean obj1 , InitDataBean obj2) {
	                int order1 = getOrder(obj1);
	                int order2 = getOrder(obj2);
	            	return order1 - order2;
	            }
	        });
			
			for(InitDataBean b : list){
				logger.info("init data by : " + SysUtils.getTarget(b).getClass());
				b.doInit();
			}
			
			File f = SysUtils.createFile(SysUtils.DATA_INITED);
			if(f != null){
				logger.info("create dataInitFile : " + f.getAbsolutePath());
			}
			
		}
	}
	
	private int getOrder(InitDataBean obj1) {
		Object obj = ClassHelper.getTarget(obj1);
		Class<?> clazz = null;
		if(obj instanceof Class){
			clazz = (Class<?>) obj;
		}else{
			clazz = obj.getClass();
		}
		
		InitOrder anno = (InitOrder) clazz.getAnnotation(InitOrder.class);
		if(anno != null){
			return anno.value();
		}
		return 0;
	}
}
