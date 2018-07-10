//招聘管理
Ext.define('codemonkey-cms.view.PostInfoFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'PostInfo',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
				 { name  : 'name'   , xtype : 'textfield' , fieldLabel : '标题' , allowBlank : false  , colspan : 100 , width : 545},
		         { name  : 'publishedDate'   , xtype : 'datefield' , fieldLabel : '发布时间' , allowBlank : false , colspan : 50 },
		         { name  : 'status'   , xtype : 'enumselect' , packageName:'com.codemonkey.domain.cms', className : 'ArticleSatus', fieldLabel : '发布状态'  , allowBlank : false ,  colspan : 100 ,disabled : true } ,
		         { name  : 'keyWord'   , xtype : 'textfield' , fieldLabel : '简要描述' , colspan : 100 , width : 545},
		         { name  : 'description'   , xtype : 'textarea' , fieldLabel : '内容' , colspan : 50 , hidden:true}
        ]);
		
		var p3 = ExtUtils.panel(
				{html:''},
				{
					itemId : 'contentId' , 
					title : '内容' , 
					height : 150 , 
					listeners : {
						afterrender : function( p , eOpts ){
							Ext.EventManager.on(p.body , 'click' , function(event , target , options){
								me.startEditDescription();
							});
						}
					}
				}
		);
		
		
    	return [p2 , p1 , p3];
    },
    
    afterModelLoad : function(result){
    	var me = this;
    	this.callParent([result]);
    	this.updateContentPanel();
    },
    
    updateContentPanel : function(){
    	var me = this;
    	var field = this.getForm().findField('description');
    	var value = field.getValue() ? field.getValue() : '';
    	var content = me.down('#contentId');
    	content.body.update(value);
    },
    
    startEditDescription : function(){
    	var me = this;
    	var field = me.getForm().findField('description');
    	var value = field.getValue() ? field.getValue() : '';
    	ExtUtils.win(
			[{xtype : 'htmleditor' , value : value}],
			{
				title: '编辑',
				modal : false,
				buttons : [{
	        	   text : '确定',
	        	   handler : function(){
	        		   var win = this.up('window');
	        		   var htmleditor = win.down('htmleditor');
	        		   field.setValue(htmleditor.getValue());
	        		   me.updateContentPanel();
	        		   win.close();
	        	   }
	           },{
	        	   text : '取消',
	        	   handler : function(){
	        		   this.up('window').close();
	        	   }
	           }]
			});
    }
});