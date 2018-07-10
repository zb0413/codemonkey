
Ext.define('AM.field.SearchingSelect', {
    
	extend : 'AM.field.EntitySelect',
	
    alias: 'widget.searchingselect',
    
    trigger2Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

    trigger1Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    defaultPopupConfig : function(){
    	var me = this;
    	return {
    		modelName : this.modelName,
    		mode : this.mode,
	    	formItems : this.formItems || function(){
//	    		var p1 = ExtUtils.panel([
//					{name : 'code_Like' , xtype : 'textfield' , fieldLabel : i18n.code }
//				]);
				var p2 = ExtUtils.panel([
					{name : 'name_Like' , xtype : 'textfield' , fieldLabel : i18n.name1}
				]);
//				return [p1 , p2];
				return [p2];
			},
		    
			columns :  me.columns,
		    
		    autoSearch : true,
		    
		    searchParams : me.searchParams,
		    
		    itemdblclick : function(records){
		    	me.fireEvent('change' , me, records, me.getValue());
				if(Ext.isArray(records)){
					me.setValue(records);
				}else{
					me.setValue([records]);
				}
				me.onSelected(records ,  me);
			}
    	};
    },
    
    popupConfig : Ext.emptyFn,
    
    constructor : function(config) {
		
    	var me = this;
		
		Ext.apply(me , config);
		
		if(!me.jsonUrl){
			me.jsonUrl = NS.url(me.modelName);
		}
		
		this.store =  me.buildStore(config);
		
		this.callParent();
	
    },
    
    buildStore : function(){
		
		var me = this;
		
		me.valueField = me.valueField || 'id';
	    
		me.displayField = me.displayField || 'name';
		
		var fields = [this.valueField , this.displayField].concat(me.hiddenFields);
		
		var store = ExtUtils.ajaxStore({
			jsonUrl : this.jsonUrl,
			modelName : this.modelName,
			modelFields : fields
		},{
			autoLoad : false,
			listeners : {
				'beforeload' : function(store, options){
					Ext.apply(store.proxy.extraParams , ExtUtils.prepareConfig(this.searchParams));
			    }
			}
		});
		
		return store;
	},
    
    onTrigger1Click : function(/* Ext.EventObject */ e){
		var me = this;
		
		var c = Ext.apply(me.defaultPopupConfig() , me.popupConfig());
		ExtUtils.popup(c);
	}
	
});