
Ext.define('AM.view.UserDashboardListView', {
    
	extend: 'AM.view.AppBatchedPanel',
	
    modelName : 'UserDashboardList',
    
    cols : [
        {header: 'id', dataIndex: 'id' , hidden : true},
        {dataIndex:"appUser_username",header : '用户名'},
        {dataIndex:"uiPortlet",header : '组件id' , editor : {xtype : 'textfield'}},
        {dataIndex:"uiPortlet_xtype",header : '组件' , editor : {xtype : 'textfield'}},
        {dataIndex:"userConfig",header : '用户设置' , editor : {xtype : 'textfield'}},
        {dataIndex:"sortIndex",header : '排序号' , editor : {xtype : 'textfield'}}
    ],
    
    formItems : function(){
   		return [
	        {name : 'user。username' , xtype : 'textfield' , fieldLabel : '用户名'},
	        {name : 'uiPortlet。xtype' , xtype : 'textfield' , fieldLabel : '组件'}
   		];
	}
});
