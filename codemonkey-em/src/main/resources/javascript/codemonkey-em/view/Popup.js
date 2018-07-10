Ext.define('AM.view.Popup', {

	extend : 'AM.view.AppListPanel',
	
	alias: 'widget.popup',
	
	mode : 'SINGLE',
	
	selectedRecords : [],
	
	itemdblclick : function(records){
	
	},
	
	overrideListGrid : function(){
	},
	
	constructor : function(config) {
		var me = this;

		me.selectedRecords = [];
		
		Ext.apply(me, config);
		
		me.buttons = [{
			text : i18n.ok,
			iconCls : 'btn-confirm',
			handler : function() {
				me.doConfirm();
				this.up('window').close();
			}
		}, {
			text : i18n.cancel,
			iconCls : 'btn-close',
			handler : function() {
				this.up('window').close();
			}
		}];
		
		me.callParent();
	},
	
	//override
	listStore : function(store){
		var me = this;
		Ext.apply(store , {
			listeners: {
				load : function ( thisStore , records, successful, eOpts ){
					var grid = me.down('grid');
					if(me.selectedRecords){
						grid.getSelectionModel().select(me.selectedRecords);
					}
				}
			}
		});
	},
	
	defaultListGrid : function(){
		var me = this;
		var columns =  ExtUtils.defaultGridCols1();
		if(me.columns){
			columns = ExtUtils.defaultGridCols5().concat(me.columns);
			
		}
		
		//弹出列表不显示action column
		for(var i = 0 ; i < columns.length ; i++){
			if(columns[i].xtype == 'actioncolumn'){
				columns[i].hidden = true;
			}
		}
		
		var grid = {
			mode : me.mode,	
			columns : columns,
			listeners: {
				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
					me.updateSelectedRecord(record , true);
					me.itemdblclick(record);
					this.up('window').close();
			    },
			    
//			    'itemclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
//					var selModel = this.getSelectionModel();
//					if(selModel.isSelected(record)){
//						selModel.deselect(record , false);
//					}else{
//						selModel.select(record , true);
//					}
//				},
			    
			    'select' : function(thisGrid, record, index, eOpts){
			    	me.updateSelectedRecord(record , true);
			    	
			    },
			    
			    'deselect' : function(thisGrid, record, index, eOpts){
			    	me.updateSelectedRecord(record , false);
			    	
			    }
			}
		};
		return grid;
	},
	
	listGrid : function(){
	
		var c = this.defaultListGrid();
		
		Ext.apply(c , this.overrideListGrid());
		
		return c;
		
	},
	
	pageLayout : function(searchForm , listBar , grid){
		this.items = [searchForm , grid];
	},
	
	updateSelectedRecord : function(r , selFlag){
		if(selFlag){
			this.selectedRecords.push(r);
		}else{
			this.selectedRecords = Ext.Array.difference(this.selectedRecords , [r]);
		}
	},
	
	doConfirm : function(){
		var me = this;
		var records = me.selectedRecords;
		if (records) {
			for(var i = 0 ; i < records.length ; i++){
				me.itemdblclick(records[i]);
			}
		}
	}

});
