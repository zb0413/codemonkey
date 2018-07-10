//模板 表单
Ext.define('codemonkey-cms.view.PageTemplateFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'PageTemplate',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
             { name  : 'name'   , xtype : 'textfield' , fieldLabel : '模板名称' , allowBlank : false  , colspan : 50 },
             {xtype:"enumselect",packageName:'com.codemonkey.domain.cms' ,fieldLabel:"模板类型",name:"type", allowBlank : false,className:"PageTemplateType" , colspan : 50},
             {name:"defaultTemplate", xtype:"checkbox" , fieldLabel:"默认" , colspan : 100},
             { name  : 'description'   , xtype : 'textarea' , fieldLabel : '内容' , allowBlank : false , height:350 , width : 545, colspan : 100 , maxLength : 99999999999999999999999999}
        ]);
		
    	return [p2 , p1];
    }
});