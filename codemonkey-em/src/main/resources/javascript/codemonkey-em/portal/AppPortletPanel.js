Ext.define('AM.portal.AppPortletPanel' , {
    
	extend: 'AM.view.AppListPanel',
	
	alias: 'widget.apppportletpanel',
	
	colIndex : null,

	sortIndex : null,
	
	doUserConfig : function(config){},
	
	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(me, config);
		
		this.callParent(arguments);
	},
	
	pageLayout : function(searchForm , listBar , grid){
		this.tbar = [searchForm];
		this.items = [grid];
	},
	
	buildSearchForm : function(items){
		var me = this;
		if(items || items.length > 0){
			
			var buttonGroup = {
					xtype : 'buttongroup',
					frame : false,
					padding : 0,
		    		bodyPadding : 0,
					items : me.searchFormBar()
			};
			
			return  ExtUtils.hboxLayout(items.concat(buttonGroup), {
				xtype : 'form',
				padding : 0,
	    		bodyPadding : 0
			});
		}
	},
	
	afterCreate : function(){
		this.callParent();
		var userConfig = this.userConfig ? Ext.decode(this.userConfig) : null;
		if(userConfig){
			this.doUserConfig(userConfig);
		}
	}
});
