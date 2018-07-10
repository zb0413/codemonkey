Ext.define('AM.chart.AreaChart', {
	
	extend : 'AM.chart.XYChart',
	
	addArea : function(yField , override){
		
		var c = {
	    	highlight: false,
	        style: {
	            opacity: 0.93
	        }
	    };
		Ext.apply(c , override);
		
		return this.addSeries('area' , yField , c);
	}
	
});