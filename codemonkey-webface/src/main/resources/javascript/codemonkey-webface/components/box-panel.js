avalon.component('ms-box-panel', {
    template: AvalonUtils.heredoc(function (){
        /*
        <div ms-class="'box ' + theme">
			<div class="box-header">
				<i ms-class="icon"></i>
				<h3 class="box-title">{{ title }}</h3>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" data-widget="collapse">
						<i class="fa fa-minus"></i>
	                </button>
	                <button type="button" class="btn btn-box-tool" data-widget="remove">
	                	<i class="fa fa-times"></i>
	                </button>
				</div>
			</div>
			<div class="box-body">
			    <slot name="body"/>
			</div>
			<div class="box-footer clearfix no-border">
				<slot name="footer"/>
			</div>
		</div>
		*/
    }),
    
    defaults : {
    	icon : 'fa fa-envelope-o',
    	title : '标题',
    	theme : 'box-primary'
    }
});

