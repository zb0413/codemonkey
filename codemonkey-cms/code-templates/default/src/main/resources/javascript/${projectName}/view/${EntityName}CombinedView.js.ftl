//一级菜单-二级菜单
Ext.define('${projectName}.view.${EntityName}CombinedView', {
	
	extend: 'AM.view.CombinedPanel',
	
	formClass : 'zhd-me2-hospital.view.${EntityName}FormView',
	
	listClass : 'zhd-me2-hospital.view.${EntityName}ListView',
	
	winTitle : '标题'
	
});