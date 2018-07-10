avalon.component('ms-donut-chart', {
    template: AvalonUtils.heredoc(function (){
        /*
			<div class="chart"></div>
		*/
    }),
	defaults:{
		onReady : function(){
			var donut = new Morris.Donut({
				element: this.$element,
				resize: this.resize,
				colors: this.colors,
				data: this.data,
				hideHover: this.hideHover
			});
		},
		resize: true,
		colors: ["#3c8dbc", "#f56954", "#00a65a"],
		data: [
			{label: "Download Sales", value: 12},
			{label: "In-Store Sales", value: 30},
			{label: "Mail-Order Sales", value: 20}
		],
		hideHover: 'auto'
	}
});
