//基础设置-科室信息 表单
Ext.define('codemonkey-cms.view.ArticleFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'Article',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
				 { name  : 'name'   , xtype : 'textfield' , fieldLabel : '标题' , allowBlank : false  , colspan : 100 , width : 545},
				 { name  : 'category'   , xtype : 'enumselect' , packageName:'com.codemonkey.domain.cms', className : 'ArticleCategory', fieldLabel : '用途'  , allowBlank : false , colspan : 50 },
		         { name  : 'publishedDate'   , xtype : 'datefield' , fieldLabel : '发布时间' , allowBlank : false , colspan : 50 },
		         { name  : 'menu'   , xtype : 'entityselect' , modelName : 'menuList' ,fieldLabel : '所属栏目' , colspan : 50 , allowBlank : false},
		         { name  : 'status'   , xtype : 'enumselect' , packageName:'com.codemonkey.domain.cms', className : 'ArticleSatus', fieldLabel : '发布状态'  , allowBlank : false ,  colspan : 50 } ,
		         { name : 'thumb' , xtype : 'popupselect' , listClass : "zhd-university-web.view.UploadEntryListView" , fieldLabel : '文件/图片' , colspan : 50 },
		         { name  : 'topFlag'   , xtype : 'enumselect' , className : 'BasicTypeIf', fieldLabel : '是否置顶' , colspan : 50 },
		         { name  : 'keyWord'   , xtype : 'textarea' , fieldLabel : '简要描述' , colspan : 100 , width : 545},
		         { name  : 'description'   , xtype : 'textarea' , fieldLabel : '内容' , colspan : 50 , hidden:true , maxLength : 9999}
        ]);
		
		var p3 = ExtUtils.panel({
					itemId : 'contentId', 
					html:'good'
				},{
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
    	var el = Ext.query('.x-autocontainer-innerCt' , false , content.el);
    	if(el && el[0]){
    		el[0].update(value);
    	}
    },
    
    startEditDescription : function(){
    	var me = this;
    	var field = me.getForm().findField('description');
    	var value = field.getValue() ? field.getValue() : '';
    	ExtUtils.win(
			[{
				xtype : 'htmleditor' , 
				value : value,
				plugins: [new Ext.create('AM.plugins.ImageUpload', {
					submitUrl:'/cms/uploadFile/create',
					referenceUrl : '/cms/uploadFile/download?uploadEntry='
				})]
			}],
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