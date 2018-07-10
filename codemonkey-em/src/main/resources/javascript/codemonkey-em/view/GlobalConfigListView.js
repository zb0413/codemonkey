
Ext.define('AM.view.GlobalConfigListView', {
    
	extend: 'AM.view.AppBatchedPanel',
    modelName : 'GlobalConfigList',
    
    modelFields : function(){
    	return [ "id" ,"code" ,"name" , "value" , "valueType"].concat(ExtUtils.defaultModelFields);
    },

    cols : [
        {header: 'id', dataIndex: 'id' , hidden : true},
        {dataIndex:"code",header : '标识' , editor : {xtype : 'textfield'}},
        {dataIndex:"name",header : '名称' , editor : {xtype : 'textfield'}},
        {dataIndex:"value",header : '值' , editor : {xtype : 'textfield'}},
        {dataIndex:"valueType",header : '值类型' , editor : {xtype : 'enumselect' , className : 'ValueType'}}
    ],
    
    formItems : function(){ 
   		return [{ name  : 'valueType'   , xtype : 'enumselect' , className : 'ValueType' ,fieldLabel : '值类型'},
   		        { name  : 'code_Like'   , xtype : 'textfield' , fieldLabel : '标识'},
   		        { name  : 'name_Like'   , xtype : 'textfield' , fieldLabel : '名称'}
   		];
	}
	
});
