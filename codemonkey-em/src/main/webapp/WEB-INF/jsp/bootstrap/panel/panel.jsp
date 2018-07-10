<%@ page contentType="text/html;charset=UTF-8"%>
<div class="col-md-3 col-sm-6 col-xs-12">
	<div class="info-box">
		<span class="info-box-icon bg-green"><i class="fa fa-flag-o"></i></span>
		<div class="info-box-content">
			<span class="info-box-text">Bookmarks</span> 
			<span class="info-box-number">410</span>
		</div>
		<!-- /.info-box-content -->
	</div>

	<div class="info-box bg-aqua">
		<span class="info-box-icon"><i class="fa fa-bookmark-o"></i></span>
		<div class="info-box-content">
			<span class="info-box-text">Bookmarks</span> <span
				class="info-box-number">41,410</span>
			<div class="progress">
				<div class="progress-bar" style="width: 70%"></div>
			</div>
			<span class="progress-description"> 70% Increase in 30 Days </span>
		</div>
		<!-- /.info-box-content -->
	</div>

	<div class="small-box bg-yellow">
		<div class="inner">
			<h3>44</h3>
			<p>User Registrations</p>
		</div>
		<div class="icon">
			<i class="ion ion-person-add"></i>
		</div>
		<a href="#" class="small-box-footer"> More info <i
			class="fa fa-arrow-circle-right"></i>
		</a>
	</div>
</div>

<div class="col-md-3 col-sm-6 col-xs-12">
	<div class="box box-danger box-solid">
		<div class="box-header with-border">
			<h3 class="box-title">Collapsable</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
			<!-- /.box-tools -->
		</div>
		<!-- /.box-header -->
		<div class="box-body" style="display: block;">The body of the
			box</div>
		<!-- /.box-body -->
	</div>

	<div class="box box-success">
		<div class="box-header with-border">
			<h3 class="box-title">Removable</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-times"></i>
				</button>
			</div>
			<!-- /.box-tools -->
		</div>
		<!-- /.box-header -->
		<div class="box-body">The body of the box</div>
		<!-- /.box-body -->
	</div>

	<div class="box box-default box-solid">
		<div class="box-header with-border">
			<h3 class="box-title">Expandable</h3>
			<div class="box-tools pull-right">
				<button class="btn btn-box-tool" data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
			</div>
			<!-- /.box-tools -->
		</div>
		<!-- /.box-header -->
		<div class="box-body" style="display: block;">The body of the
			box</div>
		<!-- /.box-body -->
	</div>
</div>

<div class="col-md-3 col-sm-6 col-xs-12">
	<div class="box box-danger box-solid">
		<div class="box-header">
			<h3 class="box-title">Loading state</h3>
		</div>
		<div class="box-body">The body of the box</div>
		<!-- /.box-body -->
		<!-- Loading (remove the following to stop the loading)-->
		<div class="overlay">
			<i class="fa fa-refresh fa-spin"></i>
		</div>
		<!-- end loading -->
	</div>

	<!-- DIRECT CHAT -->
	<div class="box box-success direct-chat direct-chat-success">
		<div class="box-header with-border">
			<h3 class="box-title">Direct Chat</h3>
			<div class="box-tools pull-right">
				<span data-toggle="tooltip" title="" class="badge bg-green"
					data-original-title="3 New Messages">3</span>
				<button class="btn btn-box-tool" data-widget="collapse">
					<i class="fa fa-minus"></i>
				</button>
				<button class="btn btn-box-tool" data-toggle="tooltip"
					title="Contacts" data-widget="chat-pane-toggle">
					<i class="fa fa-comments"></i>
				</button>
				<button class="btn btn-box-tool" data-widget="remove">
					<i class="fa fa-times"></i>
				</button>
			</div>
		</div>
		<!-- /.box-header -->
		<div class="box-body">
			<!-- Conversations are loaded here -->
			<div class="direct-chat-messages">
				<!-- Message. Default to the left -->
				<div class="direct-chat-msg">
					<div class="direct-chat-info clearfix">
						<span class="direct-chat-name pull-left">Alexander Pierce</span> 
						<span class="direct-chat-timestamp pull-right">23 Jan 2:00 pm</span>
					</div>
					<!-- /.direct-chat-info -->
					<img class="direct-chat-img" src="../dist/img/user1-128x128.jpg" alt="message user image">
					<!-- /.direct-chat-img -->
					<div class="direct-chat-text">
						Is this template really for
						free? That's unbelievable!
					</div>
					<!-- /.direct-chat-text -->
				</div>
				<!-- /.direct-chat-msg -->

				<!-- Message to the right -->
				<div class="direct-chat-msg right">
					<div class="direct-chat-info clearfix">
						<span class="direct-chat-name pull-right">Sarah Bullock</span> 
						<span class="direct-chat-timestamp pull-left">23 Jan 2:05 pm</span>
					</div>
					<!-- /.direct-chat-info -->
					<img class="direct-chat-img" src="../dist/img/user3-128x128.jpg"
						alt="message user image">
					<!-- /.direct-chat-img -->
					<div class="direct-chat-text">You better believe it!</div>
					<!-- /.direct-chat-text -->
				</div>
				<!-- /.direct-chat-msg -->
			</div>
			<!--/.direct-chat-messages-->

			<!-- Contacts are loaded here -->
			<div class="direct-chat-contacts">
				<ul class="contacts-list">
					<li>
						<a href="#"> 
							<img class="contacts-list-img" src="../dist/img/user1-128x128.jpg"/>
							<div class="contacts-list-info">
								<span class="contacts-list-name"> Count Dracula 
									<small class="contacts-list-date pull-right">2/28/2015</small>
								</span> 
								<span class="contacts-list-msg">
									How have you been? I was...
								</span>
							</div>
							<!-- /.contacts-list-info -->
						</a>
					</li>
					<!-- End Contact Item -->
				</ul>
				<!-- /.contatcts-list -->
			</div>
			<!-- /.direct-chat-pane -->
		</div>
		<!-- /.box-body -->
		<div class="box-footer">
			<form action="#" method="post">
				<div class="input-group">
					<input type="text" name="message" placeholder="Type Message ..."
						class="form-control"> <span class="input-group-btn">
						<button type="button" class="btn btn-success btn-flat">Send</button>
					</span>
				</div>
			</form>
		</div>
		<!-- /.box-footer-->
	</div>
	<!-- end DIRECT CHAT -->
</div>
