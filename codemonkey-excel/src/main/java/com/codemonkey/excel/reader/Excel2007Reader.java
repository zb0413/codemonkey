package com.codemonkey.excel.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.utils.SysUtils;

public class Excel2007Reader extends AbsExcelReader {

    private InputStream inputStream;

	private Set<IDataHandler> dataHandlers;

	private List<IDataHandler> tempHandlers;
      
    public Excel2007Reader(InputStream inputStream,Set<IDataHandler>  dataHandlers){
        this.inputStream = inputStream;
		this.dataHandlers = dataHandlers;
		init();
    }

	private void init() {
		if(SysUtils.isNotEmpty(dataHandlers)){
			//转换成有序列表
			tempHandlers = new ArrayList<IDataHandler>();
			
			for(IDataHandler d : dataHandlers){
				regSubHandlers(tempHandlers , d);
			}
		}
	}

	private void regSubHandlers(List<IDataHandler> handlers, IDataHandler d) {
		
		handlers.add(d);
		
		List<IDataHandler> subHandlers = d.getSubHandlers();
		
		if(SysUtils.isNotEmpty(subHandlers)){
			for(IDataHandler s : subHandlers){
				regSubHandlers(handlers , s);
			}
		}
	}

	@Override
	public void process(int sheetIndex, IRowReader rowReader) {
		OPCPackage pkg = null;
		InputStream sheetInputStream = null;
		if(null==rowReader) {
			throw new IllegalArgumentException("rowReader cannot be null!");
		}
		try {
			pkg = OPCPackage.open(inputStream);
			this.setRowReader(rowReader);
			XSSFReader xssfReader = new XSSFReader(pkg);

			StylesTable styles = xssfReader.getStylesTable();
			ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
			int index = 0;
			Iterator<InputStream> it = xssfReader.getSheetsData();
			while(it.hasNext()){
				sheetInputStream = it.next();
				if(index == sheetIndex){
					processSheet(styles, strings, sheetInputStream);
				}
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sheetInputStream != null){
				try {
					sheetInputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			if(pkg != null){
				try {
					pkg.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			//处理完成之后清除数据,释放内存
			cleanData();
		}
	}


	private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream) throws SAXException, ParserConfigurationException, IOException{
        XMLReader sheetParser = SAXHelper.newXMLReader();   
        final IRowReader rowReader = this.getRowReader();
        sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler(){
            protected List<String> row = new ArrayList<String>();  
            protected List<String> header = new ArrayList<String>();
            protected int currentRowNum = 0;
            protected List<String> headerCellIndex = new ArrayList<String>();
            
            @Override  
            public void startRow(int rowNum) {
            	currentRowNum = rowNum;
                row.clear();  
            }  
      
            @Override  
            public void endRow(int rowNum) {
//				System.err.println("rowReader.getHeaderRows() : " + rowReader.getHeaderRows());
//				System.err.println("rowNum : " + rowNum);
				if (rowNum < rowReader.getHeaderRows()) {
				} else {
					if(SysUtils.isNotEmpty(tempHandlers)){
						for(int i = 0 ; i < tempHandlers.size() ; i++){
							IDataHandler dataHandler = tempHandlers.get(i);
							rowReader.processRow(row, header, dataHandler);
						}
					}
				}
			}

            @Override  
            public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            	String cellRef = cellReference.replaceAll("\\d", "");
        		headerCellIndex.add(cellRef);
        		
            	if(currentRowNum < rowReader.getHeaderRows()){
            	
            		header.add(formattedValue);
            	}else{
            		String ref = cellReference.replaceAll("\\d", "");
            		Integer index = getCellIndex(ref);
            		while(row.size() < index){
            			row.add("");
            		}
            		row.add(index , formattedValue);
            	}
            }
      
            private Integer getCellIndex(String cellReference) {
            	
            	for(int i = 0 ; i < headerCellIndex.size(); i++){
            		String s = headerCellIndex.get(i);
            		if(s.equals(cellReference)){
            			return i;
            		}
            	}
            	
				return null;
			}

			@Override  
            public void headerFooter(String text, boolean isHeader, String tagName) {  
                  
            }  

        	
        } , false));  
          
        sheetParser.parse(new InputSource(sheetInputStream));
		buildResult();
    }
	private void buildResult(){
		if(SysUtils.isNotEmpty(tempHandlers)){
			for(int i = 0 ; i < tempHandlers.size() ; i++){
				IDataHandler dataHandler = tempHandlers.get(i);
				dataHandler.buildResult();
			}
		}
	}
}  
