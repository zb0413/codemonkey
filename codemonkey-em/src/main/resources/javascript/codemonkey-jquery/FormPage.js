function FormPage($obj , config){

	this.$obj = $obj;
	this.modelName = '';
	this.readUrl = null;
	this.readParams = {id : C.NEW_ENTITY_ID};
	this.submitUrl = null;
	this.listUrl = null;
	
	this.afterModelLoad = function(data){
		
	};
	this.beforeSave = function(values){};
	this.userValidate = function(values){
		return true;
	};
	
	this.manageFields = function(){};
	
	this.save_callback = function(values){};
	
	this.doSave = function(){
		var me = this;
		
		var form = $('.form_items' , this.$obj);
		
		var values = me.getFormValues();
		
		if(values){
			me.beforeSubmit(values);
			me.encodeSubmitValues(values);
			JQUtils.mask(me.$obj);
			JQUtils.post(me.submitUrl , values , 
				function(result) {
					JQUtils.unmask(me.$obj);
					JQUtils.setFormValues(form , result.data);
					me.afterModelLoad(result.data);
					me.manageFields();
					JQUtils.tipMsg(me.$obj , '系统提示' , '保存成功');
					me.save_callback(result.data);
				},
				function(result) {
					JQUtils.unmask(me.$obj);
					me.markInvalidField(form , result.errorFields);
				}
			);
		}
	};
	
	this.getFormValues = function(){
		var me = this;
		var form = $('.form_items' , this.$obj);
		
		var values = JQUtils.getFormValues(form);
		me.beforeSave(values);
		
		if(me.userValidate){
			flag = me.userValidate(values);
		}
		
		if(!flag){
			return null;
		}
		return values;
	};
	
	this.doAction = function(url , values , tipMsg , callback){
		var me = this;
				
		if(values){
			JQUtils.mask(me.$obj);
			JQUtils.post(url , values , 
				function(result) {
					me.afterModelLoad(result.data);
					if(tipMsg){
						JQUtils.tipMsg(me.$obj , '系统提示' , tipMsg);
					}
					if(callback){
						callback(result.data , me);
					}
					JQUtils.unmask(me.$obj);
				} , function(result){
					JQUtils.unmask(me.$obj);
				}
			);
		}
	};
	
	this.markInvalidField = function($form , errorFields){
		if(!$form){
			return;
		}
		
		if(!errorFields || errorFields.length == 0){
			return;
		}
		
		for(var i = 0 ; i < errorFields.length ; i++){
			var input = $( 'input[name=' + errorFields[i].fieldName + ']', $form);
			if(input.length > 0){
				input.attr('data-toggle' , 'tooltip');
				input.attr('data-placement' , 'bottom');
				input.attr('title' , errorFields[i].message);
				//input.tooltip('show');
				
				input.parent('.input-group').addClass('has-error');
			}
		}
	},
	//TODO 提取到CommonUtils
	this.beforeSubmit = function(values){
		if(values){
			for(var p in values){
				if(_.isArray(values[p]) || _.isObject(values[p])){
					values[p] = JSON.stringify(values[p]);
				}
			}
		}
	};
	//TODO 提取到CommonUtils
	this.encodeSubmitValues = function(values){
		if(values){
			for(var p in values){
				values[p] = JQUtils.encodeURL(values[p]);
			}
		}
	};
	
	this.doBack = function(){
		window.location = this.listUrl;
	};
	
	this.doCreate = function(){
		this.doRead({id : C.NEW_ENTITY_ID});
	};
	
	this.doInit = function(){
		var me = this;
		if(!this.$obj){
			alert('jquery Object is null');
			return;
		}
		if(!this.modelName){
			alert('modelName is null');
			return;
		}
		
		me.submitUrl = me.submitUrl || NS.url(me.modelName , C.UPDATE);
		
		me.readUrl = me.readUrl || NS.url(me.modelName , C.READ);
		
		me.listModelName = me.modelName + 'List';
		
		me.listUrl = me.listUrl || NS.url(me.listModelName , C.SHOW);
		
		this.doRead();
		
		//action_bar
		var actionBar = $('.action_bar' , this.$obj);
		JQUtils.regActions(actionBar , this);
		
		this.resizeWin();
		
	}
	
	this.resizeWin = function(){
		if(parent && parent.window){
			parent.window.scroll(0,0);
		}
	};
	
	this.doRead = function(p , callback){
		
		var me = this;
		
		var params = p || this.readParams;
		
		if(this.readUrl && params){
			
			var form = $('.form_items' , this.$obj);
			
			JQUtils.mask(me.$obj);
			JQUtils.post(this.readUrl , params , 
				function(result) {
					JQUtils.unmask(me.$obj);
					JQUtils.setFormValues(form , result.data);
					me.afterModelLoad(result.data);
					me.manageFields();
					if(callback){
						callback(result.data);
					}
				},
				function(result) {
					JQUtils.unmask(me.$obj);
				}
			);
		}
	};
	
	$.extend(true , this , config);
	
	this.doInit();
	
	return this;
	
}