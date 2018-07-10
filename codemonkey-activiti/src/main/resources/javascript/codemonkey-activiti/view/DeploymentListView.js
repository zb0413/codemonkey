Ext.define('codemonkey-activiti.view.DeploymentListView', {
    
	extend: 'AM.view.AppListPanel',
    modelName : 'DeploymentList',

    modelFields : function(){
    	return [ "id" ,"name" , "deploymentTime" , "category", "url", "tenantId"].concat(ExtUtils.defaultModelFields);
    },
    
    limitParam : 'size',
    
    readUrl : '/repository/deployments',
    
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
							me.doSearch({'name_Like' : value});
						}
					}
				}
			}],
    		columns : [
		        {dataIndex: 'id',header: 'id'},
		        {dataIndex:"name",header : 'name'},
		        {dataIndex:"deploymentTime",header : 'deploymentTime'},
		        {dataIndex:"category",header : 'category'},
		        {dataIndex:"url",header : 'url'},
		        {dataIndex:"tenantId",header : 'tenantId'}
		    ]
    	};
    },
    
    pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
	}
});