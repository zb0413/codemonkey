Ext.define('AM.view.DrawPanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.drawpanel',
	
	drawItems : function(){
		return [{
	        type: 'rect',
	        fill: '#79BB3F',
	        width: 20,
	        height: 20,
	        group: 'rectangles',
	        x: 100,
	        y: 100
	    },{
	    	type: 'rect',
	        fill: '#79BB3F',
	        width: 20,
	        height: 20,
	        group: 'rectangles',
	        x: 200,
	        y: 100
	    },{
	    	type: 'path',
	    	path: ['M0,109.718c0-43.13,24.815-80.463,60.955-98.499L82.914,0C68.122,7.85,58.046,23.406,58.046,41.316'].join(''),
            fill: '#C5D83E'
	    }];
	},
	
	constructor : function(config) {
		
		Ext.apply(this , config);
		
		this.pageLayout();
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		
		this.drawComponent = Ext.create('Ext.draw.Component', {
		    viewBox: false,
		    items: this.drawItems()
		});
		
		this.items = [this.drawComponent];
	}
	
});