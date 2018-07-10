
Ext.define('AM.view.UiPortletListView', {
    
	extend: 'AM.view.AppListPanel',
	
    modelName : 'UiPortletList',
    
    searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
	
	searchFormMaxHeight : C.SEARCH_FORM_HEIGHT_1,
    
    formItems : function(){
    	var p = ExtUtils.hboxPanel([
	        {name : 'name' , xtype : 'textfield' , fieldLabel : '名称'}
   		]);
   		return [p];
	}
});
