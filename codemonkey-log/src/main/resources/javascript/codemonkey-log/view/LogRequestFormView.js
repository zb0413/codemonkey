//操作日志-表单信息
Ext.define('codemonkey-log.view.LogRequestFormView', {
    extend: 'AM.view.AppEditPanel',
    
    /**常量**/
    modelName : 'LogRequest',
	/**自定义常量**/
    
    /**实现父类方法**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
    	var p2 = ExtUtils.tableLayout([
	            
	            {xtype:"textfield"	, name:"opUser_username" ,  fieldLabel:'操作人' , labelWidth : 80 , colspan : 50},
	            {xtype:"textfield"	, name:"opTime" ,  fieldLabel:'操作时间',  labelWidth : 80 , colspan : 50},
	            {xtype:"textfield"	, name:"result" , fieldLabel:'请求结果' , labelWidth : 80 , colspan : 100},
	            
	            {xtype:'textarea'  , name:'opClass' , fieldLabel:'操作类' , width : 565 , height : 30, colspan : 100},
	            
               	{xtype:'textfield'  , name:'clsMethod' , fieldLabel:'方法' , labelWidth : 80 , colspan : 50},
               	{xtype:'textfield'  , name:'clsMedDesc' , fieldLabel:'方法描述' , labelWidth : 80 , colspan : 50}
	            
          ],{
    		title : '操作日志详细信息',
    		height : 200 ,
    		collapsible : true
    		
    	});
    	var paramColumns = [
    				{dataIndex : 'id' , header: 'id' , hidden : true},
	       		    {dataIndex : 'param' , header: '参数'},
	       		    {dataIndex : "type" , flex:2 , header : '类型'},
	       		    {dataIndex : "value" , flex:2 , header : '值'}
    	];
    	var paramStore = ExtUtils.buildStoreByColumns( "LogRequest_param" , paramColumns);
    	
    	var paramGrid = ExtUtils.grid({
   			store : paramStore,
   			columns : paramColumns,
   			itemId : 'paramGridId',
   			title : '方法参数列表',
   			plugins : [{ 
				ptype: 'rowexpander',
				rowBodyTpl : new Ext.XTemplate('<p><b>{name}</b>类型：{type}<br>值:{value}</p>' )
	        }]
   		});
		
    	return [p2 , paramGrid , p1];
    },
    /**覆盖父类方法**/
    	afterModelLoad : function(model){
		this.callParent([model]);
   		var grid = this.down('#paramGridId');
   		grid.getStore().loadData(model.lines || []);
   	}
    /**自定义页面行为**/
	
    /**自定义页面行为响应**/
});