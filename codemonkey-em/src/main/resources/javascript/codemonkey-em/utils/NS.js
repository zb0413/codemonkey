Ext.define('AM.utils.NS', {
	
	url : function(modelName , actionName){
		
		actionName = actionName || C.READ;
		
		return C.WEB_CONTEXT + C.REQUEST_ROOT + this.uncapitalize(modelName) + '/' + actionName;
	},
	
	uncapitalize : function(str){
		return Ext.String.uncapitalize(str);
	},
	
	capitalize : function(str){
		return Ext.String.capitalize(str);
	}
	
});

