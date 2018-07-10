Ext.define('AM.chart.PolarChart', {
	
	extend : 'AM.chart.BaseChart',
	
    createChart : function(){
    	var c = {
			store : this.store,
			axes : this.axes,
			series : this.series,
			builder : this
    	};
    	return Ext.create('Ext.chart.PolarChart' , c);
    },
	
    buildSeries : function(){
		return null;
    },
    
	buildAxes : function(){
    	return [{
	       type: 'numeric',
	       position: 'left',
	       fields: [this.yField],
	       title: {
	           text: this.yTitle,
	           fontSize: 15
	       },
	       grid: true,
	       minimum: 0
	   }, {
	       type: 'category',
	       position: 'bottom',
	       fields: [this.xField],
	       title: {
	           text: this.xTitle,
	           fontSize: 15
	       }
	   }];
    },
        
    addSeries : function(config , override){
    	var me = this;
    	var s = config;
    	Ext.apply(s , override);
    	return s;
    }
	
});