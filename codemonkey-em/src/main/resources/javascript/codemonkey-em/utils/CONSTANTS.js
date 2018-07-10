Ext.define('AM.utils.CONSTANTS', {
	
	PAGE_SIZE : 25,
	NEW_ENTITY_ID : -1,
	REQUEST_ROOT : '/ext/',
	WEB_CONTEXT : '',
	
	READ : 'read',
	CREATE : 'create',
	UPDATE : 'update',
	DESTROY : 'destroy',
	BATCHED_UPDATE : 'batchedUpdate',
	
	constructor : function(config) {
		var ctxElm = document.getElementById("ctx");
		if(ctxElm){
			this.WEB_CONTEXT = ctxElm.value; 
		}
	},
	
	REPORT_PROTOCOL : 'http://',
    REPORT_ROOT : ':28080/spreadsheet/vision/openresource.jsp?',
    REPORT_URL : 'user=admin&password=manager&resid=',
    
    //list查询 一行默认高度
	SEARCH_FORM_HEIGHT_1 : 86 ,
	//list查询  两行默认高度
	SEARCH_FORM_HEIGHT_2 : 125 ,
	//list查询  三行默认高度
	SEARCH_FORM_HEIGHT_3 : 165 ,
	//actioncolumn默认宽度
	ACTION_COLUMN_WIDTH  : 55  
	

});


