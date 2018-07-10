Ext.define('AM.chart.LineChart', {
	
	extend : 'AM.chart.XYChart',
	
	addLine : function(yField , override){
    	return this.addSeries('line' , yField , override);
    }	
});