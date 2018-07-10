Ext.define('codemonkey-activiti.view.UploadProcessView', {
    
	extend: 'AM.view.AppEditPanel',
	
	requires: ['AM.view.AppEditPanel'],
	
	modelName : 'ProcessList',
	
	winModal : true,
	
	winHeight : 300,
	
	winWidth  : 300,
	
	readParams : null,
	
    /**覆盖父类方法**/
    formItems : function(){
    	var me = this;
    	var p2 =  ExtUtils.vboxLayout([
			{xtype : 'textfield' , allowBlank:false, name : 'name' , fieldLabel:'名称'},
			{
				 xtype : 'filefield',
				 fieldLabel:'模型文件',
				 name:'file',
				 emptyText:'请选择符合规范的BPMN流程模型文件...',
				 allowBlank:false,
				 blankText:'模型文件不能为空',
				 buttonText:'浏览BPMN文件'
			},
			{xtype : 'textarea' , name : 'description' , flex : 1 , fieldLabel:'备注'}
		]);
    	
    	var p1 = ExtUtils.creationInfoPanel();
		return [p2 , p1];
	},
	
	doSave : function(){
		
		var me = this;
		var form = me.getForm();
		var url = NS.url(me.modelName , 'uploadProcess');
		
        if(form.isValid()){
            form.submit({
                url: url,
                waitMsg: '上传中...',
                success: function(fp, action) {
                	
                	var data = action.result.data;
                	
                	if(!data.complete){
                		
                	}
                    me.save_callback();
                },
                failure : function(form, action) {
    				var response = action.response;
    				
    				if(response.status != 200){
    					ExtUtils.handleSysError({
    						errorCode : response.status,
    						errorMsg : response.statusText
    					});
    				}else{
    					ExtUtils.handleSysError({
    						errorCode : action.result.errorCode,
    						errorMsg : action.result.errorMsg
    					});
    				}
                }
            });
        }
	},
	
    save_callback : function(){
    	var me = this;
    	me.parentPanel.doSearch();
		this.callParent();
		this.up('window').close();
	}
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
		
});
