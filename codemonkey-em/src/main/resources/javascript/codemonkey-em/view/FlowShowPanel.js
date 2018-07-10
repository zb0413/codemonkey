Ext.define('AM.view.FlowShowPanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.flowshowpanel',
	
	requires : ['AM.view.RaphaelPanel'],
	
	constructor : function(config) {
		
		var c = {
			listeners : {
				'afterrender' : function(){
//					this.pageLayout();
				}
			}
		};
		
		Ext.apply(c , config);
		
		Ext.apply(this , c);
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		
	}
});