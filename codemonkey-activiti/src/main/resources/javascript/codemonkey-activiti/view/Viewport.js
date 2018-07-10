Ext.define('codemonkey-activiti.view.Viewport', {
    extend: 'Ext.container.Viewport',

    layout : 'border',
    
    initComponent: function() {
        var me = this;

        var treeStore = this.buildTreeStore();
        
        Ext.applyIf(me, {
            items: [ {
                xtype: 'treepanel',
                title : '导航',
            	region: 'west',
            	width :200,
            	split: true,
    		    minWidth: 175,
    		    maxWidth: 400,
    		    collapsible: true,
    		    animCollapse: true,
                store: treeStore,
                listeners : {
                	itemclick : function( panel , record, item, index, e, eOpts ){
                		me.functionNodeOnclick(record.data);
                	}
                }
            },{
        		xtype : 'tabpanel',
        		region : 'center',
        		items : []
            }]
        });
        me.callParent(arguments);
    },
    
    buildTreeStore : function(){
    	
    	Ext.define('demoTree' , {
    		 extend: 'Ext.data.Model',
    		 fields : ['text' , 'fn' , 'viewClass']
    	});
    	
    	var m1 = {
            text: '我的任务',
            expanded: true,
            children: [{
                text: '我的待办任务',
                viewClass : 'codemonkey-activiti.view.MyToDoTaskListCombinedView',
                leaf: true
            },{
                text: '我的已办任务',
                viewClass : 'codemonkey-activiti.view.MyFinishedTaskListView',
                leaf: true
            }]
        };
    	
    	var m2 = {
            text: '管理',
            expanded: true,
            children: [{
                text: '流程管理',
                viewClass : 'codemonkey-activiti.view.ProcessListView',
                leaf: true
            },{
                text: '实例管理',
                viewClass : 'codemonkey-activiti.view.ProcessInstanceListView',
                leaf: true
            },{
                text: '任务管理',
                viewClass : 'codemonkey-activiti.view.TaskListView',
                leaf: true
            },{
                text: '发布管理',
                viewClass : 'codemonkey-activiti.view.DeploymentListView',
                leaf: true
            }]
        };
    	
    	var store = Ext.create('Ext.data.TreeStore', {
    		model : 'demoTree',
    		root : {
	            expanded: true,
	            text: '演示',
	            children: [
	                m1,
	                m2
	            ]
	        }
    	});
    	
    	return store;
    },
    
    functionNodeOnclick : function(item , override){
    	var me = this;
    	if(item.viewClass){
    		
    		var tab = me.down('panel[$className=' + item.viewClass + ']');
    		
    		if(tab){
    			me.down('tabpanel').setActiveTab(tab);
    			return;
    		}
    		
			var view = Ext.create(item.viewClass , item.viewConfig);
			
			Ext.apply(view , override , {
				title : view.winTitle,
				closable : true
			});
			
    		me.down('tabpanel').add(view);
    		me.down('tabpanel').setActiveTab(view);
    		
    	}
    }
});