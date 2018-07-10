Ext.define('AM.view.AppBatchedPanel', {

	extend : 'AM.view.AppListPanel',
	
	alias: 'widget.appbatchedpanel',
	
	cols : ExtUtils.defaultGridCols1(),
	
	submitUrl : null,
	
	constructor : function(config) {
		var me = this;
		
		me.submitUrl = me.submitUrl || NS.url(me.modelName , C.UPDATE);
		
		Ext.apply(me, config);
		
		me.callParent();
	},
	
	listActionBar : function(){
		var me = this;
		
		var actions = this.callParent();
		
		actions.push({
			action : 'delete', 
			text : '删除',
			iconCls : 'btn-delete',
			handler : function(){
				var grid = me.down('grid');
				var data = ExtUtils.getSelectedData(grid);
				if(data && data[0]){
					me.doDelete(data[0]);
				}
			}
		});
		
		return actions;
	},
	
	listGrid : function(){
		var me = this;
		
		var rowEditor = Ext.create('Ext.grid.plugin.RowEditing', {
            autoCancel: true,
            pluginId: 'roweditor',
            listeners : {
         	   'edit' : function(editor, context, eOpts){
         		   me.doSave(context.record);
         	   }
            }
        });
		
		var cols = [];
		
		if(Ext.isArray(me.cols)){
			cols = me.cols;
		}else if(Ext.isFunction(me.cols)){
			cols = me.cols();
		}
		
		var grid = {
			columns : ExtUtils.defaultGridCols5().concat(cols),
			selModel: null,
			plugins : [rowEditor],
			listeners: {
				'select' : function(selModel, record, index, eOpts){
					rowEditor.startEdit(record , 0);
			    }
//				,beforedeselect : function( selModel, record, index, eOpts ){
//			    	if(rowEditor.editing){
//			    		Ext.MessageBox.confirm('确认' , '数据尚未保存是否保存?' , function(btn){
//			    			 if(btn == 'yes'){
//			    				 rowEditor.completeEdit();
//			    			 }else{
//			    				 rowEditor.cancelEdit();
//			    			 }
//			    		});
//			    		return false;
//			    	}
//		    		return true;
//			    }
			}
		};
		return grid;
	},
	
	doSave : function(record){
		var me = this;
		var data = Ext.apply( {} , record.data);
		
		if(record.phantom){
			Ext.apply(data , {id : ""});
		}
		
		ExtUtils.submitForm({
			url : me.submitUrl,
			params : data,
			success : function(){
				me.doSave_callback();
			}
		});
	},
	
	doSave_callback : function(form, action){
		this.doSearch();
	},
	
	doCreate : function(){
		this.createNewOne();
	},
	
	createNewOne : function(){
		var me = this;
		var grid = this.down('grid');
		ExtUtils.doUrlAction(me.readUrl , {id : C.NEW_ENTITY_ID} , function(result){
			ExtUtils.addLine(grid , result.data[0]);
		});
	},
	
//	doDelete : function(){
//		var grid = this.down('grid');
//		ExtUtils.removeLine(grid);
//	},
	
	doCancel : function(){
		this.doSearch();
	}

});
