package com.codemonkey.excel.reader;

import com.codemonkey.excel.handler.IDataHandler;

import java.util.List;

public interface IRowReader {  
    
    void getRow(int sheetIndex,int curRow, List<String> rowlist , List<String> headerRowList);

    void processRow(List<String> rowlist, List<String> headerRowList, IDataHandler handler);
    
    int getHeaderRows();
}
 