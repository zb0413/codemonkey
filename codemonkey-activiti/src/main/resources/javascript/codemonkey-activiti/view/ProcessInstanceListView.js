Ext.define('codemonkey-activiti.view.ProcessInstanceListView', {
    
	extend: 'AM.view.AppListPanel',
    modelName : 'ProcessInstanceList',

    modelFields : function(){
    	return [ "id" ,"url" ,"businessKey" , "suspended" , "ended","processDefinitionId","processDefinitionUrl","activityId","tenantId","completed"];
    },
    
    limitParam : 'size',
    
    readUrl : '/runtime/process-instances',
    
    buildSearchParams : function(p){
    	var params = {};
    	Ext.apply(params , this.searchParams);
    	if(p){
    		Ext.apply(params , p);
    	}
    	return params;
    },
    
    winTitle : '实例管理',

    listGrid : function(){
    	var me = this;
    	return {
    		tbar : [{
    			text : '激活实例',
    			handler : function(){
					 me.activateProcessInstance();
				}
    		},{
    			text : '挂起实例',
    			handler : function(){
					 me.suspendProcessInstance();
				}
    		},{
    			text : '删除实例',
    			handler : function(){
					 me.deleteProcessInstance();
				}
    		},{
				xtype : 'triggerfield',
				emptyText:'实例名称',
				trigger1Cls:'x-form-search-trigger',
				enableKeyEvents : true,
				onTrigger1Click : function(){
					var value = this.getValue();
					if(value){
						me.doSearch({'processDefinitionKey' : value});
					}else{
						me.doSearch();
					}
				},
				listeners:{
					'specialkey' : function(obj,e){
						if (e.getKey() == Ext.EventObject.ENTER) {
							var value = this.getValue();
							if(value){
								me.doSearch({'processDefinitionKey' : value});
							}else{
								me.doSearch();
							}
						}
					}
				}
			},'-',{
				text:'查看任务',
				handler: function(){
					me.viewTasks();
				}
			}],
			
    		columns : [
		        {header: 'id', dataIndex: 'id'},
		        {dataIndex:"url",header : 'url'},
		        {dataIndex:"businessKey",header : 'businessKey'},
		        {dataIndex:"suspended",header : 'suspended'},
		        {dataIndex:"ended",header : 'ended'},
		        {dataIndex:"processDefinitionId",header : 'processDefinitionId'},
		        {dataIndex:"processDefinitionUrl",header : 'processDefinitionUrl'},
		        {dataIndex:"activityId",header : 'activityId'},
		        {dataIndex:"tenantId",header : 'tenantId'},
		        {dataIndex:"completed",header : 'completed'}
		    ]
    	};
    },
    
    pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
	},
	
	suspendProcessInstance: function(){
		
		var me = this;
    	var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var url = NS.url(me.modelName , 'suspendProcessInstance')
			ExtUtils.doUrlAction(url , records[0].data , function(obj){
				me.doSearch();
	        });
		}
	},
	
	activateProcessInstance: function(){
		
		var me = this;
    	var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var url = NS.url(me.modelName , 'activateProcessInstance')
			ExtUtils.doUrlAction(url , records[0].data , function(obj){
				me.doSearch();
	        });
		}
	},
	
	deleteProcessInstance: function(){
		
		var me = this;
    	var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var url = NS.url(me.modelName , 'deleteProcessInstance')
			ExtUtils.doUrlAction(url , {
				businessKey : records[0].businessKey,
				deleteReason : 'user deleted'
			} , function(obj){
	        	me.doSearch();
	        });
		}
	},
	
	viewTasks : function(){
		var me = this;
		var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var view = Ext.create('codemonkey-activiti.view.TaskListView' , {
				searchParams : {processInstanceId : records[0].get('id')}
			});
		}
		
		ExtUtils.win(view , {title : '实例任务'});
	}
});