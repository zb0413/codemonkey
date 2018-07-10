Ext.define('AM.chart.BarChart', {
	
	extend : 'AM.chart.XYChart',
	
	addBar : function(xField , yField , override){
		
    	var me = this;
    	var s = {
    		type : 'bar',
    		xField: xField,
            yField: yField,
            stacked: false,
            tips : {
				trackMouse : true,
				width : 130,
				renderer : function (tooltip, record, item) {
			        var fieldIndex = Ext.Array.indexOf(item.series.getYField(), item.field);
			        tooltip.setHtml(item.field + ': ' + record.get(item.field));
			    }
			},
			style: {
                opacity: 0.80
            },
            highlight: {
                fillStyle: 'yellow'
            },
	            
			label : {
				display : 'inside',
				field : yField,
				renderer : Ext.util.Format.numberRenderer('0'),
				orientation : 'vertical',
				color : '#333',
				'text-anchor' : 'middle'
			}
    	};
    	
    	Ext.apply(s , override);
    	
    	return s;
    }
	
});