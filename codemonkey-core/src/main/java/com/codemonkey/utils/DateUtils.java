package com.codemonkey.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
	private static final Integer MONTH_12 = 12;
	// 频率定数
	public static final String FREQUENCY_YEAR = "年度";
	public static final String FREQUENCY_SEASON = "季度";
	public static final String FREQUENCY_MONTH = "月度";
	
	/**
	 * 方法描述： 取得考核年月
	 * 
	 * @return: 考核年月
	 * @author: lt
	 */
	public static Map<String , Integer> getCheck_YearMonth() {
		
		int check_year = 0;
		int check_beforemonth = 0;
		Map<String , Integer> map = new HashMap<String , Integer>();
		
		Calendar c = Calendar.getInstance();
		// 获取系统当前年份
		check_year = c.get(Calendar.YEAR);
		// 获取系统当前月份的前一月
		check_beforemonth = c.get(Calendar.MONTH);
		if(check_beforemonth == 0){
			check_beforemonth = MONTH_12 ; 
			check_year = check_year - 1;
		}
		map.put("check_year", check_year);
		map.put("check_month", check_beforemonth);
		return map;
	}
	
	
	/**
	 * 方法描述： 取得考核年
	 * 
	 * @return: 考核年
	 */
	public static int getCheck_Year() {
		
		Calendar c = Calendar.getInstance();
		// 获取系统当前年份
		int year = c.get(Calendar.YEAR);
		
		return year;
	}
	
	
	/**
	 * 方法描述： 取得考核月
	 * 
	 * @return: 考核月
	 */
	public static int getCheck_Month() {
		
		Calendar c = Calendar.getInstance();
		// 获取系统当前年份
		int month = c.get(Calendar.MONTH )+1;
		
		return month;
	}
	
	/**
	 * 方法描述： 取得季度值
	 * 
	 * @param:  月份
	 * @return: 季度
	 * @author: lt
	 */
	public static int getSeason(int month) {
		int season = 0;

		if(month <=0 || month >12 ){
			// 无效的月份
			return season;
		}
		if (month >= 1 && month <= 3) {
			season = 1;
		} else if (month >= 4 && month <= 6) {
			season = 2;
		} else if (month >= 7 && month <= 9) {
			season = 3;
		} else if (month >= 10 && month <= 12) {
			season = 4;
		}
		return season;
	}
	
	public static int[] getMonthByFreq(String frequency, int season, int month){
		
		int[] arrMonth = null;
		
		if(FREQUENCY_YEAR.equals(frequency)){
			arrMonth = new int[]{1,2,3,4,5,6,7,8,9,10,11,12}; 
		}else if(FREQUENCY_SEASON.equals(frequency)){
			if(season == 1){
				arrMonth = new int[]{1,2,3}; 
			}else if(season == 2){
				arrMonth = new int[]{4,5,6}; 
			}else if(season == 3){
				arrMonth = new int[]{7,8,9}; 
			}else if(season == 4){
				arrMonth = new int[]{10,11,12}; 
			}
		}else if(FREQUENCY_MONTH.equals(frequency)){
			arrMonth = new int[]{month};
		}
		return arrMonth;
	}
	/**
	 * 方法描述： 取得年季度的开始日期
	 * 
	 * @param:  年份
	 * @return: 开始日期
	 * @author: lt
	 */
	public static String getFDateBySeason(int year,int season){
		int s_month = 0;
		int s_day = 1;
		if(season == 1){
			s_month = 1;
		}else if(season == 2){
			s_month = 4;
		}else if(season == 3){
			s_month = 7;
		}else if(season == 4){
			s_month = 10;
		}
		return getDate(year,s_month,s_day);
	}
	
	/**
	 * 方法描述： 取得年季度的结束日期
	 * 
	 * @param:  年份
	 * @return: 结束日期
	 * @author: lt
	 */
	public static String getLDateBySeason(int year,int season){
		int e_month = 0;
		int e_day = 1;
		if(season == 1){
			e_month = 4;
		}else if(season == 2){
			e_month = 7;
		}else if(season == 3){
			e_month = 10;
		}else if(season == 4){
			year += 1;
			e_month = 1;
		}
		return getDate(year,e_month,e_day);
	}
	
	/**
	 * 方法描述： 取得年的开始日期
	 * 
	 * @param:  年份
	 * @return: 开始日期
	 * @author: lt
	 */
	public static String getFDateByYear(int year){
		int s_month = 1;
		int s_day = 1;
		return getDate(year,s_month,s_day);
	}
	
	/**
	 * 方法描述： 取得年的结束日期
	 * 
	 * @param:  年份
	 * @return: 结束日期
	 * @author: lt
	 */
	public static String getLDateByYear(int year){
		int e_month = 1;
		int e_day = 1;
		return getDate(year+1,e_month,e_day);
	}
	
	/**
	 * 方法描述： 取得年月的开始日期
	 * 
	 * @param:  年份
	 * @param:  月份
	 * @return: 开始日期
	 * @author: lt
	 */
	public static String getFDateByMonth(int year , int month) {
		int minday = 1;
		if("err".equals(monthValidate(month)) ){
			// 无效的月份
			return null;
		}
		
		return getDate(year,month,minday);
	}
	
	/**
	 * 方法描述： 取得年月的开始日期
	 * 
	 * @param:  年份
	 * @param:  月份
	 * @return: 开始日期
	 * @author: lt
	 */
	public static String getLDate(int year , int month) {
		if("err".equals(monthValidate(month)) ){
			// 无效的月份
			return null;
		}
		int maxday = getLastDay(year,month);
		
		return getDate(year,month,maxday);
	}
	
	/**
	 * 方法描述： 取得年月的开始日期
	 * 
	 * @param:  年份
	 * @param:  月份
	 * @return: 开始日期
	 * @author: lt
	 */
	public static String getLDateByMonth(int year , int month) {
		int maxday = 1 ; 
		if("err".equals(monthValidate(month)) ){
			// 无效的月份
			return null;
		}
		switch(month){
    	case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:case 9:case 10:case 11:month +=1;;break;
    	case 12:
    		year +=1;
    		month =1;
    	}
		
		return getDate(year,month,maxday);
	}
	
    /**
     * 方法描述： 月份判断
     * @param month
     * @return:  月份
     */
    public static String monthValidate(int month){
    	String str = "";
    	switch(month){
    	case 1:case 3:case 5:case 7:case 8:case 10:case 12:str = "l";break;
    	case 4:case 6:case 9:case 11:str = "s";break;
    	case 2:str = "m";break;
    	default:str = "err";
    	}
    	return str;
    }
    
    /**
     * 方法描述： 获取年月的天数
     * @param: year
     * @param: month
     * @return: 天数
     */
    public static int getLastDay(int year ,int month){
    	int days = 0;
		if("err".equals(monthValidate(month)) ){
			// 无效的月份
			return 0;
		}
    	switch(month){
    	case 1:case 3:case 5:case 7:case 8:case 10:case 12:days = 31;break;
    	case 4:case 6:case 9:case 11:days = 30;break;
    	case 2:
    		if(year%4 == 0){
    			days = 29;
    		}else{
    			days = 28;
    		}
    	}
    	return days;
    }
    
    /**
     * 方法描述： 获取年月的天数
     * @param: year
     * @param: month
     * @return: 天数
     */
    public static int getLastDays(int year ,int month){
    	Calendar c = Calendar.getInstance();
    	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM"); //如果写成年月日的形式的话，要写小d，如："yyyy/MM/dd"
    	try{
			c.setTime(simpleDate.parse(String.valueOf(year) + "/" + String.valueOf(month)));
			//c.setTime(simpleDate.parse("2012/2"));
		}catch(Exception e){
			e.printStackTrace();
		}
    	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 方法描述： 获取日期
     * @param: year
     * @param: month
     * @param: day
     * @return: 日期
     */
    public static String getDate(int year , int month , int day){
    	String dateString = "";
    	
    	String strYear = String.valueOf(year);
    	String strMonth = String.valueOf(month);
    	String strDay = String.valueOf(day);
    	// 月份规范
    	if(strMonth.length() == 1){
    		strMonth = "0" + strMonth;
    	}
    	// 日期规范
    	if(strDay.length() == 1){
    		strDay = "0" + strDay;
    	}
    	dateString = strYear + "-" + strMonth + "-" + strDay;
    	
    	return dateString ;
    }
    
    public static void main(String[] a){
    	
    	int[] aa = getMonthByFreq("年度",3,11);
    	System.out.println("" + aa.length);
    	for(int i=0;i<=aa.length-1;i++){
    		System.out.println("fsdaa :  " + aa[i]);
    	}
    }
}
