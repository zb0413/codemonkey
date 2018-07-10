Ext.define('AM.view.MyDashboard', {
   
	extend: 'AM.portal.PortalApp',
	
	alias: 'widget.mydashboard',
	
	title : '我的工作台',
	
	modelName : 'MyDashboard',
	
	constructor : function(config) {
		var me = this;
		
		me.readUrl = NS.url(me.modelName , C.READ);
		
		me.updateUrl = NS.url(me.modelName , C.UPDATE); 
		
		me.deleteUrl = NS.url(me.modelName , C.DESTROY); 
		
		me.batchedUpdateUrl = NS.url(me.modelName , C.BATCHED_UPDATE); 
		
		Ext.apply(me, config);
		
		me.tbar = [{
			text : '添加',
			handler : function(){
				me.showPortletList();
			}
		}];
		
		this.callParent(arguments);
	},
	
	afterCreate : function(){
		
		var me = this;
		
        ExtUtils.doUrlAction(me.readUrl , {pageable : false} , function(obj){
        	var data = obj.data;
        	
        	if(data && data.length == 0){
        		ExtUtils.tipMsg(me , '提示' , '尚未添加自定义组件');
        		return;
        	}
        	
        	if(me.portalColumns){
        		
        		var cols = me.query('portalcolumn');
        		
        		for(var i = 0 ; i < me.portalColumns ; i++){
        			var portlets = me.findPortlets(data , i);
        			
        			if(portlets && portlets.length > 0){
        				for(var j = 0 ; j < portlets.length ; j++){
        					var view = Ext.create(portlets[j].uiPortlet_xtype, {
				    			uiPortlet : portlets[j].uiPortlet,
				    			userDashboard : portlets[j].id,
				    			userConfig : portlets[j].userConfig
				    		});
        					cols[i].add({
        			        		height : 300,
        			        		title : portlets[j].uiPortlet_name,
        			        		items : [view]
        					});
        				}
        			}
        		}
        	}
        });
	},
	
	findPortlets : function(data , colIndex){
	    	var portlets = [];
	    	for(var i = 0 ; i < data.length ; i++){
	    		if(data[i].colIndex == colIndex){
	    			portlets.push(data[i]);
	    		}
	    	}
	    	return portlets;
	},
	
	showPortletList : function(){
		var me = this;
		
		var view = Ext.create('AM.view.UiPortletListView' , {
			listActionBar : function(){
				return null;
			},
			
			listGrid : function(){
		    	return {
		    		columns : [
				        {dataIndex:"name",header : '名称'},
				        {dataIndex:"xtype",header : '组件' , hidden : true}
				    ],
				    
				    listeners : {
				    	'itemdblclick' : function(g , record, item, index, e, eOpts ){
				    		var view = Ext.create(record.get('xtype') , {
				    			uiPortlet : record.get('id'),
				    			userDashboard : ''
				    		});
				    		me.addPortlet(view , null , record.get('name'));
		                    this.up('window').close();
				    	}
				    }
		    	}
    		}
			
		});
	   	
    	ExtUtils.win(view , {
            title : "选择组件" ,
            modal: true
	    });
	
	},
	
	onRemovePortlet  : function(view){
		var me = this;
		
		if(view.userDashboard){
			var p = {
					id : view.userDashboard
			};
			
			ExtUtils.doUrlAction(me.deleteUrl , p , function(obj){
	        	ExtUtils.tipMsg(me , '提示' , '配置保存成功');
	        });
		}
		
	},
	
	onAddPortlet  : function(view){
		var me = this;
		var v = view;
		
		if(view.uiPortlet){
			var p = {
					uiPortlet : view.uiPortlet,
					id : view.userDashboard,
					colIndex : view.colIndex,
					sortIndex : view.sortIndex
			};
			
			ExtUtils.doUrlAction(me.updateUrl , p , function(obj){
				v.userDashboard = obj.data.id;
	        	ExtUtils.tipMsg(me , '提示' , '配置保存成功');
	        });
		}
	},
	
	onReorderPortlet  : function(){
		var me = this;
		var params = [];
		var cols = me.query('portalcolumn');
		if(!cols || cols.length == 0){
			return;
		}
		
		for(var i = 0 ; i < cols.length ; i++){
			var portlets = cols[i].query('apppportletpanel');
			if(!portlets || portlets.length == 0){
				continue;
			}
			
			for(var j = 0 ; j <  portlets.length ; j++){
				portlets[j].colIndex = i;
				portlets[j].sortIndex = j;
				
				params.push({
					id : portlets[j].userDashboard,
					colIndex : portlets[j].colIndex,
					sortIndex : portlets[j].sortIndex
				});
			}
		}
		
		ExtUtils.doUrlAction(me.batchedUpdateUrl , {recordsToModify : params} , function(obj){
        	ExtUtils.tipMsg(me , '提示' , '配置保存成功');
        });
		
	}
	
});