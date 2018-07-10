Ext.define('AM.view.CombinedSelector', {
	
	/*********************
	 * 配置属性
	 **********************/
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.combinedselector',
	
	layout : {
        type: 'hbox',
        align: 'stretch'
    },
	
	/*********************
	 * 扩展属性
	 **********************/
	
    modelName : null,
    
    readUrl : null,
    
    readParam : null,
    
    submitUrl : null,
    
    submitParam : null,
    
	availableListType : null,
	
	listClass : null,
	
	selectedGrid : null,
	
	selectionIdField : null,
	
	availableListIdField : 'id',
	
	availableListConfig : {},
	
	showAvailableList : false,
	
	selectionToGrid : function(records){},
	
	getRecord : function(obj){},
	
	/*********************
	 * 构造方法
	 **********************/
	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(this , config);
		
		if(me.modelName){
			me.submitUrl = me.submitUrl || NS.url(me.modelName , C.BATCHED_UPDATE);
			me.readUrl = me.readUrl || NS.url(me.modelName , C.READ);
		}
		
		me.submitParam = me.submitParam || me.readParam;
		
		me.tbar = [{
			text : '选择',
			iconCls : 'btn-add',
			enableToggle : true,
			handler : function(){
				me.setSelectionDisplay(!this.pressed , me);
			}
		}];
		
		var c1 = Ext.apply(this.defaultListConfig() , this.availableListConfig);
		this.availableList = Ext.create(this.listClass , c1);
		
		this.actionPanel = Ext.create('Ext.panel.Panel' , {
			hidden : true,
			lbar : [{
				text : '>',
				handler : function(){
					var records = me.getSelectedRecords();
					me.toRight(records);
				}
			},{
				text : '<',
				handler : function(){
					var records = ExtUtils.getSelected(me.selectedGrid);
					me.toLeft(records);
				}
			},{
				text : '>>',
				handler : function(){
					me.allToRight();
				}
			},{
				text : '<<',
				handler : function(){
					me.allToLeft();
				}
			}]
		});
		
		Ext.apply(this.selectedGrid , {flex : 6 , region : 'center'});
		
		this.selectedGrid = Ext.create('Ext.grid.Panel' , this.selectedGrid);
		
		this.actionButtons = this.buildActionButtons();
		
		this.pageLayout();
		
		this.callParent();

		this.afterCreate();
		
	},
	
	/*********************
	 * 成员方法
	 **********************/
	
	getSelectedRecords : Ext.emptyFn,
	selectAll : Ext.emptyFn,
	deselectAll : Ext.emptyFn,
	
	buildActionButtons : function(){
		var me = this;
		var actions = [{
			text : '确认',
			handler : function(){
				me.doConfirm();
			}
		},{
			text : '取消',
			handler : function(){
				me.doCancel();
			}
		}];
		return actions;
	},
	
	doConfirm : function(){
		var me = this;
		
		var submitParam = this.submitParam || {};
		
		var modifiedKey = me.modelName + '_modified';
		var deletedKey = me.modelName + '_deleted';
		
		var obj = {};
		obj[modifiedKey] = ExtUtils.getModifedData(me.selectedGrid) || [];
		obj[deletedKey] = ExtUtils.getDeletedData(me.selectedGrid) || []
		
		Ext.apply(submitParam , obj);
		
		me.beforeSave(submitParam);
		
		if(this.submitUrl && submitParam){
			ExtUtils.mask(me);
			ExtUtils.doUrlAction(
					this.submitUrl , 
					submitParam , 
					function(result){
						me.up('window').close();
					},
					function(result){
						ExtUtils.unmask(me);
					}
			);
		}
	},
	
	doCancel : function(){
		this.up('window').close();
	},
	
	beforeSave : function(values){
		
	},
	
	afterCreate : function(){
		this.doRead();
	},
	
	doRead : function(p){
		var me = this;
		
		var readParams = p || this.readParams;
		
		if(this.readUrl && readParams){
			ExtUtils.mask(me);
			ExtUtils.doUrlAction(
					this.readUrl , 
					readParams , 
					function(result){
						var data = result.data;
						ExtUtils.unmask(me);
						me.selectedGrid.getStore().loadData(data || []);
						me.refreshAvailableList();
					},
					function(result){
						ExtUtils.unmask(me);
					}
			);
		}
	},
	
	setSelectionDisplay : function(b , p){
		var me = this;
		if(b){
			this.availableList.show();
			this.actionPanel.show();
			p.refreshAvailableList();
		}else{
			this.availableList.hide();
			this.actionPanel.hide(); 
		}
	},
	
	refreshAvailableStore : function(store , records , availableListIdField , selectionIdField){
		store.filter({
	    	filterFn: function(item) { 
	    		if(records){
	    			for(var i = 0 ; i < records.length ; i++){
	    				if(records[i].get(selectionIdField) == item.get(availableListIdField)){
	    					return false;
	    				}
	    			}
	    		}
	    		return true;
	    	}
	    });
	},
	
	setSortIndex : function(store){
		for(var i = 0 ; i < store.getCount() ; i++){
			store.getAt(i).set('sortIndex' , i + 1);
		}
	},
	
	defaultListConfig : function(){
		var me = this;
		return {
			flex : 6 , 
			checkable : true,
			region : 'west',
			split : true,
			listActionBar : function(){
				return null;
			}
		};
	},
	
	refreshAvailableList : function(){
		var win = this.up('window');
		ExtUtils.mask(win);
		
		var records = this.selectedGrid.getStore().getRange();
		
		var treePanel = this.availableList.down(this.availableListType);
		var store = treePanel.getStore();
		store.clearFilter();
		
		this.refreshAvailableStore(store , records , this.availableListIdField , this.selectionIdField );
		
		ExtUtils.unmask(win);
	},
	
	/*********************
	 * 页面行为及相应
	 **********************/
	pageLayout : function(){
		var actionCols = this.availableList.down('actioncolumn');
		actionCols.hide();
		this.items = [this.availableList , this.actionPanel , this.selectedGrid];
		this.buttons = this.actionButtons;
		this.setSelectionDisplay(this.showAvailableList , this); 
	},
	
	toRight : function(records){
		if(records && records.length > 0){
			for(var i = 0 ; i < records.length ; i++){
				var r = records[i];
				
				var data = this.selectionToGrid(r);
				
				ExtUtils.addLine(this.selectedGrid , data , [this.selectionIdField]);
			}
		}
		this.setSortIndex(this.selectedGrid.getStore());
		this.refreshAvailableList();
	},
	
	toLeft : function(records){
		if(records && records.length > 0){
			for(var i = 0 ; i < records.length ; i++){
				var r = records[i];
				this.selectedGrid.getStore().remove(r);
			}
		}
		this.setSortIndex(this.selectedGrid.getStore());
		this.refreshAvailableList();
	},
	
	allToRight : function(){
		var me = this;
		this.selectAll();
		var records = me.getSelectedRecords();
		me.toRight(records);
	},
	
	allToLeft : function(){
		var me = this;
		me.selectedGrid.getStore().removeAll();
		me.refreshAvailableList();
		me.deselectAll();
	},
	
	getRecordsByResult : function(array){
		var records = [];
		
		if(array && array.length > 0){
			for(var i = 0 ; i < array.length ; i++){
				var r = this.getRecord(array[i]);
				if(r){
					records.push(r);
				}
			}
		}
		
		return records;
	}
	
});