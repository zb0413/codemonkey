//基础设置-人员信息 表单
Ext.define('AM.view.LinkToUserFormView', {
    extend: 'AM.view.AppEditPanel',
    
    /**常量**/
    modelName : null,
	/**自定义常量**/
    
    entityField : null,
    
    parentFormView : null,
    
    /**实现父类方法**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
    	var p3 = ExtUtils.tableLayout([
            { xtype:"textfield"	, name:"username" ,  fieldLabel:'用户名' ,  labelWidth : 80 , colspan : 50 },
            { xtype:"textfield"	, name:"password" , fieldLabel:'密码',  labelWidth : 80 , colspan : 50  }
        ],{height : 42});
        
        var gridColumns = [
           	{dataIndex : 'checkFlag' , header : 'checkFlag' , hidden : true }, 
  		    {dataIndex : 'id' , header : 'id' , hidden : true}, 
  		    {dataIndex : "name" , flex:1 , header : '名称'} 
  		];
		
	   	var store = ExtUtils.buildStoreByColumns( "AppRole" , gridColumns);
	   	
        var appRolesGrid = ExtUtils.grid({
 			store : store,
 			columns : gridColumns,
 			title : '用户角色',
 			itemId : 'gridId',
 			tbar: [
	        	   {
	        		   xtype:'textfield'  , name:'name1' , fieldLabel:'名称' , width : 200, labelWidth : 40 , margin :'0 0 0 25',
		       	   	   listeners : {
		       	       		change : function (thisField, newValue, oldValue, eOpts){
		       	       		   var grid = this.up('grid');
		       	       		   grid.getStore().filterBy(
		        		    		   function(item) { 
		        		        		   return item.get('name').indexOf(newValue) >= 0; 
		        		        	   }
		        		       );
		       				}
		       	       }
	        	   }
	        	],
 			selModel: Ext.create('Ext.selection.CheckboxModel', {
 				mode : 'MULTI',
 				allowDeselect : 'true',
 				checkOnly : true
 			}) 
 		});
        
    	return [p3 , appRolesGrid , p1];
    },
    
    /**覆盖父类方法**/
    manageFields : function(values){
    	var form = this.getForm();
    	form.findField('password').setValue('');
    	
    	if(values.id){
    		form.findField('username').setReadOnly(false);
    	}else{
    		form.findField('username').setReadOnly(false);
    	}
    },
    userValidate : function(values){
    	if(!values.id){
    		if(!values.password){
    			Ext.Msg.alert('错误' , '请输入密码');
    			return false;
    		}
    	}
    	return true;
    },
    beforeSave : function(values){
    	var me = this;
    	var obj = {};
    	obj[this.entityField] = this[this.entityField];
    	Ext.apply(values , obj);
    	var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	Ext.apply(values , {
    		roles : roles || []
    	});
    },
    
    afterModelLoad : function(model){
    	var me = this;
    	this.callParent([model]);
 		var roles = model.roles;
 		var grid = me.down('grid');
 		var store = grid.getStore();
 		store.loadData(model.lines || [] , false);
 		for(var i = 0 ; i < store.getCount() ; i++){
 			var record = store.getAt(i);
 			grid.getSelectionModel().deselect(record , true);
 			for(var j = 0 ; j < roles.length ; j++){
 				if(record.get("id") == roles[j].id){
 	 				grid.getSelectionModel().select(record , true);
 				}
 			}
		}
    },
    
    /**自定义页面行为**/
	save_callback : function(){
		this.parentFormView.doRead({id : this[this.entityField]});
		this.up('window').close();
	}
    /**自定义页面行为响应**/
});