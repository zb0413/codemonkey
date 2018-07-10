Ext.define('AM.view.CombinedPanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.combinedpanel',
	
	layout : 'border',
	
//	height : 500,
	
	formClass : null,
	
	formConfig : {},
	
	defaultFormConfig : function(){
		var me = this;
		return {
			flex : 6 ,
			winTitle : this.winTitle,
			region : 'center',
			doBack : function(){
				this.reloadList();
			},
			formBar : function(){
				var me = this;
				return [{
					action : 'create', 
					text : '新建',
					iconCls : 'btn-new',
					handler : function(){
						me.doCreate();
					}
				},'->' ,{
					text : '保存',
					iconCls : 'btn-save',
					handler : function(){
						me.doSave();
					}
				},{
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function(){
						me.doCreate();
					}
				}];
			}
		};
	},
	
	listClass : null,
	
	listConfig : {},
	
	defaultListConfig : function(){
		return {
			flex : 6 , 
			winTitle : this.winTitle,
			region : 'west',
			keepCurrentPage : true,
			split : true,
			listActionBar : function(){
				return null;
			},
			itemdblclick : function(record){
				this.formPanel.doRead(record.data);
			},
			doCreate : function(){
				if(this.xtype == "appbatchedpanel"){
					this.createNewOne();
				}else{
					this.formPanel.doRead({id : C.NEW_ENTITY_ID});
				}
			},
			
			doDelete_callback : function(data){
				if(this.formPanel){
					this.formPanel.doRead({id : C.NEW_ENTITY_ID});
				}
			}
		};
	},
	
	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(this , config);
		
		var c1 = Ext.apply(this.defaultListConfig() , this.listConfig);
		this.listObj = Ext.create(this.listClass , c1);
		
		Ext.apply(this.formConfig , {parentPanel : this.listObj});
		
		var c2 = Ext.apply(this.defaultFormConfig() , this.formConfig);
		this.formObj = Ext.create(this.formClass , c2);
		
		Ext.apply(this.listObj , {formPanel : this.formObj});
		
		this.pageLayout();
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		this.items = [this.listObj , this.formObj];
	}
	
});