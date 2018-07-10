Ext.define('AM.chart.BaseChart', {
	
	extend : 'Ext.panel.Panel',
	
	chart : null,
	
	layout : 'fit',
	
	modelName : 'ExampleData',
	
	xField : 'data1',
	
	yField : 'data2',
	
	searchParams : Ext.emptyFn,
	
	legend : {
		position : 'right'
	},
	
	modelFields : function(){
		
		if(this.dataFields){
			return Ext.Array.merge([this.xField , this.yField] , this.dataFields);
		}
		
		return [this.xField , this.yField];
    	
    },
	
	constructor : function(config) {
		
		var me = this;
		
		Ext.apply(this , config);
    	
		var modelFields = me.modelFields();
		
		if(!me.store){
			me.store = ExtUtils.ajaxStore({
				modelName : me.modelName,
				modelFields : modelFields,
				jsonUrl : me.jsonUrl || NS.url(me.modelName)
			},{
				autoLoad : true,
	    		listeners : {
	    			'beforeload' : function( cmp , operation, eOpts ){
	    				var proxy = cmp.getProxy();
	    	    		Ext.apply(proxy.extraParams , ExtUtils.buildQueryInfo(me.searchParams()));
	    			}
	    		}
			});
		}
    	
        this.axes = this.buildAxes();
        
    	this.series = this.buildSeries();
    	
    	this.chart = this.createChart();
    	
    	this.items = [this.chart];
    	
    	this.callParent();
		
    }
	
});