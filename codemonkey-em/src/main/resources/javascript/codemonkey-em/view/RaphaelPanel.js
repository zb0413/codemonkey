Ext.define('AM.view.RaphaelPanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.raphaelpanel',
	
	constructor : function(config) {
		
		var c = {
			listeners : {
				'afterrender' : function(){
					this.paper = Raphael(this.getEl().el.id, 1000, 600);
					this.pageLayout();
				}
			}
		};
		
		Ext.apply(c , config);
		
		Ext.apply(this , c);
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		var paper = this.paper;
		
		var block1 = RaphaelUtils.rect(paper , {
			x : 10,
			y : 10,
			text : 'text',
			width : 100,
			height : 100,
			r : 5
		});
		// Sets the fill attribute of the circle to red (#f00)
		RaphaelUtils.attrs(block1 , {
			"fill" : "#f00",
			"stroke" : "#fff"
		});

		var block2 = RaphaelUtils.rect(paper , {
			x : 500,
			y : 70,
			text : 'text',
			width : 100,
			height : 100,
			r : 5
		});
		// Sets the fill attribute of the circle to red (#f00)
		RaphaelUtils.attrs(block2 , {
			"fill" : "#f90090",
			"stroke" : "#fff"
		});
		
		var line = paper.connection(block1 , block2);
		
		var block3 = RaphaelUtils.circle(paper , {
			x : 400,
			y : 320,
			text : 'text',
			r : 50
		});
		RaphaelUtils.attrs(block3 , {
			"fill" : "#f90090",
			"stroke" : "#fff"
		});
	}
	
});