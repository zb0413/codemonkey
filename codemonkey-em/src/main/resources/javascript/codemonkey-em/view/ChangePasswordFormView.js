
Ext.define('AM.view.ChangePasswordFormView', {
    extend: 'AM.view.AppEditPanel',
    
    winTitle : '修改登录密码',
    
    modelName : 'AppUser',
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
    	var p2 = ExtUtils.panel([
    	           {xtype:"textfield",fieldLabel:"旧密码" , name : "oldPassword"},              
	               {xtype:"textfield",fieldLabel:"新密码" , name : "password"},
	   			   {xtype:"textfield",fieldLabel:"确认新密码" ,  name : "password_ack"}
               ] 
		);
    	return [p2 , p1];
    	
		
    },
    userValidate : function(values){
    	var me = this;
    	if(!values.oldPassword){
			ExtUtils.tipMsg(me , '提示' , '请输入旧密码！');
			return false ;
		}
		if(!values.password){
			ExtUtils.tipMsg(me , '提示' , '请输入新密码！');
			return false ;
		}
		if(!values.password_ack){
			ExtUtils.tipMsg(me , '提示' , '请确认新密码！');
			return false ;
		}
		if(values.password != values.password_ack){
			ExtUtils.tipMsg(me , '提示' , '两次密码输入不一致！');
			return false ;
		}
    	return true;
    },
    
    doSave : function(){
		var me = this;
		var values = me.getFormValues();
		if(values){
			Ext.apply (values,{needOldPassword : true});
			ExtUtils.mask(this);
			ExtUtils.doUrlAction('/auth/changePassword' , values , 
				function(result) {
					//me.doBack();
					//me.afterModelLoad(result.data);
					ExtUtils.tipMsg(me , '成功' , '保存成功');
					//me.save_callback(result.data);
					ExtUtils.unmask(me);
				} , function(result){
					ExtUtils.unmask(me);
				}
			);
		}
	}

});
