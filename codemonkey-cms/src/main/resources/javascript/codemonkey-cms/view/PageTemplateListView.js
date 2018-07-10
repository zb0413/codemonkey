//模板列表
Ext.define('codemonkey-cms.view.PageTemplateListView', {
    
	extend: 'AM.view.AppListPanel',
	
	requires: ['AM.view.AppListPanel'],
	
	modelName : 'PageTemplateList',
	formClass : 'codemonkey-cms.view.PageTemplateFormView',
	searchFormHeight : 90,
    searchFormMaxHeight : 90,    
    
    listGrid : function(){
    	var me = this;
		return {
			title : '已创建',
			flex : 6,
			columns : [
					   {dataIndex:"name" , flex : 3 ,  header : '模板名称' },
					   {dataIndex:"type", flex:1,header:"模板种类" },
					   {dataIndex:"defaultTemplate", flex:1,header:"默认" },
					   {	
							header : '删除',
							xtype : 'actioncolumn',
							align : 'center',
							width : C.ACTION_COLUMN_WIDTH,
							items : [{
								iconCls : 'tbar_btn_del',
								tooltip : '删除',
								handler : function(gridView, rowIndex, colIndex , col , e , record) {
									me.doDelete(record.data);
								}
							}]
					   }
			]
		};
    },
    /**覆盖父类方法**/
    formItems : function(){ 
    	var p1 = ExtUtils.panel(
        		[ { name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : '模板名称' , colspan : 50 }
    	]);
   		return [p1];
	}
		
});
