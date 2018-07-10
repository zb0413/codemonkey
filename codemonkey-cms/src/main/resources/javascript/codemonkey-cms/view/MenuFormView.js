//基础设置-科室信息 表单
Ext.define('codemonkey-cms.view.MenuFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'Menu',
	/**自定义常量**/
    
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
		                         {xtype:"textfield",fieldLabel:"栏目名称",name:"name" , allowBlank : false , colspan : 50 },
		                         {modelName:"MenuList",xtype:"searchingselect",fieldLabel:"上级栏目",name:"parent" , colspan : 50 },
		                         {xtype:"enumselect",className:"Status",fieldLabel:"启用状态",name:"status" , colspan : 50 },
		                         {xtype:"textfield",fieldLabel:"url",name:"url" , colspan : 50 },
		                         {xtype:"entityselect", modelName : 'pageTemplateList' , fieldLabel:"模板",name:"template", colspan : 50, allowBlank : false }
		                         ]);
		
    	return [p2  , p1];
    }
    
    
});