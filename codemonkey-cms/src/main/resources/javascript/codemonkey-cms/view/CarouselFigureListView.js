//轮播图管理
Ext.define('codemonkey-cms.view.CarouselFigureListView', {
    
	extend: 'AM.view.AppListPanel',
	
	requires: ['AM.view.AppListPanel'],
	
	modelName : 'CarouselFigureList',
	formClass : 'codemonkey-cms.view.CarouselFigureFormView',
	searchFormHeight : 90,
    searchFormMaxHeight : 90,    
    
    listGrid : function(){
    	var me = this;
		return {
			title : '已上传轮播图',
			flex : 6,
			columns : [{dataIndex:"picture_name",flex:1,header:"图片名称"},
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
	    		[ { name  : 'picture.name_Like'   , xtype : 'textfield' , fieldLabel : '图片名称' , colspan : 50 }
		]);
			return [p1];
	}
		
});
