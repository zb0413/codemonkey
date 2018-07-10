Ext.define('AM.view.TreeNaviPanel', {
	extend : 'AM.view.NaviPanel',

	alias: 'widget.treenavipanel',
	
	modelName : null,
	
	modelFields : null,
	
	bodyItems : null,
	
	constructor : function(config) {
		
		this.bodyItems = [{
			xtype : 'tabpanel',
			items :　[{
				html : 'demo'
			}]
		}];
		
		Ext.apply(this , config);
		
		this.callParent();
		
		this.buildTree();
		
	},
	
	buildTree : function(){
		var me = this;
		var naviPanel = this.down('panel[region=west]');
		
		var url = NS.url(this.modelName);
		
		ExtUtils.doUrlAction( url , {node : 'root'} , function(result){
			Ext.define(me.modelName , {
			    extend: 'Ext.data.Model',
			    fields: me.modelFields || ['id' , 'name' , 'text']
			});
			
			 var store = ExtUtils.treeStore({
				model : me.modelName,
				root : {
					children : result.data
				}
		    });
			
		    // create the Tree
		    var tree = ExtUtils.tree({
		    	 store: store,
		    	 listeners: {
		        	 itemclick: function(view, node) {
		                 if(node.isLeaf()) {
		                     me.treeNodeOnClick(node);
		                 } else if(node.isExpanded()) {
		                     node.collapse();
		                 } else {
		                     node.expand();
		                 }
		             }
		        }
		    });
		    
		    naviPanel.add(tree);
		} , function(){
			
		});
	},
	
	treeNodeOnClick : function(treeNode , treeView){
		var me = this;
		var listView = null;
		if(me.bodyItems && me.bodyItems.length > 0 ){
			listView = me.bodyItems[0];
			if(listView.search){
				listView.search();
			}
		}
	}
	
});