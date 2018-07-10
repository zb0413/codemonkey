Ext.define('AM.view.AppUserCombinedView', {
	
	extend: 'AM.view.CombinedPanel',
	
	formClass : 'AM.view.AppUserFormView',
	
	listClass : 'AM.view.AppUserListView',
		
	winTitle : '用户管理',
	
	formConfig : {
		formBar : function(){
			var me = this;
			return [{
				action : 'create', 
				text : '重置密码',
				iconCls : 'btn-new',
				handler : function(){
					me.doResetPassword();
				}
			},'->' ,{
				text : '保存',
				iconCls : 'btn-save',
				handler : function(){
					me.doSave();
				}
			},{
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function(){
					me.doCreate();
				}
			}];
		}
	}
		
});