avalon.component('ms-user-menu', {
    template: AvalonUtils.heredoc(function (){
        /*
		<li class="dropdown user user-menu">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<img ms-attr="{src: img}" class="user-image" alt="User Image">
				<span class="hidden-xs">{{ name }}</span>
			</a>
			<ul class="dropdown-menu">
				<li class="user-header">
					<img ms-attr="{src: img}" class="img-circle" alt="User Image">
					<p>{{ name }} <small>{{ desc }}</small></p>
				</li>
				<li class="user-footer">
					<div class="pull-left">
						<a ms-attr="{href: profileLink}" class="btn btn-default btn-flat">{{ profile }}</a>
					</div>
					<div class="pull-right">
						<a ms-attr="{href: signoutLink}" class="btn btn-default btn-flat">{{ signout }}</a>
					</div>
				</li>
			</ul>
		</li>
		*/
    }),
	defaults : {
		profile : 'Profile',
		profileLink : '#',
		
		signout : 'Sign out',
		signoutLink : '#',
		img : '/webjars/AdminLTE/2.3.8/dist/img/user2-160x160.jpg',
		name : 'Alexander Pierce',
		desc : 'Member since Nov. 2012'
	}	
});
