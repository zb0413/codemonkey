avalon.component('ms-messages-menu', {
    template: AvalonUtils.heredoc(function (){
        /*
		<li class="dropdown messages-menu">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">
				<i class="fa fa-envelope-o"></i>
				<span class="label label-success">{{ num }}</span>
			</a>
			<ul class="dropdown-menu">
				<li class="header">{{ header }}</li>
				<li>
					<ul class="menu">
						<li ms-for="msg in messages">
							<a ms-attr="{href : msg.link}">
								<div class="pull-left">
									<img ms-attr="{src : msg.img}" class="img-circle" alt="User Image">
								</div>
								<h4>
									{{ msg.title }}
									<small><i class="fa fa-clock-o"></i> {{ msg.time }} </small>
								</h4>
								<p>{{ msg.desc }}</p>
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
		header : 'you have 4 meesages',
		num : 4,
		footer : 'View All Messages',
		footerLink : '#',
		messages : [
            {img : '/webjars/AdminLTE/2.3.8/dist/img/user2-160x160.jpg', title:'Support Team' , time : '5 min' , desc : 'Why not buy a new awesome theme?' , link : '#'}
        ]
	}	
});
