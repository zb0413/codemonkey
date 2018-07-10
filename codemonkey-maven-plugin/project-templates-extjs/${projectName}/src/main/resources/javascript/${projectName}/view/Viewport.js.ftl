Ext.define('${projectName}.view.Viewport', {
extend: 'AM.view.DefaultViewport',
	
	buildFunctionNodes : function(){
		return ExtUtils.buildTrees(PAGE_DATA.functionNodes);
	},
	
	buildDefaultMainView : function(){
		return Ext.create('AM.view.MyDashboard');
	},
	
	buildTopPanel : function(){
    	
    	var html = 
    		'<div class="logo">' +
    		'</div>' +
    		'<div class="logo_txt">' + 
    		'</div>' +
		    '<div class="banner_action" style = "float:right">' +
				'<div class="txt_log"> 当前用户：' + PAGE_DATA['currentUsername']  +
					'<a href="\auth/logout" class = "ia-top-redlink">' +
						'退出登录/切换用户' +
					'</a>' +
				'</div>'+
			'</div>';
    	
    	var p = {
			xtype : 'panel',
			region : 'north',
        	cls : 'top-banner',
    		height : 65,
    		html : html	
    	};
    
    	return p;
    }
});