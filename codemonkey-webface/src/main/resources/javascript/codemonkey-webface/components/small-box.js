avalon.component('ms-small-box', {
    template: AvalonUtils.heredoc(function (){
    	/*
    	<div :class="'small-box ' + background">
			<div class="inner">
				<h3>{{ num }}</h3>
			    <p>{{ title }}</p>
			</div>
			<div class="icon">
				<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
			</div>
			<a :href="link" class="small-box-footer">{{ linkText }} <i :class="linkIcon"></i></a>
  		</div>
    	*/
    }),
	defaults:{
		background: 'bg-aqua',
		num: 150,
		title: 'New Orders',
		icon : 'fa fa-envelope-open-o',
		link: '#',
		linkText : 'More info',
		linkIcon : 'fa fa-arrow-circle-right'
	}
});
