Ext.define('AM.view.DataExportEntryListView', {
    
	extend: 'AM.view.AppListPanel',

	/**常量**/
    modelName : 'DataExportEntryList',
    
    winTitle : '导入导出',
    
    pageable : false,
	
    listGrid : function(){
    	var me = this;
		return {
			title : '表名称',
			columns : [
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"tableName" , header : '表名' , flex : 1 },
		        {	
					header : '导入Excel',
					xtype : 'actioncolumn',
					align : 'center',
					flex : 1, 
					items : [{
						iconCls : 'tbar_btn_add',
						tooltip : 'xml',
						handler : function(gridView, rowIndex, colIndex , col , e , record) {
							me.doImport(record.get('tableName') , 'excel');
						}
					}]
				},
		        {	
					header : '导出Excel',
					xtype : 'actioncolumn',
					align : 'center',
					flex : 1, 
					items : [{
						iconCls : 'tbar_btn_add',
						tooltip : 'excel',
						handler : function(gridView, rowIndex, colIndex , col , e , record) {
							me.doExport(record.get('tableName') , 'excel');
						}
					}]
				}
		    ]
		};
    },
    
	listActionBar : function(){
		return null;
	},
	
	buildSearchForm : function(items){
		var me = this;
		return ExtUtils.hboxLayout(items, {
			xtype : 'form',
			padding : 0,
    		bodyPadding : 0,
    		border : false,
			resizable : true,
			resizeHandles : 's',
			height : 0,
			buttons : me.searchFormBar()
		});
	},
	
    /**自定义页面行为**/
	doExport : function(tableName , fileType){
		var action = 'exportExcel';
		if(fileType && fileType == 'xml'){
			action = 'exportXml';
		}
		var url = NS.url(this.modelName , action);
		ExtUtils.openUrl(url,{tableName : tableName});
	},
	
	doImport : function(tableName , fileType){
		var action = 'importExcel';
		var url = NS.url(this.modelName , action);
		
		ExtUtils.doUrlAction(url , {tableName : tableName} , 
		function (resp){
			Ext.Msg.alert("系统提示" ,  '导入成功！');
			});
	}
    /**自定义页面行为响应**/
	
});
