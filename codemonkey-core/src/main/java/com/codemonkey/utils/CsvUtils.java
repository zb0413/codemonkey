package com.codemonkey.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvUtils {

	/** 
	* 读取CSV文件 
	*/  
	public static List<String[]> readeCsv(String path){
		return readeCsv(path , SysUtils.UTF8 , true);
	}
	
	public static List<String[]> readeCsv(String path , String encodeing){
		return readeCsv(path , encodeing , true);
	}
	
	public static List<String[]> readeCsv(String path ,String encodeing , boolean skipHeader){  
	    try {      
	        List<String[]> csvList = new ArrayList<String[]>(); //用来保存数据  
	        CsvReader reader = new CsvReader(path,',',Charset.forName(encodeing));    //一般用这编码读就可以了      
	        if(skipHeader){
	        	reader.readHeaders(); // 跳过表头
	        }  
	           
	        while(reader.readRecord()){ //逐行读入除表头的数据      
	            csvList.add(reader.getValues());  
	        }
	        reader.close(); 
	        
			return csvList;
	    }catch(Exception ex){  
	        System.out.println(ex);  
	    }
	    return null;
	}  
	  
	/** 
	 * 写入CSV文件 
	 */  
	public static void writeCsv(String path , List<String[]> contents , String encodeing){
		
		if(SysUtils.isEmpty(contents)){
			return;
		}
		
	    try {  
	         CsvWriter wr = new CsvWriter(path,',',Charset.forName(encodeing));  
	         for(String[] c : contents){
	        	 wr.writeRecord(c);
	         }
	         wr.close();
	     } catch (IOException e) {  
	        e.printStackTrace();  
	     }  
	} 
	
}
