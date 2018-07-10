Ext.define('AM.view.GuaidePanel', {
	extend : 'Ext.panel.Panel',

	alias: 'widget.guaidepanel',
	
	layout: 'border',
	
	requires : ['AM.view.RaphaelPanel'],
	
	views : [{
		xtype : 'panel',
		title : 'panel-1',
		beforeNext : function(guaidePanel , callback){
			alert(222);
			callback();
		}
	},{
		xtype : 'panel',
		title : 'panel-2'	
	},{
		xtype : 'panel',
		title : 'panel-3'	
	}],

	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(me , config);
		
		var pageLayout = function(){
			var paper = this.paper;
			
			this.blocks = [];
			
			var blocksDefault = {
				y : 10,
				width : 100,
				height : 30,
				r : 5
			};
			
			var startX = 10;
			var steps = 150;
			
			if(me.views && me.views.length > 0 ){
				for(var i = 0 ; i < me.views.length ; i++){
					var c = {
						x : startX + steps * i,	
						text : me.views[i].title
					};
					c = Ext.apply(blocksDefault , c);
					var b = RaphaelUtils.rect(paper , c);
					
					if(i == 0){
						RaphaelUtils.attrs(b , {
							"fill" : "#08E81C"
						});
					}
					this.blocks.push(b);
					if(i > 0){
						paper.connection(this.blocks[i-1] , this.blocks[i]);
					}
				}
			}
			
		};
		
		this.items = [{
		    region: 'north',
		    xtype : 'raphaelpanel',
		    pageLayout : pageLayout,
		    height : 60
		},{
			xtype : 'panel',
			itemId : 'cardPanel',
			region: 'center', // a center region is ALWAYS required for border layout
			stateful: true,
			layout: 'card',
            items: me.views
        }];
		
		this.buttons = me.buildButtons();
		
		this.callParent();
	},
	
	buildButtons : function(){
		var me = this;
		
		var actions = [{
			text : '上一步',
			itemId : 'move-prev',
			disabled : true,
			handler : function(){
				var activedPanel = me.getActivedItem();
				if(activedPanel.beforePrev){
					activedPanel.beforePrev(me , function(){
						me.navigate('prev');
					});
				}else{
					me.navigate('prev');
				}
			}
		},'->',{
			text : '下一步',
			itemId : 'move-next',
			handler : function(){
				var activedPanel = me.getActivedItem();
				if(activedPanel.beforeNext){
					activedPanel.beforeNext(me , function(){
						me.navigate('next');
					});
				}else{
					me.navigate('next');
				}
			}
		}];
		return actions;
	},
	
	navigate : function(direction){
		var me = this;
		var cardPanel = me.down('#cardPanel');
		var activedPanel = cardPanel.getLayout().getActiveItem();
		var index = cardPanel.items.indexOf(activedPanel);
		
		var nextPanel = cardPanel.items.getAt(index + 1);
		
		if(direction == 'prev'){
			nextPanel = cardPanel.items.getAt(index - 1);
		}else{
			if(nextPanel && nextPanel.beforeEnterPage){
				nextPanel.guaidePanel = me;
				nextPanel.beforeEnterPage();
			}
		}
		
		var raphaelPanel = me.down('raphaelpanel');
		var paper = me.down('raphaelpanel').paper;
		var blocks = raphaelPanel.blocks;
		
		if(direction == 'prev'){
			RaphaelUtils.attrs(blocks[index] , {
				"fill" : "#DFE8F6"
			});
		}else if(direction == 'next'){
			RaphaelUtils.attrs(blocks[index + 1] , {
				"fill" : "#08E81C"
			});
		}
		
	    var layout = cardPanel.getLayout();
	    layout[direction]();
	    me.down('#move-prev').setDisabled(!layout.getPrev());
	    me.down('#move-next').setDisabled(!layout.getNext());
	},
	
	getActivedItem : function(){
		var me = this;
		var cardPanel = me.down('#cardPanel');
		return cardPanel.getLayout().getActiveItem();
	}
	
});