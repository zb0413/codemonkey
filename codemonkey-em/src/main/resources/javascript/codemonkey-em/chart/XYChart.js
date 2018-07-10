Ext.define('AM.chart.XYChart', {
	
	extend : 'AM.chart.BaseChart',
	
    createChart : function(){
    	var c = {
			store : this.store,
			axes : this.axes,
			series : this.series,
			builder : this
    	};
    	return Ext.create('Ext.chart.CartesianChart' , c);
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
	       },
	       label: {
               rotate: {
                   degrees: -20
               }
           }
	   }];
    },
    
    addSeries : function(type , yField , override){
    	var me = this;
    	var s = {
    		type : type,
    		xField: this.xField,
            yField: yField,
            tooltip : {
				trackMouse : true,
				width : 130
				,
				renderer: function (storeItem, item , arg3 ) {
					storeItem.setHtml(arg3.field + ': ' + arg3.record.get(arg3.field));
				}
			},
			label : {
				display : 'outside',
				field : yField,
				renderer : Ext.util.Format.numberRenderer('0'),
				orientation : 'horizontal',
				color : '#333',
				'text-anchor' : 'middle'
			}
    	};
    	
    	Ext.apply(s , override);
    	
    	return s;
    }
	
});