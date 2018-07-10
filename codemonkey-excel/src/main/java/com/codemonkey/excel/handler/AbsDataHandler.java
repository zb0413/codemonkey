package com.codemonkey.excel.handler;

import java.util.List;

public abstract class AbsDataHandler implements IDataHandler {
    //数据结果
	protected Double result = 0d;
    
    private String detailResult;

    public Double getResult(){
        return result;
    }

    @Override
	public List<IDataHandler> getSubHandlers() {
		return null;
	}
    
    @Override
	public void buildResult() {
		
	}

	public String getDetailResult() {
		return detailResult;
	}

	public void setDetailResult(String detailResult) {
		this.detailResult = detailResult;
	}

}
