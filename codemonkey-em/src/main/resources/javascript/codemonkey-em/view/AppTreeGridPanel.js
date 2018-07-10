Ext.define('AM.view.AppTreeGridPanel' , {
    
	extend: 'AM.view.AppListPanel',
	
	alias: 'widget.apptreegridpanel',
	
	checkable : false,
	
	constructor : function(config) {
		
		Ext.apply(this , config);
		
		this.callParent();
		
	},
	
	buildListGrid : function(config){
		var me = this;
		
		var fields = ExtUtils.fieldsFromCols(config.columns);
		
		ExtUtils.model({
			modelName : me.modelName,
			modelFields : fields ,
			baseClass : 'Ext.data.TreeModel'
		});
		
		var store = ExtUtils.treeStore({
			model : me.modelName,
			autoLoad : false,
			root : {}
	    });
		
	    // create the Tree
		 var tree = {
			xtype : 'treepanel',
	        useArrows: true,
	        rootVisible: false,
	        store: store,
	        layout: {
	            type: 'fit'
	        },
	        
//	        viewConfig: {
//                plugins: {
//                    ptype: 'treeviewdragdrop',
//                    containerScroll: true
//                }
//            },
            
	        listeners : {
            	itemdblclick : function( panel , record, item, index, e, eOpts ){
            		me.itemdblclick(record);
            	}
	        	,
    			
    			checkchange : function( node, checked, eOpts ) {
            		node.expand(true , function(){
            			ExtUtils.checkChildNodes(node , checked);
            		});
                }
            }
	    };
	    
	    Ext.apply(tree , config);
	    
	    return tree;
	},
	
	doSearch : function(p , callback){
		var me = this;
		var treePanel = this.down('treepanel');
		var form = this.down('form');
		var values = {};
		
		if(form){
			Ext.apply(values , form.getForm().getValues());
		}
		
		if(p){
			Ext.apply(values , p);
		}
		
		var searchParams = ExtUtils.prepareConfig(me.searchParams);
		
		if(searchParams){
			Ext.apply(values , searchParams);
    	}
		
		var params = ExtUtils.buildQueryInfo(values);
		
		Ext.apply(params , {pageable : this.pageable}); 
		
		Ext.apply(params , {checkable : this.checkable}); 
		
		if(treePanel){
			ExtUtils.doUrlAction(me.readUrl , params , function(result){
				
				treePanel.getStore().setRootNode({expanded: true, children : result.data});
				
				me.doSearch_callback(result.data);
				
				if(callback){
					callback(result.data);
				}
				
			} , function(){
				
			});
		}
	},
	doReset : function(){
		var me = this;
		var treePanel = this.down('treepanel');
		var form = this.down('form');
		
		if(form){
			form.getForm().reset();
		}
		
		var values =  {};
		
		var searchParams = ExtUtils.prepareConfig(me.searchParams);
		
		if(searchParams){
			Ext.apply(values , searchParams);
    	}
		
		var params = ExtUtils.buildQueryInfo(values);
		
		Ext.apply(params , {pageable : this.pageable}); 
		
		Ext.apply(params , {checkable : this.checkable});
		
		if(treePanel){
			ExtUtils.doUrlAction(me.readUrl , params , function(result){
				
				treePanel.getStore().setRootNode({expanded: true, children : result.data});
				
				me.doSearch_callback(result.data);
				
			} , function(){
				
			});
		}
	}
	
});
