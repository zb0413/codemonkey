Ext.define('AM.view.AppRoleListView', {
    
	extend: 'AM.view.AppListPanel',

	/**常量**/
    
    modelName : 'AppRoleList',
    
    searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
    
    searchFormMaxHeight : C.SEARCH_FORM_HEIGHT_1,
    
	/**自定义常量**/
    
    formClass : 'AM.view.AppRoleFormView',
    
    /**实现父类方法**/
    modelFields : function(){
    	return ExtUtils.defaultModelFields;
    },
	
    listGrid : function(){
		return {
			columns : [
			   		{header: 'id', dataIndex: 'id' , hidden : true},
					{dataIndex:"name",flex:1,header : '名称'}
	        ]
		};
    },
    /**覆盖父类方法**/
    formItems : function(){ 
    	var p1 = ExtUtils.panel([
         	        { name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : '名称' , labelWidth : 80}
                 ]);
    	return [p1];
	}
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
	
});
