Ext.define('AM.view.AppDynamicListPanel' , {
	
	extend: 'AM.view.AppListPanel',
	
	alias: 'widget.appdynamiclistpanel',
	
	pageable : false,
	
	afterReconfigGrid : function(){},
	
	doSearch : function(p , callback , keepCurrentPageFlg){
		var me = this;
		var params = me.buildSearchParams(p);
		
		var grid = this.down('grid');
		
		ExtUtils.mask(me , "数据提取中。。。");
    	
    	ExtUtils.doUrlAction(this.readUrl , {queryInfo : params , pageable : this.pageable}, function(result){
    			var data = result.data;
    			var columns = me.rebuildColumns(result.columns);
    			
    			var store = ExtUtils.buildStoreByColumns( me.modelName , columns , {data : data});
    	 		
    			grid.reconfigure(store, columns);
    			var store = grid.getStore();
    			store.loadData(data);
    			
    			me.afterReconfigGrid();
    			
    			ExtUtils.unmask(me);
    		},function(){
    			ExtUtils.unmask(me);
    		}
    	);
	},
	
	rebuildColumns : function(columns){
		return columns;
	}
});
