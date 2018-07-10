
Ext.define('AM.field.PopupSelect', {
    
	extend : 'Ext.form.TwinTriggerField',
	
    alias: 'widget.popupselect',
    
    emptyText : '(空)',
    
    trigger2Cls: Ext.baseCSSPrefix + 'form-clear-trigger',

    trigger1Cls: Ext.baseCSSPrefix + 'form-search-trigger',
    
    listClass : null,
    
    listView : null,
    
    editable : false,
    
    displayField : 'name',
    
    valueField : 'id',
    
    hValue : null,
    
    searchParams : null,
    
    onSelected : function(record ,  cmp){
    	return true;
    },
    
    onCleared : Ext.emptyFn,
    
    defaultListConfig : function(){
    	
    	var me = this;
    	
    	var c = {
			listActionBar : function(){
	    		return null;
	    	},
	    	
	    	itemdblclick : function(record){
	    		
	    		var flag = me.onSelected(record ,  me);
	    		if(flag){
	    			
	    			me.getRawValue(null);
	    			me.setRawValue(record.get(me.displayField));
	    			
	    			me.hValue = record.get(me.valueField);
	    			me.rawValue = record.get(me.displayField);
	    			var win = me.listView.up('window');
		    		if(win){
		    			win.close();
		    		}
	    		}else{
	    			me.setValue(null);
	    		}
	    	}
    	};
    	
    	return c;
    	
    },
    
    listConfig : {},
    
    
    defaultPopupConfig : function(){
    	var me = this;
    	//不能mask窗口header
//    	var ownerCt = this.up('window');
    	return {
//    		ownerCt : ownerCt,
    		constrainHeader : false,
			constrain : false,
    		closeAction : 'hide',
    		buttons : [{
    			text : '确认',
    			handler : function(){
    				var cmp = me.listView.down('grid') || me.listView.down('treepanel');
    				var records = ExtUtils.getSelected(cmp);
    				
    				if(records && records.length > 0){
    					me.listView.itemdblclick(records[0]);
    				}
    			}
			},{
				text : '关闭',
				handler : function(){
					var win = me.listView.up('window');
		    		if(win){
		    			win.close();
		    		}
				}
			}]
    	};
    },
    
    popupConfig : null,
    
    constructor : function(config) {
		
    	var me = this;
    	
    	Ext.apply(me , config);
    	
    	var c = me.defaultListConfig();
    	
    	Ext.apply(c , ExtUtils.prepareConfig(me.listConfig));
    	
		me.listView = Ext.create(me.listClass , c);
		
		//弹出列表不显示action column
		var columns = me.listView.query('actioncolumn');
		if(columns && columns.length > 0){
			for(var i = 0 ; i < columns.length ; i++){
				columns[i].hide();
			}
		}
		
//		var cmp = me.listView.down('grid') || me.listView.down('treepanel');
//		
//		me.store = cmp.getStore();
		
		this.callParent();
	
    },
    
    onTrigger2Click : function(){
        this.setValue(null);
        this.onCleared();
    },
    
    onTrigger1Click : function(/* Ext.EventObject */ e){
		var me = this;
		var c = me.defaultPopupConfig();
    	
    	Ext.apply(c , ExtUtils.prepareConfig(me.popupConfig));
    	
    	if(!me.popupWin){
	    	
	    	if(me.searchParams){
	    		Ext.apply(me.listView , {searchParams : ExtUtils.prepareConfig(me.searchParams)})
	    	}
	    	me.listView.doSearch();
	    	
			me.popupWin = ExtUtils.win(me.listView , c);
    	}else{
    		var listView = me.popupWin.down('panel[doSearch]');
    		if(listView && listView.doSearch){
    			listView.doSearch();
    		}
    		me.popupWin.show();
    	}
	},
	
	clearValue : function(){
    	 var me = this;

         if (me.getValue()) {
             me.setRawValue('');
             me.hValue = '';
             me.updateLayout();
         }
    },
    
	getSubmitValue : function(){
    	return this.hValue;
    },
    
    setValue: function(v) {
    	
    	var me = this;
    	
    	if(!v){
    		this.clearValue();
    		return;
    	}
		
    	me.listView.doSearch({id : v} , function(data){
    	
    		var result;
    		
    		if(Ext.isObject(data)){
    			result = data;
    		}else if(Ext.isArray(data)){
    			result = data[0] ? data[0].data : null;
    		}
    		
    		if(result){
    			me.setRawValue(result[me.displayField]);
    			me.hValue = result[me.valueField];
    		}else{
    			me.clearValue();
    		}
    	});
    }
	
});