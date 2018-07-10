avalon.component('ms-user-panel', {
    template: AvalonUtils.heredoc(function (){
        /*
		<div class="user-panel">
			<div class="pull-left image">
				<img ms-attr="{src: img}" class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>{{ name }}</p>
				<a href="#"><i class="fa fa-circle text-success"></i> {{ status }}</a>
			</div>
		</div>
		*/
    }),
	defaults : {
		img : '/webjars/AdminLTE/2.3.8/dist/img/user2-160x160.jpg',
		name : 'Alexander Pierce',
		status : 'online'
	}
});
