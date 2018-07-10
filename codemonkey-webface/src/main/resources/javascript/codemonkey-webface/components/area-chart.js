avalon.component('ms-area-chart', {
    template: AvalonUtils.heredoc(function (){
        /*
			<div class="chart"></div>
		*/
    }),
	defaults:{
		onReady : function(){
			var area = new Morris.Area({
				element: this.$element,
				resize: this.resize,
				data : this.data,
				xkey: this.xkey,
				ykeys: this.ykeys,
				labels: this.labels,
				lineColors: this.lineColors,
				hideHover: this.hideHover
			});
		},
		resize: true,
		data: [
			{y: '2011 Q1', item1: 2666, item2: 2666},
			{y: '2011 Q2', item1: 2778, item2: 2294},
			{y: '2011 Q3', item1: 4912, item2: 1969},
			{y: '2011 Q4', item1: 3767, item2: 3597},
			{y: '2012 Q1', item1: 6810, item2: 1914},
			{y: '2012 Q2', item1: 5670, item2: 4293},
			{y: '2012 Q3', item1: 4820, item2: 3795},
			{y: '2012 Q4', item1: 15073, item2: 5967},
			{y: '2013 Q1', item1: 10687, item2: 4460},
			{y: '2013 Q2', item1: 8432, item2: 5713}
		],
		xkey: 'y',
		ykeys: ['item1', 'item2'],
		labels: ['Item 1', 'Item 2'],
		lineColors: ['#a0d0e0', '#3c8dbc'],
		hideHover: 'auto'
	}
});
