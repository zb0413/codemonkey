Ext.define('AM.view.AppUserFormView', {
    extend: 'AM.view.AppEditPanel',
    
    /**常量**/
    modelName : 'AppUser',
	/**自定义常量**/
    
    /**实现父类方法**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
            	{ xtype:"textfield"	, name:"username" , labelWidth : 80 , fieldLabel:'用户名', allowBlank : false , labelWidth : 80 , colspan : 100},
            	{ xtype:"textfield"	, name:"name" , labelWidth : 80 , fieldLabel:'姓名', allowBlank : false , labelWidth : 80 , colspan : 100},
            	{ xtype :"entityselect", name:"appUserGroup" , labelWidth : 80, modelName : 'AppUserGroupList' , fieldLabel :"用户组" , allowBlank : false , labelWidth : 80 , colspan : 100},
            	{ xtype :"enumselect", name:"status" , labelWidth : 80, className : 'Status' ,fieldLabel :"状态" , allowBlank : false , labelWidth : 80 , colspan : 100}
	       ]
	       );
		
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
 			selModel: Ext.create('Ext.selection.CheckboxModel', {
 				mode : 'SINGLE',
 				allowDeselect : 'true',
 				checkOnly : true
 			}) 
 		});
        
    	return [p2 , p1 , appRolesGrid];
    },
    
    /**覆盖父类方法**/
    beforeSave : function(values){
    	var me = this;
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
	doResetPassword : function(){
		
		var id = this.getEntityId();
		if(!id){
			Ext.Msg.alert('错误' , '请先选择一条用户信息！');
			return;
		}
		var view = Ext.create('zhd-university-web.view.ResetPasswordFormView' , {
			readParams : {id : id}
		});
		
		ExtUtils.win(view , {title : '重置密码' , width : 300 , height : 300 , modal : true});
		
	}
    
    /**自定义页面行为**/
    
    /**自定义页面行为响应**/
});