Ext.define('codemonkey-activiti.view.TaskListView', {
    
	extend: 'AM.view.AppListPanel',
    modelName : 'TaskList',

    modelFields : function(){
    	return [ "id" , "url","owner","assignee","delegationState","name","description",
    	         "createTime","dueDate","priority","suspended","taskDefinitionKey",
    	         "tenantId","category","formKey","parentTaskId","parentTaskUrl","executionId","executionUrl",
    	         "processInstanceId","processInstanceUrl","processDefinitionId",
    	         "processDefinitionUrl"];
    },
    
    limitParam : 'size',
    
    readUrl : '/runtime/tasks',
    
    buildSearchParams : function(p){
    	return this.searchParams || {};
    },
    
    winTitle : '任务管理',

    listGrid : function(){
    	var me = this;
    	return {
    		tbar : [{
				xtype : 'triggerfield',
				emptyText:'实例名称',
				trigger1Cls:'x-form-search-trigger',
				enableKeyEvents : true,
				onTrigger1Click : function(){
					var value = this.getValue();
					me.doSearch({'name_Like' : value});
				},
				listeners:{
					'specialkey' : function(obj,e){
						if (e.getKey() == Ext.EventObject.ENTER) {
							var value = this.getValue();
							me.doSearch({'nameLike' : value});
						}
					}
				}
			},'-',{
				text : '完成任务',
    			handler : function(){
					 me.completeTask();
				}
			}],
			
    		columns : [
		        {dataIndex: 'id',header: 'id'},
		        {dataIndex:"url",header : 'url'},
		        {dataIndex:"owner",header : 'owner'},
		        {dataIndex:"assignee",header : 'assignee'},
		        {dataIndex:"delegationState",header : 'delegationState'},
		        {dataIndex:"name",header : 'name'},
		        {dataIndex:"description",header : 'description'},
		        {dataIndex:"createTime",header : 'createTime'},
		        {dataIndex:"dueDate",header : 'dueDate'},
		        
		        {dataIndex:"priority",header : 'priority'},
		        {dataIndex:"suspended",header : 'suspended'},
		        {dataIndex:"taskDefinitionKey",header : 'taskDefinitionKey'},
		        {dataIndex:"tenantId",header : 'tenantId'},
		        {dataIndex:"category",header : 'category'},
		        
		        {dataIndex:"formKey",header : 'formKey'},
		        {dataIndex:"parentTaskId",header : 'parentTaskId'},
		        {dataIndex:"parentTaskUrl",header : 'parentTaskUrl'},
		        {dataIndex:"executionId",header : 'executionId'},
		        {dataIndex:"executionUrl",header : 'executionUrl'},
		        
		        {dataIndex:"processInstanceId",header : 'processInstanceId'},
		        {dataIndex:"processInstanceUrl",header : 'processInstanceUrl'},
		        {dataIndex:"processDefinitionId",header : 'processDefinitionId'},
		        {dataIndex:"processDefinitionUrl",header : 'processDefinitionUrl'}
		    ]
    	};
    },
    
    pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
	},
	
	completeTask : function(){
		var me = this;
		var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var url = NS.url(me.modelName , 'completeTask')
			ExtUtils.doUrlAction(url , records[0].data , function(obj){
				me.doSearch();
	        });
		}
	}
	
});