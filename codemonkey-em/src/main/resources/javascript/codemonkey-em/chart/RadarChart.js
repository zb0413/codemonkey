Ext.define('AM.chart.RadarChart', {
	
	extend : 'AM.chart.PolarChart',
	
    addRadar : function(xField , yField , override){
    	
    	var c = {
	        type: 'radar',
	        xField: xField,
	        yField: yField
    	};
    	
    	Ext.apply(c , override);
    	
    	return c;
    }
	
});