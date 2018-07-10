//栏目管理 列表
Ext.define('codemonkey-cms.view.MenuListView', {
    
	extend: 'AM.view.AppTreeGridPanel',
	
	requires: ['AM.view.AppTreeGridPanel'],
	
	modelName : 'MenuTree', 
    
    listGrid : function(){
    	var me = this;
		return {
			title : '栏目列表',
			flex : 6,
			columns : [
			           {dataIndex:"name" , xtype: 'treecolumn' , header : '栏目名称' , flex : 3 },
			           {dataIndex:"status",flex:1,header:"启用状态"},
			           {	
							header : '删除',
							xtype : 'actioncolumn',
							align : 'center',
							width : C.ACTION_COLUMN_WIDTH,
							items : [{
								iconCls : 'tbar_btn_del',
								tooltip : '删除',
								handler : function(gridView, rowIndex, colIndex , col , e , record) {
									if(!record.isLeaf()){
					        			Ext.Msg.alert('警告' , '请先删除子节点');
					        		}else{
					        			me.doDelete(record.data);
					        		}
									
								}
							}]
						}
			          ]
		};
    }
    /**覆盖父类方法**/ 
	,
	listActionBar : function(){
		var me = this;
		return [
		    {
				text : '新建',
				iconCls : 'btn-new',
				handler : function(){
					me.doCreate();
				}
		    },
			{
				text : '预览',
				iconCls : 'btn-new',
				handler : function(){
					me.doPreView();
			 	}
			}
		    ];
	},
    /**覆盖父类方法**/
    formItems : function(){ 
    	var p1 = ExtUtils.panel(
        		[ { name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : '栏目名称' , colspan : 50 }
    	]);
   		return [p1];
	}
		
});
