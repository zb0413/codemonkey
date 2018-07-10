Ext.define('AM.chart.GaugeChart', {
	
	extend : 'AM.chart.PolarChart',
	
    addGauge : function(field , override){
    	var c = {
    		type : 'gauge',
    		donut: 30,
    		angleField: field
    	};
    	
    	Ext.apply(c , override);
    	
    	return c;
    }
	
});