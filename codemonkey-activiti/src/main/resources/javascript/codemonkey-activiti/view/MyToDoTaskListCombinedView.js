Ext.define('codemonkey-activiti.view.MyToDoTaskListCombinedView', {
    
	extend: 'Ext.panel.Panel',

    winTitle : '我的待办任务',
    
    layout : {
    	type : 'vbox',
		align: 'stretch'
    },
    
    items : [
         Ext.create('codemonkey-activiti.view.MyToToTaskListView',{flex : 1}),
         Ext.create('codemonkey-activiti.view.MyAvailableTaskListView',{flex : 1})
    ]
   
});