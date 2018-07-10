package com.codemonkey.excel.reader;

import com.codemonkey.error.SysError;
import com.codemonkey.excel.handler.IDataHandler;
import com.codemonkey.utils.SysUtils;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** 
 * 抽象Excel2003读取器，通过实现HSSFListener监听器，采用事件驱动模式解析excel2003 
 * 中的内容，遇到特定事件才会触发，大大减少了内存的使用。 
 * 
 */  
public  class Excel2003Reader extends AbsExcelReader implements HSSFListener {  
	
	private InputStream inputStream;

    private Set<IDataHandler> dataHandlers;

    private List<IDataHandler> tempHandlers;

    private int minColumns = -1;  
    private POIFSFileSystem fs;  
    private int lastRowNumber;  
    private int lastColumnNumber;  
  
    /** Should we output the formula, or the value it has? */  	
    private boolean outputFormulaValues = true;  
  
    /** For parsing Formulas */  
    private SheetRecordCollectingListener workbookBuildingListener;  
    //excel2003工作薄  
    private HSSFWorkbook stubWorkbook;  
  
    // Records we pick up as we process  
    private SSTRecord sstRecord;  
    private FormatTrackingHSSFListener formatListener;  
  
    //表索引  
    private int currentSheetIndex = -1;  
    //需要读取的sheet
    private int sheetIndex = -1; 
    
    private BoundSheetRecord[] orderedBSRs;  
    
    @SuppressWarnings("rawtypes")
	private ArrayList boundSheetRecords = new ArrayList();  
  
    // For handling formulas with string results  
    private int nextRow;  
    private int nextColumn;  
    private boolean outputNextStringRecord;  
    //当前行  
    private int curRow = 0;  
    //存储行记录的容器  
   private List<String> rowList = new ArrayList<String>();
    
    //列头信息，仅在readWithHeader时使用 
    private List<String> headerRowList = new ArrayList<String>();
    
    private String sheetName;
      
    public Excel2003Reader(InputStream inputStream,Set<IDataHandler>  dataHandlers){
    	this.inputStream = inputStream;
        this.dataHandlers = dataHandlers;
        init();
    }
    private void init() {
        //转换成有序列表
        tempHandlers = new ArrayList<IDataHandler>(dataHandlers);
    }

