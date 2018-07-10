Ext.define('AM.view.FunctionNodeTreeView', {
    
	extend: 'AM.view.AppTreeGridPanel',
	
	modelName : 'FunctionNodeTree',
	
	checkable : true,
	
	collapsible : null,
    
    listGrid : function(){
    	var me = this;
		return {
			flex : 6,
			columns : [
		        {header: 'id' , dataIndex: 'id' , hidden : true},
		        {dataIndex:"name" , xtype: 'treecolumn' , header : '名称' , flex : 4 }
	        ]
		};
    },
    /**覆盖父类方法**/
    formItems : function(){ 
    	return null;
	},
	pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
    }
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
		
});
