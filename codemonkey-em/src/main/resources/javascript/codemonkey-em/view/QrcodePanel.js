Ext.define('AM.view.QrcodePanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.qrcodepanel',
	
	//title : '二维码',
	
	defaultQrcodeConfig : {
	    text: "i am codemonkey",
	    width: 128,
	    height: 128,
	    colorDark : "#000000",
	    colorLight : "#ffffff",
	    correctLevel : QRCode.CorrectLevel.H
	},
	
	qrcodeConfig : {},
	
	constructor : function(config) {
		
		var me = this;
		
		this.qrcodeDivId = 'qrcodeDivId' + Ext.id();
		
		var c = {
			
			html : '<div id="' + this.qrcodeDivId + '"/>',
				
			listeners : {
				'afterrender' : function(){
					
					var c = me.defaultQrcodeConfig;
					
					Ext.apply(c , me.qrcodeConfig);
					
					this.paper = new QRCode(this.qrcodeDivId , c);
					this.pageLayout();
				}
			}
		};
		
		Ext.apply(c , config);
		
		Ext.apply(this , c);
		
		this.callParent();
		
	},
	
	pageLayout : function(){
		
	}
	
});