package com.codemonkey.excel.handler;

public class CounterHandler extends AbsDataHandler {
    
	
	
    @Override
    public void dataProcess(Object row) {
        result += 1;
    }

}
