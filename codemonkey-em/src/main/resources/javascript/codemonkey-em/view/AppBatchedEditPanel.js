Ext.define('AM.view.AppBatchedEditPanel', {

	extend : 'AM.view.AppListPanel',
	
	alias: 'widget.appbatchededitpanel',
	
	cols : [
       	{header: 'id', dataIndex: 'id' , hidden : true},
       	{dataIndex:"code",flex:1,header : i18n.code , editor : {xtype : 'textfield'}},
		{dataIndex:"name",flex:1,header : i18n.name1 , editor : {xtype : 'textfield'}}
   	],
   	
   	editorFormItems : function(){
   		return [
	        {xtype: 'textfield', name : 'id' , hidden : true},
	        {xtype: 'textfield', name : 'code' , fieldLabel : 'code'},
	        {xtype: 'textfield', name : 'name' , fieldLabel : 'name'}
        ];
   	},
	
	submitUrl : null,
	
	constructor : function(config) {
		var me = this;
		
		Ext.apply(me, config);
		
		if(!me.submitUrl){
			me.submitUrl = NS.url(me.modelName , C.UPDATE);
		}
		me.callParent();
	},
	
	//override
	pageLayout : function(searchForm , listBar , grid){
		
		this.layout = 'border';
		
		this.items = [{
			xtype : 'container',
			region : 'west',
			split : true,
			layout : {
				type : 'vbox',
				align : 'stretch',
				pack  : 'start'
			},
			flex : 6,
			items : [searchForm , grid]
		},{
			xtype : 'form',
			region : 'center',
			itemId : 'editorFormId',
			flex : 6,
			items : this.editorFormItems(),
			buttons : this.formButtons()
		}];
	},
	
	formButtons : function(){
		var me = this;
		return [{
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				me.doCreate();
			}
		},{
			text : '保存并新建',
			iconCls : 'btn-save-new',
			handler : function(){
				me.doCreate();
			}
		},{
			text : '保存并复制当前内容',
			iconCls : 'btn-save-copy',
			handler : function(){
				me.doCreate();
			}
		}];
	},
	
	listGrid : function(){
		var me = this;
		
		var grid = {
			columns : me.cols,
			selModel: null,
			listeners: {
				'select' : function(grid, record, index, eOpts){
			    }
			}
		};
		return grid;
	},
	
	doSave : function(record){
		var me = this;
		ExtUtils.submitForm({
			url : me.submitUrl,
			params : record.data,
			success : function(){
				me.doSave_callback();
			}
		});
	},
	
	doSave_callback : function(form, action){
		this.doSearch();
	},
	
	doCreate : function(){
		var grid = this.down('grid');
		ExtUtils.addLine(grid);
	},
	
	doDelete : function(){
		var grid = this.down('grid');
		ExtUtils.removeLine(grid);
	},
	
	doCancel : function(){
		this.doSearch();
	}

});
