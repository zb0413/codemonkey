Ext.define('AM.field.EntitySelect', {
	
	extend : 'Ext.form.field.ComboBox',
	
	alias : 'widget.entityselect',
	
	triggers : {
		clear: {
	        cls: Ext.baseCSSPrefix + 'form-clear-trigger',
	        handler: function() {
	            this.setValue(null);
	        }
	    }
	},

	editable : true,
    
    forceSelection : true,
    
    matchFieldWidth : true,
	
	modelName : null,
	
	hiddenFields : [],
	
	jsonUrl : null,
	
	searchParams : Ext.emptyFn,
	
	allowEmptyOption : true,
	
	valueField : 'id',
	
	displayField : 'name',
	
	emptyText : '(空)',
	
	minChars : 2,
	
	onSelected : Ext.emptyFn,
	
	onCleared : Ext.emptyFn,
	
	afterSetValue : Ext.emptyFn,
	
	constructor : function(config) {

		var me = this;
		
		me.listeners = {
			select : function( combo, record, eOpts ){
				
				me.onSelected(record , combo);
			
			},
			
			expand : function( queryPlan, eOpts ){
				var me = this;
				me.store.proxy.extraParams = {};
				var p = me.searchParams() || {};
				Ext.apply(me.store.proxy.extraParams , p);
				me.store.load();
			},
			
			keyup : function(field , e, eOpts ){
				if(field.readOnly){
					Ext.EventManager.stopEvent( e );
				}
			},
			
			keydown: function(field , e, eOpts ){
				if(field.readOnly){
					Ext.EventManager.stopEvent( e );
				}
			}
		};
		
		Ext.apply(me , config);
		
		me.valueField = me.valueField || 'id';
		    
		me.displayField = me.displayField || 'name';
		
		if(!me.store){
			me.modelFields = [me.valueField , me.displayField].concat(me.hiddenFields);
			var storeOverride = me.configStore();
			
			me.store = ExtUtils.ajaxStore({
				modelName : me.modelName,
				modelFields : me.modelFields,
				jsonUrl : me.jsonUrl || NS.url(me.modelName)
				
			} , storeOverride);
		}
		
		this.callParent();
	},
	
	configStore : function(){
		return {};
	},
	
	setValue: function(value) {
    	var me = this;
    	if(!value){
			this.callParent(value);
//			this.onCleared();
			return;
		}
    	
		if(Ext.isArray(value)){
			this.callParent(value);
			if(value.length > 0){
//				this.onSelected(value[0] ,  this);
			}
			return;
		}else if(Ext.isObject(value)){
			this.callParent(value);
//			this.onSelected([value] ,  this);
			return;
		}
		
		if(value){
			
			this.setValue(null);
			
			var p = me.searchParams() || {};
			
			Ext.apply(p , {id : value});
			
			Ext.apply(this.store.proxy.extraParams , p);
			
			this.store.load({
				callback: function(records){
					if(records){
						var r = me.findResult(records , value , this);
						me.setValue(r);
						me.afterSetValue();
					}
				}
			});
		}
    },
    
    findResult : function(records , value , store){
    	var me = this;
    	
//    	if(me.allowEmptyOption){
//    		var obj = {};
//    		obj[me.valueField] = "";
//    		obj[me.displayField] = me.emptyText;
//			var r = Ext.ModelManager.create(obj, store.model.modelName);
//			store.insert(0, r);
//		}
		
    	if(!records || !value){
    		return null;
    	}
    	
    	if(records.length == 0){
    		return null;
    	}
    	
    	for(var i = 0 ; i < records.length ; i++){
    		var r = records[i];
    		if(value == r.get(this.valueField)){
    			return [r];
    		}
    	}
    	
    	return null;
    	
    },
    
    onTrigger1Click : function(){
        this.setValue(null);
        this.onCleared();
    }
	
});