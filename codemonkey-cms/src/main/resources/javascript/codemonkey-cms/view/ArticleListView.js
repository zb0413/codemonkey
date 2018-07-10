//内容管理
Ext.define('codemonkey-cms.view.ArticleListView', {
    
	extend: 'AM.view.AppListPanel',
	
	requires: ['AM.view.AppListPanel'],
	
	modelName : 'ArticleList',
	searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
    searchFormMaxHeight : 120, 
    
    listGrid : function(){
    	var me = this;
		return {
			title : '文章列表',
			flex : 6,
			columns : [{dataIndex:"name",flex:1,header:"文章标题",flex : 2},
			           {dataIndex:"menu_name",flex:1,header:"所属栏目"}, 
			           {dataIndex:"status",flex:1,header:"发布状态"},
			           {dataIndex:"publishedDate",flex:1,header:"发布日期"},
			           {	
							header : '删除',
							width : C.ACTION_COLUMN_WIDTH, 
							xtype : 'actioncolumn',
							align : 'center',
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
		var p1 = ExtUtils.tableLayout([ 
		           { name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : '标题' , colspan : 50 },
		           { name  : 'menu.id'   , xtype : 'entityselect' , modelName : 'menuList' ,fieldLabel : '所属栏目' , colspan : 50 },
		           { name  : 'status'   , xtype : 'enumselect' , packageName:'com.codemonkey.domain.cms', className : 'ArticleSatus', fieldLabel : '发布状态' , colspan : 50 },
		           { name  : 'category'   , xtype : 'enumselect' , packageName:'com.codemonkey.domain.cms', className : 'ArticleCategory', fieldLabel : '用途' , colspan : 50 }
		]);
			return [p1];
	}
	
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
		
});
