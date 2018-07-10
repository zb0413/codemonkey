Ext.define('AM.view.AppRoleFormView', {
    extend: 'AM.view.AppEditPanel',
    
    /**常量**/
    modelName : 'AppRole',
	/**自定义常量**/
    
    /**实现父类方法**/
    modelFields : function(){
    	return ['functionNodes' , 'checkedFunctionNodes'].concat(ExtUtils.defaultModelFields);
    },
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
    	var p2 = ExtUtils.tableLayout([
    		         { xtype:"textfield"  , name:"name" , labelWidth : 120 ,fieldLabel:'角色名称', allowBlank : false , labelWidth : 80 , colspan : 50}
    	         ],{
    	            height : 45
    	         }
    	);
    	
    	ExtUtils.model({
    		modelName : 'AppRoleFunctionNode',
    		modelFields : ['text'].concat(ExtUtils.defaultModelFields)
    	});
    	
    	var store1 = ExtUtils.treeStore({
			model : 'AppRoleFunctionNode',
			autoLoad : false,
			root : {}
	    });
    	
    	var p3 = {
    			xtype : 'treepanel',
    			title:'功能设置',
    			flex: 1,
    			store : store1,
    	        useArrows: true,
    	        rootVisible: false,
    	        renderTo: Ext.getBody(),
    	        listeners : {
                	checkchange:  {
                    	fn: function( node, checked, eOpts ){ 
                    		node.expand(true , function(){
                    			me.checkChildNodes(node , checked);
                    		});
                    	}
                    }
                }
    	    };
    	
	   	return [p2 , p1 , p3];
    },
    
    /**覆盖父类方法**/
    beforeSave : function(values){
    	var me = this;
    	var treePanel = me.down('treepanel');
    	var functionNodes = ExtUtils.checkedTreeJson(treePanel);
    	Ext.apply(values , {
    		functionNodes : functionNodes || []
    	});
    },
    
    afterModelLoad : function(model){
		this.callParent([model]);
		
		var me = this;
		var treePanel = this.down('treepanel');
		var treeStore = treePanel.getStore();
		var allNodes = model.functionNodes.data;
		//加载所有功能点
		treeStore.setRootNode({expanded: true, children : allNodes});
//		if(allNodes){
//			for(var i = 0 ; i < allNodes.length ; i++){
//				var node = treeStore.getNodeById(allNodes[i].id);
//				node.set('checked', false);
//			}
//		}
		var checkedNodes = model.allFunctionNodes;
		if(checkedNodes){
			for(var i = 0 ; i < checkedNodes.length ; i++){
				var node = treeStore.findNode( 'id' , checkedNodes[i].id);
//				var node = treeStore.getNodeById(checkedNodes[i].id);
				node.set('checked', false);
			}
		}
		//设置选中的功能点
		var checkedNodes = model.checkedFunctionNodes;
		if(checkedNodes){
			for(var i = 0 ; i < checkedNodes.length ; i++){
				var node = treeStore.findNode( 'id' , checkedNodes[i].id);
//				var node = treeStore.getNodeById(checkedNodes[i].id);
				node.set('checked', true);
			}
		}
		
    },
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
  	 checkChildNodes : function(node , checked){
    	var do_flg = true;
    	node.set('checked', checked);
    	if(node.parentNode){
    		if(checked === true){
    			this.setParentNode(node.parentNode , checked);
    		}else{
    			if(node.parentNode.childNodes.length == 1){
    				this.setParentNode(node.parentNode , checked);
    			}else{
    				for(var i = 0 ;  i < node.parentNode.childNodes.length ; i++){
    	    			if(node.parentNode.childNodes[i].data.checked === true && node.id != node.parentNode.childNodes[i].id){
    	    				do_flg = false;
    	    				break;
    	    			}
    	    		}
    				if(do_flg){
    					this.setParentNode(node.parentNode , checked);
    				}
    			}
    		}
    	}
    	if(node.childNodes){
    		for(var i = 0 ;  i < node.childNodes.length ; i++){
    			this.checkChildNodes(node.childNodes[i] , checked);
    		}
    	}
    },
    
    setParentNode : function(node ,checked) {
    	node.set('checked', checked);
    	if(node.parentNode){
    		if(checked === true){
    			node.parentNode.set('checked', checked);
    		}else{
    			if(node.parentNode.childNodes.length == 1){
    				this.setParentNode(node.parentNode , checked);
    			}
    		}
    	}
    }
});