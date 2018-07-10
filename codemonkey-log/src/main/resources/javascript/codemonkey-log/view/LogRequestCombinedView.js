//操作日志
Ext.define('codemonkey-log.view.LogRequestCombinedView', {
	
	extend: 'AM.view.CombinedPanel',
	
	formClass : 'codemonkey-log.view.LogRequestFormView',
	
	listClass : 'codemonkey-log.view.LogRequestListView',
	
	formConfig : {
		formBar : function(){
			return [];
		}
	},
	listConfig : {},
	
	winTitle : '操作日志信息'	
});