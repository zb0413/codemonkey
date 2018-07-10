//操作日志-列表信息
Ext.define('codemonkey-log.view.LogRequestListView', {
    
	extend: 'AM.view.AppListPanel',

	/**常量**/
    modelName : 'LogRequestList',
    
    formClass : 'codemonkey-log.view.LogRequestFormView',
    
    searchFormHeight : C.SEARCH_FORM_HEIGHT_2,
    
    searchFormMaxHeight : C.SEARCH_FORM_HEIGHT_2,
    
	/**自定义常量**/
    
    
    /**实现父类方法**/
	
    listGrid : function(){
    	var me = this;
		return {
			title : '操作日志信息',
			columns : [
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"opTime" , header : '操作时间' , flex : 1},
		        {dataIndex:"opUser_username" , header : '操作用户' , flex : 1 },
		        {dataIndex:"result" , header : '操作结果' , flex : 1 }
		       
		    ]
		};
    },
    /**覆盖父类方法**/
   formItems : function(){ 
    	var p1 = ExtUtils.panel([
	        { name  : 'opUser.id'   , xtype : 'searchingselect' , modelName : "AppUserList" ,fieldLabel : '操作用户' , labelWidth : 80},
          	{ name  : 'clsMedDesc_Like'   , xtype : 'textfield' , fieldLabel : '方法描述', labelWidth : 80}
	       
        ]);
   	    var p2 = ExtUtils.panel([
         	{ name  : 'opTime_GE'   , xtype : 'datefield' , fieldLabel : '起始时间' , labelWidth : 80},
	        { name  : 'opTime_LE'   , xtype : 'datefield' , fieldLabel : '结束时间' , labelWidth : 80}
		]);
   	   
   		return [p1, p2];
	}
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
	
});
