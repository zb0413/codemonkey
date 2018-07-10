avalon.component('ms-calendar', {
    template: AvalonUtils.heredoc(function (){
        /*
			<div></div>
		*/
    }),
	defaults:{
		onReady : function(){
			$(this.$element).datepicker();
		}
	}	
});
