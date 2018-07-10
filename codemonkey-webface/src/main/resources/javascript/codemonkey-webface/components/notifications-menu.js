avalon.component('ms-notifications-menu', {
    template: AvalonUtils.heredoc(function (){
        /*
		<li class="dropdown notifications-menu">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<i class="fa fa-bell-o"></i>
				<span class="label label-warning">{{ num }}</span>
			</a>
			<ul class="dropdown-menu">
				<li class="header">You have {{ num }} notifications</li>
				<li>
					<ul class="menu">
						<li ms-for="n in notifications">
							<a ms-attr="{href : n.link}">
								<i :class="n.icon"></i> {{ n.title }}
							</a>
						</li>
					</ul>
				</li>
				<li class="footer"><a ms-attr="{href : footerLink}" >{{ footer }}</a></li>
			</ul>
		</li>
		*/
    }),
	defaults : {
		num : 4,
		footer : 'View all',
		footerLink : '#',
		notifications : [
            {icon : 'fa fa-users text-aqua', title:'5 new members joined today'  , link : '#'}
        ]
	}	
});
