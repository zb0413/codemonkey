
Ext.define('AM.utils.ExtDateUtils', {
	
	/*----------------------------------
	 *          Ext日期、时间Utils
	 -------------------------------------*/
	  /**
     * 获取当前年份
     * @returns str
     */
    currentYear : function(){
    	var currY =((new Date()).getYear()<1900?(1900+(new Date()).getYear()):(new Date()).getYear());
		return  currY;
	},
    currentMonth : function(){
    	var month = new Date().getMonth() + 1;
		return  month;
	},
    currentDate : function(){
    	var date = new Date().getDate();
		return  date;
	},
	currentSeason : function(){
		return this.season(this.currentMonth());
	},
	
	season : function(month){
		if( month >= 1 && month <=3){
			return 1;
		}else if(month > 3 && month <= 6){
			return 2;
		}else if(month > 6 && month <= 9){
			return 3;
		}else if(month > 9 && month <= 12){
			return 4;
		}
	},
	/**
     * 获取当前时间的前一个月的年季月，
     * 例如2014年1季度1月，前一个月应为2013年4季度12月;
     *     2014年2季度4月，前一个月为2014年1季度3月;
     *     2014年3季度8月，前一个月为2013年3季度7月
     * @returns str
     */
	getLastYearSeasonMonth:function(){
		var month = this.currentMonth();
		var beforeYear = this.currentYear();
        var beforeSeason = 0;
	    var beforeMonth = 0;
	    //例如2014年1季度1月，前一个月应为2013年4季度12月
	    if (month == 1){
			 beforeMonth = 12;
			 beforeSeason = 4;
			 beforeYear = this.currentYear() - 1;
	    }else {
	    	  beforeMonth = month -1;
			  beforeSeason = this.season(beforeMonth);
	    }
	    var result = {
			  year : beforeYear,
			  month : beforeMonth,
			  season : beforeSeason 
		};
	    return result;
	}
});
