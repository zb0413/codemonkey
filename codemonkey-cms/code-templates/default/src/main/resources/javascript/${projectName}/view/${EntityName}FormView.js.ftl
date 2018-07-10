//基础设置-科室信息 表单
Ext.define('${projectName}.view.${EntityName}FormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : '${EntityName}',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.panel(${fieldsJson});
		
    	return [p2 , p1];
    }
});