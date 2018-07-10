Ext.define('AM.view.NaviPanel', {
	extend : 'Ext.panel.Panel',

	alias: 'widget.navipanel',
	
	layout: 'border',

	width : 800,
	height :600,
	
	bodyItems : [],
	
	naviItems : [],
	
	constructor : function() {
		
		this.items = [{
		    region: 'west',
		    iconCls : 'icon-navigation',
		    split: true,
		    width: 200,
		    collapsible: false,
		    animCollapse: true,
		    margins: '0 0 0 5',
		    layout : 'fit',
    		items: this.naviItems
		},{
			xtype : 'panel',
			region: 'center', // a center region is ALWAYS required for border layout
			stateful: true,
			layout : 'fit',
            items: this.bodyItems
        }];
		
		this.callParent();
	}
	
});