//dependency jquery.plot
avalon.component('ms-plot', {
    template: AvalonUtils.heredoc(function (){
        /*
			<div style="height:180px;width:180px;"></div>
		*/
    }),
	defaults:{
		index : 1,
		values : [],
		commingValue : null,
		onReady : function(){
			this.drawChart();
			this.$watch("commingValue" , function(newVal , oldVal){
				this.updateSpeed(newVal);
			});
		},
		
		updateSpeed : function(speed){
			var max = 100;
			if(this.values.length < max){
				this.values.push(speed);
			}else{
				this.values = _.rest(this.values);
				this.values.push(speed);
			}
			
			var res = [];
			for(var i = 0 ; i < this.values.length ; i++){
				res.push([i , this.values[i]]); 
			}
			this.interactive_plot.setData([res]);
			this.interactive_plot.draw();
		},
		
		drawChart : function(){
			this.interactive_plot = $.plot($(this.$element) , [this.commingValue], {
				grid  : {
					borderColor: '#f3f3f3',
					borderWidth: 1,
					tickColor  : '#f3f3f3'
				},
				series: {
					shadowSize: 0, // Drawing is faster without shadows
					color : '#3c8dbc'
				},
				lines : {
					fill : true, //Converts the line chart to area chart
					color: '#3c8dbc'
				},
				yaxis : {
					min : 0,
					max : 1500,
					show: true
				},
				xaxis : {
					min : 0,
					max : 30,
					show: true
				}
			});
			this.interactive_plot.draw();
		}
		
	}
});