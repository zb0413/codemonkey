
Ext.define('AM.view.AppUserListView', {
    
	extend: 'AM.view.AppListPanel',

	/**常量**/
    
    modelName : 'AppUserList',
    
    searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
    
    searchFormMaxHeight : C.SEARCH_FORM_HEIGHT_1,
    
	/**自定义常量**/
    
    /**实现父类方法**/
	
    listGrid : function(){
		return {
			title : '用户信息',
			columns : [
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"username",header : '登录名' , width : 200 },
		        {dataIndex:"name",header : '姓名' , width : 200 },
		        {dataIndex:"status",header : '状态' , width : 200 }
		    ],
	        selModel : null
		};
    },
    /**覆盖父类方法**/
   formItems : function(){ 
    	var p1 = ExtUtils.panel([
	        { name  : 'username_Like'   , xtype : 'textfield' , fieldLabel : '用户名' , labelWidth : 80}
        ]);
   	    var p2 = ExtUtils.panel([
   	        { name  : 'status'   , xtype : 'enumselect' , className : 'Status' ,fieldLabel : '状态', labelWidth : 80}
		]);
   	   
   		return [p1, p2];
	}
    
	
	
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
	
});
