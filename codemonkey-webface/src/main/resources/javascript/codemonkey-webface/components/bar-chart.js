avalon.component('ms-bar-chart', {
    template: AvalonUtils.heredoc(function (){
        /*
			<div class="chart"></div>
		*/
    }),
	defaults:{
		onReady : function(){
			var bar = new Morris.Bar({
				element: this.$element,
				resize: this.resize,
				data : this.data,
				barColors : this.barColors,
				xkey: this.xkey,
				ykeys: this.ykeys,
				labels: this.labels,
				hideHover: this.hideHover
			});
		},
		resize: true,
		data: [
			{y: '2006', a: 100, b: 90},
			{y: '2007', a: 75, b: 65},
			{y: '2008', a: 50, b: 40},
			{y: '2009', a: 75, b: 65},
			{y: '2010', a: 50, b: 40},
			{y: '2011', a: 75, b: 65},
			{y: '2012', a: 100, b: 90}
		 ],
		 barColors: ['#00a65a', '#f56954'],
		 xkey: 'y',
		 ykeys: ['a', 'b'],
		 labels: ['CPU', 'DISK'],
		 hideHover: 'auto'
	}
});
