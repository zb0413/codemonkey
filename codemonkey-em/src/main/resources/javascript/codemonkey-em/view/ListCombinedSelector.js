Ext.define('AM.view.ListCombinedSelector', {
	
	extend: 'AM.view.CombinedSelector',
	
	alias: 'widget.listcombinedselector',
	
	/*********************
	 * 扩展属性
	 **********************/
	
	availableListType : 'grid',
	
	getSelectedRecords : function(){
		var grid = this.availableList.down(this.availableListType);
		var records = ExtUtils.getSelected(grid);
		return records;
	},
	
	getRecord : function(obj){
		var grid = this.availableList.down(this.availableListType);
		var store = grid.getStore();
		return store.getById(obj[this.selectionIdField]);
	},
	
	selectAll : function(){
		var grid = this.availableList.down(this.availableListType);
		grid.getSelectionModel().selectAll();
	},
	
	deselectAll : function(){
		var grid = this.availableList.down(this.availableListType);
		grid.getSelectionModel().deselectAll();
	}
	
});