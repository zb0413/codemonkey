Ext.define('AM.view.DefaultViewport', {
	
	extend: 'Ext.container.Viewport',
	
	layout : 'border',
	
	buildFunctionNodes : function(){},
	
	buildDefaultMainView : function(){},
	
	buildTopPanel : function(){},
	
	buildMainTools : function(){},
    
	constructor : function(config) {
        var me = this;
        
        Ext.apply(me , config);

        var naviPanel = this.buildNaviPanel();
        
        var tabPanel = {
    		xtype : 'tabpanel',
    		plain : true,
    		tools : me.buildMainTools(),
    		items : [me.buildDefaultMainView()]
        };
        
        var topPanel = this.buildTopPanel();
        
        me.pageLayout(naviPanel , tabPanel , topPanel);
        
        me.callParent(arguments);
    },
    
    pageLayout : function(naviPanel , tabPanel , topPanel){
    	Ext.apply(naviPanel , {
    		title : '导航',
 	    	region: 'west',
 	    	width :200,
 	    	split: true,
 		    minWidth: 175,
 		    maxWidth: 400,
 		    collapsible: true,
 		    animCollapse: true
    	});
    	
    	Ext.apply(tabPanel , {
    		region : 'center'
    	});
    	
    	this.items = [naviPanel , tabPanel];
    	
    	if(topPanel){
    		Ext.apply(topPanel , {
        		region : 'north'
        	});
    		this.items.push(topPanel);
    	}
    },
    
    buildNaviPanel : function(){
    	
    	var me = this;
    	
    	Ext.define('FunctionNode' , {
			 extend: 'Ext.data.Model',
			 fields : ['id' , 'code' , 'name' , 'iconClass' , 'viewClass' , 'viewConfig']
		});
    	
    	var roots = me.buildFunctionNodes();
    	
    	if(!roots){
    		return null;
    	}
    	
    	var navi = {
			xtype : 'panel',
			title : '导航',
			layout: 'accordion'
    	};
    	
    	var items = [];
    	
    	for(var i = 0 ; i < roots.length ; i++){
    		var treeStore = this.buildTreeStore(roots[i]);
        	
	       	var treePanel = {
	   	        xtype: 'treepanel',
	   	        title : roots[i].name,
	   	        store: treeStore,
	   	        rootVisible : false,
	   	        listeners : {
	   	        	itemclick : function( panel , record, item, index, e, eOpts ){
	   	        		me.functionNodeOnclick(record.data);
	   	        	}
	   	        }
	       	};
	       	
	       	items.push(treePanel);
    	}
    	
    	Ext.apply(navi , {items : items});
    	
    	return navi;
    	
    },
    
    buildTreeStore : function(root){
    	var me = this;
    	
    	var store = Ext.create('Ext.data.TreeStore', {
    		model : 'FunctionNode',
    		root : {
	            expanded: true,
	            children: root.children
	        }
    	});
    	
    	return store;
    },
    
    functionNodeOnclick : function(item , override){
    	var me = this;
    	if(item.viewClass){
    		
    		var tab = me.down('panel[functionNodeId=' + item.id + ']');
    		
    		var config = {
				functionNodeId : item.id,
				winTitle : item.name
    		};
    		
    		var viewConfig = {};
    		if(item.viewConfig){
    			viewConfig = Ext.decode(item.viewConfig);
        		Ext.apply(config , viewConfig);
    		}
    		
    		if(tab){
    			me.down('tabpanel').setActiveTab(tab);
    			return;
    		}
    		
			var view = Ext.create(item.viewClass , config);
			
			Ext.apply(view , override , {
				title : view.winTitle,
				closable : true
			});
			
    		me.down('tabpanel').add(view);
    		me.down('tabpanel').setActiveTab(view);
    		
    	}
    }
	
});