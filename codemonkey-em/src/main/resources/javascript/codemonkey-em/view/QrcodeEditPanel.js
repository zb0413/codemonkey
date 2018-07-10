Ext.define('AM.view.QrcodeEditPanel', {
	
	extend: 'AM.view.QrcodePanel',
	
	alias: 'widget.qrcodeeditpanel',
	
	showEditor : true,
	
	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(this , config);
		
		if(this.showEditor){
			this.tbar = [{
	 		   xtype:"textfield" , fieldLabel:'输入值' , width : 600
			},{
			   text    : '更新',
			   handler : function() {
				  var text = this.up('toolbar').down('textfield');
				      var value = text.getValue();
					  me.updateQrcode(value);
				   }
			}];
		}
		
		this.callParent([config]);
		
	},
	
	updateQrcode : function(value){
		if(!value){
			return;
		}
		
		this.paper.clear();
		this.paper.makeCode(value);
	}
	
});