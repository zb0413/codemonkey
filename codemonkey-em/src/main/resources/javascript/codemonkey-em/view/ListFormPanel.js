Ext.define('AM.view.ListFormPanel', {
	
	extend: 'Ext.panel.Panel',
	
	mixins : ['AM.view.Requirements'],

	alias: 'widget.listformpanel',
	
	layout : 'border',
	
	formClass : null,
	
	formConfig : {flex : 9 , region : 'center'},
	
	listClass : null,
	
	listConfig : {
		flex : 3 , 
		region : 'west',
		split : true
	},
	
	constructor : function(config) {
		
		Ext.apply(this , config);
		
		this.listObj = Ext.create(this.listClass , this.listConfig);
		this.formObj = Ext.create(this.formClass , this.formConfig);
		
		this.pageLayout();
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		this.items = [this.listObj , this.formObj];
	}
	
});