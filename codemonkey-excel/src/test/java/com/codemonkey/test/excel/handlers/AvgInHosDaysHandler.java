package com.codemonkey.test.excel.handlers;

import java.util.ArrayList;
import java.util.List;

import com.codemonkey.excel.handler.AbsDataHandler;
import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.utils.Calc;

public class AvgInHosDaysHandler extends AbsDataHandler {

    //出院人数处理器
    private OutHosPatsHandler outHosPatsHandler;

    //住院天数处理器
    private InHosDaysHandler inHosDaysHandler;

    public AvgInHosDaysHandler(){
    	this.outHosPatsHandler = new OutHosPatsHandler();
    	this.inHosDaysHandler = new InHosDaysHandler();
    }
    
    @Override
    public void dataProcess(Object row) {
        outHosPatsHandler.dataProcess(row);
        inHosDaysHandler.dataProcess(row);
    }

    @Override
   	public List<IDataHandler> getSubHandlers() {
    	List<IDataHandler> handlers = new ArrayList<IDataHandler>();
    	handlers.add(outHosPatsHandler);
    	handlers.add(inHosDaysHandler);
    	return handlers;
   	}
    
    @Override
   	public void buildResult() {
       	this.result = Calc.div(inHosDaysHandler.getResult(), outHosPatsHandler.getResult());
   	}
}
