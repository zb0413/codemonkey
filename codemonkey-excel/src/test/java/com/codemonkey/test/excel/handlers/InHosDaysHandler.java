package com.codemonkey.test.excel.handlers;

import java.util.Map;

import com.codemonkey.excel.handler.AbsDataHandler;
import com.codemonkey.utils.SysUtils;

@SuppressWarnings("unchecked")
public class InHosDaysHandler extends AbsDataHandler {
	@Override
    public void dataProcess(Object obj) {
    	if(obj instanceof Map){
    		Map<String , String> map = (Map<String, String>) obj;
    		if(SysUtils.isNotEmpty(map.get("天数"))){
    			this.result += Double.valueOf(map.get("天数"));
    		}
    	}
    }

}
