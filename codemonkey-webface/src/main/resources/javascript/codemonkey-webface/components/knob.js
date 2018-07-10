//dependency jquery.knob
avalon.component('ms-knob', {
    template: AvalonUtils.heredoc(function (){
        /*
			<input type="text" readOnly="true" class="knob" ms-attr="{value:value}" data-max="26500" data-width="180" data-height="180" data-fgColor="#3c8dbc">
		*/
    }),
	defaults:{
		value : 20,
		min:0,
		max:25600,
		config : {},
		onReady : function(){
			$(this.$element).knob(this.config).val(this.value);
		}
	}
});
