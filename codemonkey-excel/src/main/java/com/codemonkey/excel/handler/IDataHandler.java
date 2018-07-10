package com.codemonkey.excel.handler;

import java.util.List;

public interface IDataHandler {

    void dataProcess(Object row);
    
    void buildResult();

    List<IDataHandler> getSubHandlers();

	Double getResult();
}
