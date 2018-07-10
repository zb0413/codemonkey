//系统维护-码表维护  列表
Ext.define('AM.view.BaseDictionaryListView', {
    
	extend: 'AM.view.AppListPanel',

    modelName : 'BaseDictionaryList',
    
    formClass : 'AM.view.BaseDictionaryFormView',
    
    searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
    
    searchFormMaxHeight : C.SEARCH_FORM_HEIGHT_1,
    
    /**实现父类方法**/
   
	listGrid : function(){
    	var me = this;
		return {
			title : '码表',
			columns : [
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"fieldType" , header : '分组名称' , flex : 3 },
		        {dataIndex:"name" , header : '显示名称' , flex : 3 },
		        {dataIndex:"codeValue" , header : '码值' , flex : 3 },
		        {dataIndex:"sortIndex" , header : '排序号（同组内）' , flex : 3 }
		    ]
		};
    },
    /**覆盖父类方法**/
    formItems : function(){ 
    	var p1 = ExtUtils.panel([{ name  : 'fieldType_Like'  , xtype : 'textfield' , fieldLabel : '分组名称'}]);
    	var p2 = ExtUtils.panel([{ name  : 'name_Like' , xtype : 'textfield' , fieldLabel : '显示名称'}]);
   		return [p1, p2];
	},
	listActionBar : function(){
		var me = this;
		return [{
			text : '新建',
			iconCls : 'btn-new',
			handler : function(){
				me.doCreate();
			}
		}];
	}
    
	
});
