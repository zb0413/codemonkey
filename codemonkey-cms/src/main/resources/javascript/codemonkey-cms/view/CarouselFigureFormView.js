//轮播图管理
Ext.define('codemonkey-cms.view.CarouselFigureFormView', {
    extend: 'AM.view.AppEditPanel',
    
    requires: ['AM.view.AppEditPanel'],
    
    /**常量**/
    modelName : 'CarouselFigure',
	/**自定义常量**/
    
    formItems : function(){
    	var me = this;
		var p1 = ExtUtils.creationInfoPanel();
		
		var p2 = ExtUtils.panel([
		                          {name : "picture" , xtype : 'popupselect' , listClass : "zhd-university-web.view.UploadEntryListView" , fieldLabel : '图片' , colspan : 100, searchParams : {'fileType' : '图片'} }
		                         ]);
		
    	return [p2 , p1];
    }
});