    /** 
     * 遍历excel下所有的sheet 
     * @throws IOException 
     */  
    public void process(int sheetIndex , IRowReader rowReader){  
        try {
        	 this.fs = new POIFSFileSystem(inputStream);
        	 this.sheetIndex = sheetIndex;
        	 this.setRowReader(rowReader);
             MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);  
             formatListener = new FormatTrackingHSSFListener(listener);  
             HSSFEventFactory factory = new HSSFEventFactory();  
             HSSFRequest request = new HSSFRequest();  
             if (outputFormulaValues) {  
                 request.addListenerForAllRecords(formatListener);  
             } else {  
                 workbookBuildingListener = new SheetRecordCollectingListener(formatListener);  
                 request.addListenerForAllRecords(workbookBuildingListener);  
             }  
			factory.processWorkbookEvents(request, fs);
            buildResult();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
            //处理完成之后清除数据,释放内存
            cleanData();
        }
    }
      
    /** 
     * HSSFListener 监听方法，处理 Record 
     */  
    @SuppressWarnings("unchecked")  
    public void processRecord(Record record) {
        int thisRow = -1;
        int thisColumn = -1;  
        String thisStr = null;  
        String value = null;  
        
        switch (record.getSid()) {  
            case BoundSheetRecord.sid:  
            	if(currentSheetIndex == sheetIndex){
            		 boundSheetRecords.add(record);  
            	}
                break;  
            case BOFRecord.sid:  
            	if(currentSheetIndex == sheetIndex){
            		 BOFRecord br = (BOFRecord) record;  
                     if (br.getType() == BOFRecord.TYPE_WORKSHEET) {  
                         // 如果有需要，则建立子工作薄  
                         if (workbookBuildingListener != null && stubWorkbook == null) {  
                             stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();  
                         }  
                          
                         if (orderedBSRs == null) {  
                             orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);  
                         }  
                         setSheetName(orderedBSRs[currentSheetIndex].getSheetname()); 
                     }  
            	}else{
            		currentSheetIndex++; 
            	}
                break;  
      
            case SSTRecord.sid:  
            	if(currentSheetIndex == sheetIndex){
            		sstRecord = (SSTRecord) record;  
            	}
                break;  
      
            case BlankRecord.sid:  
            	if(currentSheetIndex == sheetIndex){
            		 BlankRecord brec = (BlankRecord) record;  
                     thisRow = brec.getRow();  
                     thisColumn = brec.getColumn();  
                     thisStr = "";  
                     rowList.add(thisColumn, thisStr);  
            	}
                
                break;  
            case BoolErrRecord.sid: //单元格为布尔类型 
            	if(currentSheetIndex == sheetIndex){
            		 BoolErrRecord berec = (BoolErrRecord) record;  
                     thisRow = berec.getRow();  
                     thisColumn = berec.getColumn();  
                     thisStr = berec.getBooleanValue()+"";  
                     rowList.add(thisColumn, thisStr.toUpperCase()); 
            	}
                break;  
      
            case FormulaRecord.sid: //单元格为公式类型 
            	if(currentSheetIndex == sheetIndex){
            		  FormulaRecord frec = (FormulaRecord) record;  
                      thisRow = frec.getRow();  
                      thisColumn = frec.getColumn();  
                      if (outputFormulaValues) {  
                          if (Double.isNaN(frec.getValue())) {  
                              // Formula result is a string  
                              // This is stored in the next record  
                              outputNextStringRecord = true;  
                              nextRow = frec.getRow();  
                              nextColumn = frec.getColumn();  
                          } else {  
                              thisStr = formatListener.formatNumberDateCell(frec);
                          }  
                      } else {  
                          thisStr = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"'; 
                      }  
                      rowList.add(thisColumn,thisStr); 
            	}
               
                break;  
            case StringRecord.sid://单元格中公式的字符串 
            	if(currentSheetIndex == sheetIndex){
            		 if (outputNextStringRecord) {  
                         // String for formula  
                         StringRecord srec = (StringRecord) record;  
                         thisStr = srec.getString();  
                         thisRow = nextRow;  
                         thisColumn = nextColumn;  
                         outputNextStringRecord = false;  
                     }  
            	}
               
                break;  
            case LabelRecord.sid:  
            	if(currentSheetIndex == sheetIndex){
            	    LabelRecord lrec = (LabelRecord) record;  
                    curRow = thisRow = lrec.getRow();  
                    thisColumn = lrec.getColumn();  
                    value = lrec.getValue().trim();  
                    value = value.equals("")?" ":value;  
                    this.rowList.add(thisColumn, value);  
            	}
            
                break;  
            case LabelSSTRecord.sid:  //单元格为字符串类型 
            	if(currentSheetIndex == sheetIndex){
            		  LabelSSTRecord lsrec = (LabelSSTRecord) record;  
                      curRow = thisRow = lsrec.getRow();  
                      thisColumn = lsrec.getColumn();  
                      if (sstRecord == null) {  
                          rowList.add(thisColumn, "");  
                      } else {  
                          value =  sstRecord  
                          .getString(lsrec.getSSTIndex()).toString().trim();  
                          value = value.equals("")?"":value;  
                          rowList.add(thisColumn,value);  
                      }  
            	}
              
                break;  
            case NumberRecord.sid:  //单元格为数字类型 
            	if(currentSheetIndex == sheetIndex){
            		 NumberRecord numrec = (NumberRecord) record;  
                     curRow = thisRow = numrec.getRow();  
                     thisColumn = numrec.getColumn();  
                     value = formatListener.formatNumberDateCell(numrec).trim();  
                     value = value.equals("")?" ":value;  
                     // 向容器加入列值  
                     rowList.add(thisColumn, value);  
            	}
                break;  
            default:  
                break;  
        }  
  
        // 遇到新行的操作  
        if (thisRow != -1 && thisRow != lastRowNumber) {  
            lastColumnNumber = -1;  
        }  
  
        // 空值的操作  
        if (record instanceof MissingCellDummyRecord) {  
            MissingCellDummyRecord mc = (MissingCellDummyRecord) record;  
            curRow = thisRow = mc.getRow();  
            thisColumn = mc.getColumn();  
            rowList.add(thisColumn,"");  
        }  
  
        // 更新行和列的值  
        if (thisRow > -1)  
            lastRowNumber = thisRow;  
        if (thisColumn > -1)  
            lastColumnNumber = thisColumn;  
  
        // 行结束时的操作  
        if (record instanceof LastCellOfRowDummyRecord) {  
            if (minColumns > 0) {  
                // 列值重新置空  
                if (lastColumnNumber == -1) {  
                    lastColumnNumber = 0;  
                }  
            }  
            lastColumnNumber = -1;  
                // 每行结束时， 调用getRows() 方法
            
            if(curRow >= 65535){
            	throw new SysError("大于65534条记录，请使用excel2007格式保存");
            }


            if (curRow < getRowReader().getHeaderRows()) {
                if (SysUtils.isNotEmpty(rowList)) {
                    for (String s : rowList) {
                        headerRowList.add(s);
                    }
                }
            }else {
                for(int i = 0 ; i < tempHandlers.size() ; i++){
                    IDataHandler dataHandler = tempHandlers.get(i);
                    
                    List<String> cpRowList = new ArrayList<String>();
                    for(String s : rowList){
                    	cpRowList.add(s);
                    }
                    
                    List<String> cpHeaderRowList = new ArrayList<String>();
                    for(String s : headerRowList){
                    	cpHeaderRowList.add(s);
                    }
                    
                    getRowReader().processRow(cpRowList, cpHeaderRowList, dataHandler);
                }
            }
            // 清空容器  
            rowList.clear();  
        }  
    }

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

    private void buildResult(){
        for(int i =0;i<tempHandlers.size();i++){
            IDataHandler dataHandler = tempHandlers.get(i);
            dataHandler.buildResult();
        }
    }

}  
