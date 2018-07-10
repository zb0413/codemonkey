//最新人才
Ext.define('codemonkey-cms.view.TalentFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'Talent',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.tableLayout([
				 { name  : 'name'   , xtype : 'textfield' , fieldLabel : '标题' , allowBlank : false  , readOnly : true , colspan : 100 , width : 545},
		         { name  : 'publishedDate'   , xtype : 'datefield' , fieldLabel : '发布时间' , allowBlank : false , readOnly : true , colspan : 50 }
        ]);
        
        var p3 = ExtUtils.tableLayout([
		         { name  : 'keyWord'   , xtype : 'textfield' , fieldLabel : '简要描述' , readOnly : true , colspan : 100 , width : 545},
		         { name  : 'description'   , xtype : 'textarea' , fieldLabel : '内容' , colspan : 100 , readOnly : true , hidden:true}
        ]);
		
		var p4 = ExtUtils.panel(
				{html:''},
				{
					itemId : 'contentId' , 
					title : '内容' , 
					height : 150 , 
					listeners : {}
				}
		);
		
		
    	return [p2 , p1 , p3, p4];
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
    }
});