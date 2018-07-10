Ext.define('AM.view.AppEditPanel', {
	extend : 'Ext.form.Panel',

	alias: 'widget.appeditpanel',
	
	layout : 'fit',
	
	containerType : 'window',
	
	scrollable : 'y',
	
	readUrl : null,
	
	readParams : {id : C.NEW_ENTITY_ID},
	
	//新建对象初始化属性
	defaultProps : null,
	
	submitUrl : null,
	
	parentPanel : null,
	
	formItems : Ext.emptyFn,
	winWidth : 600,
    winHeight : 500,	
    winTitle : null,
    winModal : true,
    
    formLayout : {
		type : 'vbox',
		align : 'stretch',
		pack  : 'start'
	},
	
	afterModelLoad : function(result){
		var form = this.getForm();
		ExtUtils.setFormValues(form , result);
		ExtUtils.clearInvalidFields(this.getForm());
	},
	
	beforeSave : function(values){
		
	},
	
	save_callback : function(values){
		
	},
	
	userValidate : function(values){
		return true;
	},
	
	manageFields : function(values){
		
	},
	
	formBar : function(){
		var me = this;
		return [ {
			action : 'save', 
			text : '保存',
			iconCls : 'btn-save',
			handler : function(){
				me.doSave();
			}
		},
		{
			action : 'cancel' , 
			text : '关闭',
			iconCls : 'btn-close',
			handler : function(){
				me.up(me.containerType).close();
			}
		} ];
	},
	
	constructor : function(config) {

		var me = this;
		
		me.submitUrl = me.submitUrl || NS.url(me.modelName , C.UPDATE);
		
		me.readUrl = me.readUrl || NS.url(me.modelName , C.READ);

		Ext.apply(me, config);

		me.items = {
			xtype : 'panel',
			bodyPadding : 0,
			border : 0,
			layout : me.formLayout,
			items : me.formItems(),//[ExtUtils.creationInfoPanel()].concat(me.formItems());
			buttons : me.formBar()
		};
		
		if(me.winTitle){
			Ext.apply(me.items , {title : "编辑" + me.winTitle });
		}
		
		me.callParent();
		
		me.on('afterrender' , function(){
			me.fixWindowOverflowY();
			me.doRead();
		});
		
	},
	
	 fixWindowOverflowY : function(){
		var p = this;
		if(!p){
			return;
		}
    	var els = Ext.query('#' + p.getId() + '-innerCt');
    	
    	if(Ext.versions.core.version >= '6.0.0'){
    		
    		els = Ext.query('#panel-' + (parseInt(p.getId().split('-')[2])+1) + '-innerCt');
    	}
    	
    	if(els && els[0]){
    		Ext.DomHelper.applyStyles(els[0], {'overflow-y':'auto'});
    	}
    },
    
    getFormValues : function(){
    	var me = this;
    	
    	var flag = true;
    	
    	var form = me.getForm();
    	var values = ExtUtils.getFormValues(form);
    	
		me.beforeSave(values);
		
		var errorMsg = ExtUtils.validateForm(form);
		
		if(errorMsg){
			ExtUtils.tipMsg(me , '校验失败' , errorMsg);
			return;
		}
		
		if(me.userValidate){
			flag = me.userValidate(values);
		}
		
		if(!flag){
			ExtUtils.tipMsg(me , '错误' , '校验失败');
			return;
		}
		
		if(values){
			me.beforeSubmit(values);
			me.encodeSubmitValues(values);
		}
		return values;
    },
	
	doSave : function(){
		var me = this;
		
		var values = me.getFormValues();
		
		if(values){
			ExtUtils.mask(this);
			ExtUtils.doUrlAction(me.submitUrl , values , 
				function(result) {
					me.doBack();
					me.afterModelLoad(result.data);
					ExtUtils.tipMsg(me , '成功' , '保存成功');
					me.save_callback(result.data);
					ExtUtils.unmask(me);
				} , function(result){
					ExtUtils.unmask(me);
				}
			);
		}
	},
	
	doAction : function(url , values , tipMsg , callback){
		var me = this;
		
		if(values){
			ExtUtils.mask(this);
			ExtUtils.doUrlAction(url , values , 
				function(result) {
					me.doBack();
					me.afterModelLoad(result.data);
					if(tipMsg){
						ExtUtils.tipMsg(me , '成功' , tipMsg);
					}
					if(callback){
						callback(result.data , me);
					}
					ExtUtils.unmask(me);
				} , function(result){
					ExtUtils.unmask(me);
				}
			);
		}
	},
	//TODO 提取到CommonUtils
	beforeSubmit : function(values){
		if(values){
			for(var p in values){
				if(Ext.isArray(values[p]) || Ext.isObject(values[p])){
					values[p] = Ext.encode(values[p]);
				}
			}
		}
	},
	//TODO 提取到CommonUtils
	encodeSubmitValues : function(values){
		if(values){
			for(var p in values){
				values[p] = ExtUtils.encodeURL(values[p]);
			}
		}
	},
	
	doBack : function(){
		//this.up(this.containerType).close();
		this.reloadList();
	},
	
	reloadList : function(){
		if(this.parentPanel && this.parentPanel.doSearch){
			this.parentPanel.doSearch();
		}
	},
	
	doCreate : function(){
		this.doRead({id : C.NEW_ENTITY_ID});
	},
	
	doRead : function(p){
		var me = this;
		//var p = Ext.Object.toQueryString(this.readParams);
		//var url = this.readUrl + '?' + p; 
		
		var readParams = p || this.readParams;
		
		if(this.defaultProps){
			Ext.apply(readParams , {defaultProps : this.defaultProps}); 
		}
		
		if(this.readUrl && readParams){
			ExtUtils.mask(me);
			ExtUtils.doUrlAction(
					this.readUrl , 
					readParams , 
					function(result){
						var data = result.data;
						ExtUtils.unmask(me);
			  	        me.afterModelLoad(data);
			  	        me.manageFields(data);
			  	        
					},
					function(result){
						ExtUtils.unmask(me);
					}
			);
		}
	},
	
	getEntityId : function(){
		return this.getForm().findField('id').getValue();
	}
	
});