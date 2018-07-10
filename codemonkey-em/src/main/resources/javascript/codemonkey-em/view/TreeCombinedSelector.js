Ext.define('AM.view.TreeCombinedSelector', {
	
	extend: 'AM.view.CombinedSelector',
	
	alias: 'widget.treecombinedselector',
	
	/*********************
	 * 扩展属性
	 **********************/
	
	availableListType : 'treepanel',
	
	getSelectedRecords : function(){
		var treePanel = this.availableList.down(this.availableListType);
		var records = treePanel.getChecked();
		var array = [];
		
		if(records && records.length > 0){
			for(var i = 0 ; i < records.length ; i++){
				if(records[i].isLeaf()){
					array.push(records[i]);
				}
			}
		}
		
		return array;
	},
	
	getRecord : function(obj){
		var treePanel = this.availableList.down(this.availableListType);
		var store = treePanel.getStore();
		return store.getNodeById(obj[this.selectionIdField]);
	},
	
	refreshAvailableStore : function(store , records , availableListIdField , selectionIdField){
		this.callParent([store , records , availableListIdField , selectionIdField]);
		store.filter({
			filterFn: function(item) { 
				if(item.childNodes && item.childNodes.length > 0){
					for(var i = 0 ; i < item.childNodes.length ; i++){
						if(item.childNodes[i].get('visible')){
							return true;
						}
					}
					return false;
				}
				return true;
			}
	    });
	},
	
	selectAll : function(){
		var me = this;
		var root = me.availableList.down(this.availableListType).getRootNode();
		ExtUtils.checkChildNodes(root , true);
	},
	
	deselectAll : function(){
		var me = this;
		var root = me.availableList.down(this.availableListType).getRootNode();
		ExtUtils.checkChildNodes(root , false);
	}
	
});