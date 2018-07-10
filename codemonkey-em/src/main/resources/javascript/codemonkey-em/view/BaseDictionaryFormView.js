//基础设置-编辑码表信息
Ext.define('AM.view.BaseDictionaryFormView', {
    extend: 'AM.view.AppEditPanel',
    
    /**常量**/
    modelName : 'BaseDictionary',
	/**自定义常量**/
    
    /**实现父类方法**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
               {xtype:'textarea'  , name:'description' , fieldLabel:'备注' , width : 545 ,  colspan : 100 }
               ] 
		);
		
    	var p3 = ExtUtils.tableLayout([
	            { xtype:"textfield"	, name:"fieldType" ,  fieldLabel:'分组名称' , allowBlank : false , labelWidth : 80 , colspan : 50 },
	            { xtype:"textfield"	, name:"name" ,  fieldLabel:'显示名称' , allowBlank : false , labelWidth : 80 , colspan : 50 },
	            
	            { xtype:"textfield"	, name:"codeValue" ,  fieldLabel:'码值' , allowBlank : false , labelWidth : 80 , colspan : 50 },
	            { xtype:"textfield"	, name:"sortIndex" ,  fieldLabel:'排序(同组)' ,  labelWidth : 80 , colspan : 50  }
	            
          ]);
		
    	return [p3 , p2 , p1];
    }
    
});