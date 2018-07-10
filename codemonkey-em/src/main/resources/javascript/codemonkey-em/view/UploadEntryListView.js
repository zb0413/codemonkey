
Ext.define('AM.view.UploadEntryListView', {
    
	extend: 'AM.view.AppListPanel',

	/**常量**/
    
    modelName : 'UploadEntryList',
    
	/**自定义常量**/
    
    /**实现父类方法**/
	
    listGrid : function(){
		return {
			title : '用户信息',
			columns : [
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"fileName",header : '文件名' , width : 200 },
		        {dataIndex:"filePath",header : '路径' , width : 200 },
		        {dataIndex:"fileSize",header : '大小' , width : 200 }
		    ],
	        selModel : null
		};
    },
    /**覆盖父类方法**/
	formItems : function(){ 
    	var p1 = ExtUtils.panel([
	        { name  : 'fileName_Like'   , xtype : 'textfield' , fieldLabel : '用户名' , labelWidth : 80}
        ]);
   		return [p1];
	}
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
	
});
