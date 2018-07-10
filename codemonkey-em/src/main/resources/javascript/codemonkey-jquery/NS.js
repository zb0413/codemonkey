var NS = {
	getContext : function(){
		return document.getElementById('ctx') != null ? document.getElementById('ctx').value : '';
	},
	url : function(modelName , actionName){
		
		actionName = actionName || C.READ;
		
		return this.getContext() + C.REQUEST_ROOT + modelName + '/' + actionName;
	}
}