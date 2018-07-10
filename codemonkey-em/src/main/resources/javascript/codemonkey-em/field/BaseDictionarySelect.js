
Ext.define('AM.field.BaseDictionarySelect', {
    
	extend : 'AM.field.EntitySelect',
	
    alias: 'widget.basedictionaryselect',
    
    modelName : 'BaseDictionaryList',
    
    fieldType : "demo",
    
    configStore : function(){
    	var me = this;
    	var c = this.callParent();
    	
    	Ext.apply(c , {
    		modelFields : ['fieldType'].concat(ExtUtils.defaultModelFields),
    		listeners : {
    			'beforeload' : function( cmp , operation, eOpts ){
    				var proxy = cmp.getProxy();
    				proxy.extraParams = {};
    	    		Ext.apply(proxy.extraParams , ExtUtils.buildQueryInfo(me.searchParams()));
    			}
    		}
    	});
    	
    	return c;
    },
    
    searchParams : function(){
    	return {fieldType : this.fieldType};
    }

});