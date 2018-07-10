//基础设置-科室信息 列表
Ext.define('${projectName}.view.${EntityName}ListView', {
    
	extend: 'AM.view.AppListPanel',
	
	requires: ['AM.view.AppListPanel'],
	
	modelName : '${EntityName}List',
	formClass : 'zhd-me2-hospital.view.${EntityName}FormView',
	searchFormHeight : 90,
    searchFormMaxHeight : 90,    
    
    listGrid : function(){
    	var me = this;
		return {
			title : '已创建',
			flex : 6,
			columns : ${columnsJson}
		};
    }
    /**覆盖父类方法**/
	
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
		
});
