//最新人才
Ext.define('codemonkey-cms.view.TalentCombinedView', {
	
	extend: 'AM.view.CombinedPanel',
	
	formClass : 'codemonkey-cms.view.TalentFormView',
	
	listClass : 'codemonkey-cms.view.TalentListView',
	
	winTitle : '内容管理',
	formConfig :{
		 formBar : function(){
				var me = this;
				return [ 
				{
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function(){
						me.doCreate();
					}
				} ];
			}
	}
	
});