Ext.define('AM.chart.ScatterChart', {
	
	extend : 'AM.chart.XYChart',
	
    addScatter : function(yField , override){
    	
    	var c = {
			type : 'scatter',
			markerConfig : {
				radius : 5,
				size : 5
			},
			axis : 'left',
			xField : this.xField,
			yField : yField
		};
		
		Ext.apply(c , override)
    	
    	return c;
    }
	
});