Ext.define('codemonkey-activiti.view.MyFinishedTaskListView', {
    
	extend: 'AM.view.AppListPanel',
    modelName : 'MyFinishedTaskList',
    
    modelFields : function(){
    	return [ "id" , "url","owner","assignee","delegationState","name","description",
    	         "createTime","dueDate","priority","suspended","taskDefinitionKey",
    	         "tenantId","category","formKey","parentTaskId","parentTaskUrl","executionId","executionUrl",
    	         "processInstanceId","processInstanceUrl","processDefinitionId",
    	         "processDefinitionUrl"];
    },
    
    limitParam : 'size',
    
    readUrl : '/history/historic-task-instances',
    
    searchParams : {
    	taskAssignee : PAGE_DATA.currentUsername
    },
    
    buildSearchParams : function(p){
    	return this.searchParams;
    },
    
    winTitle : '我的已办任务',

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
							me.doSearch({'name_Like' : value});
						}
					}
				}
			}],
    		columns : [
		        {dataIndex: 'id',header: '任务id'},
		        {dataIndex:"value",header : '任务名称'},
		        {dataIndex:"value",header : '所属实例'},
		        {dataIndex:"name",header : '拥有人'},
		        {dataIndex:"value",header : '执行人'},
		        {dataIndex:"createdDate",header : '开始时间'},
		        {dataIndex:"createdBy",header : '结束时间'},
		        {dataIndex:"createdBy",header : '备注'}
		    ]
    	};
    },
    
    pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
	}
});