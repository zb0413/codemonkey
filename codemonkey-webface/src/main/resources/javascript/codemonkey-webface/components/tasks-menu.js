avalon.component('ms-tasks-menu', {
    template: AvalonUtils.heredoc(function (){
        /*
		<li class="dropdown tasks-menu">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<i class="fa fa-flag-o"></i>
				<span class="label label-danger">{{ num }}</span>
			</a>
			<ul class="dropdown-menu">
				<li class="header">You have {{ num }} tasks</li>
				<li ms-for="t in tasks">
					<ul class="menu">
						<li ms-for="t in tasks">
							<a href="#">
								<h3> {{ t.title }} <small class="pull-right">{{ t.percent }}</small></h3>
								<div class="progress xs">
									<div class="progress-bar progress-bar-aqua" ms-css="{width: t.percent}" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
										<span class="sr-only">{{ t.percent }} Complete</span>
									</div>
								</div>
							</a>
						</li>
					</ul>
				</li>
				<li class="footer">
					<a ms-attr="{href : footerLink}" >{{ footer }}</a>
				</li>
			</ul>
		</li>
		*/
    }),
	defaults : {
		num : 4,
		footer : 'View all',
		footerLink : '#',
		tasks : [
            {icon : 'fa fa-users text-aqua', title:'5 new members joined today'  , percent : '20%' , link : '#'}
        ]
	}	
});
