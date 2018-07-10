Ext.define('AM.chart.PieChart', {
	
	extend : 'AM.chart.PolarChart',
	
    addPie : function(xField , yField , override){
    	var me = this;
    	var c = {
	        type: 'pie',
	        angleField: yField,
	        showInLegend: true,
	        label: {
	            field: xField,
	            display: 'rotate',
	            contrast: true,
	            font: '18px Arial'
	        },
	        highlight: true,
	        tooltip: {
                trackMouse: true,
                renderer: function (tooltip, record, item) {
                    tooltip.setHtml(record.get(xField) + ': ' + record.get(yField) + '%');
                }
            }
    	};
    	
    	Ext.apply(c , override);
    	
    	return c;
    }
	
});