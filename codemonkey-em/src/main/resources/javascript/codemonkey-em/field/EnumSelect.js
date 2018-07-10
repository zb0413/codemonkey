Ext.define('AM.field.EnumSelectField', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.enumselect',

	triggers : {
		clear: {
	        cls: Ext.baseCSSPrefix + 'form-clear-trigger',
	        handler: function() {
	            this.setValue(null);
	        }
	    }
	},
	  
	valueField:'name',
	displayField:'text',
	forceSelection : true,
	editable : false,
	emptyText : '(空)',
	matchFieldWidth : true,
	allowEmptyOption : true,
	
	packageName : 'com.codemonkey.domain',
	className : null,
	method : null,
	
	modelFields : ['name' , 'text'],
	
	constructor : function(config) {
		var me = this;
		
		Ext.apply(me , config);
		
		if(!me.className){
			alert("className not found");
			return;
		}
		
		me.modelName = 'Enum-' + me.className;
		
		if(me.queryMode == 'local'){
			me.store = ExtUtils.localStore({
				modelName : me.modelName,
				modelFields : me.modelFields,
				data : me.data
			});
		}else{
			me.store = ExtUtils.ajaxStore({
				modelName : me.modelName,
				modelFields : me.modelFields,
				jsonUrl : NS.url('Enum')
			},{
				autoLoad : true,
				listeners : {
					'beforeload' : function(store, options){
						var p = ExtUtils.buildQueryInfo({
							packageName : me.packageName,
							className : me.className,
							method : me.method || '',
							allowEmptyOption : me.allowEmptyOption
						});
						Ext.apply(store.proxy.extraParams , p);
					}
				}
			});
		}
		this.callParent();
	},
	
	buildLocalStore : function(){
		var me = this;
		var d = me.data;
		if(d && d.length > 0 && d[0][0] != '') {
			d.unshift(['','']);
		}
		return new Ext.data.ArrayStore({
		
		    id: 'ComboStore' + me.name, 
		    fields: [
		        me.valueField,
		        me.displayField
		    ],
		    data: d
		});
	},
	
	onTrigger1Click : function(){
       this.setValue(null);
    }
});