<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<body>
	<script src="/javascript/codemonkey-webface/components/breadcrumb.js"></script>
	<script src="/javascript/codemonkey-webface/components/small-box.js"></script>
	<script src="/javascript/codemonkey-webface/components/box-panel.js"></script>
	<script src="/javascript/codemonkey-webface/components/tab-panel.js"></script>
	<script src="/javascript/codemonkey-webface/components/area-chart.js"></script>
	<script src="/javascript/codemonkey-webface/components/donut-chart.js"></script>
	<script src="/javascript/codemonkey-webface/components/bar-chart.js"></script>
	<script src="/javascript/codemonkey-webface/components/line-chart.js"></script>
	<script src="/javascript/codemonkey-webface/components/calendar.js"></script>
	
	<script type="text/javascript">
		var vm = avalon.define({
		    $id: 'widget1',
	    	title : '主板',
	    	subtitle : '副标题',
		    breadcrumb : [
			  {style : 'active' , href:'#' , icon:'fa fa-dashboard' , text:'home1-1'},
			  {style : '' , href:'#' , icon:'' , text:'dashboard-1'}
			],
			smallBoxes : [
           		{num : 150 , title : 'New Orders' , background : 'bg-aqua'},
           		{num : '53%' , title : 'Bounce Rate' , background : 'bg-green'},
           		{num : 44 , title : 'User Registrations' , background : 'bg-yellow'},
           		{num : 65 , title : 'Unique Visitors' , background : 'bg-red'}
           	]
		});
		
		$(document).ready(function(){
			var area = new Morris.Area({
				element: 'revenue-chart',
				resize: true,
				data: [
					{y: '2011 Q1', item1: 2666, item2: 2666},
					{y: '2011 Q2', item1: 2778, item2: 2294},
					{y: '2011 Q3', item1: 4912, item2: 1969},
					{y: '2011 Q4', item1: 3767, item2: 3597},
					{y: '2012 Q1', item1: 6810, item2: 1914},
					{y: '2012 Q2', item1: 5670, item2: 4293},
					{y: '2012 Q3', item1: 4820, item2: 3795},
					{y: '2012 Q4', item1: 15073, item2: 5967},
					{y: '2013 Q1', item1: 10687, item2: 4460},
					{y: '2013 Q2', item1: 8432, item2: 5713}
				],
				xkey: 'y',
				ykeys: ['item1', 'item2'],
				labels: ['Item 1', 'Item 2'],
				lineColors: ['#a0d0e0', '#3c8dbc'],
				hideHover: 'auto'
			});
			
			//Donut Chart
			var donut = new Morris.Donut({
				element: 'sales-chart',
				resize: true,
				colors: ["#3c8dbc", "#f56954", "#00a65a"],
				data: [
					{label: "Download Sales", value: 12},
					{label: "In-Store Sales", value: 30},
					{label: "Mail-Order Sales", value: 20}
				],
				hideHover: 'auto'
			});
		});
		
	</script>
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<div ms-controller='widget1' >
			<section class="content-header">
				<h1>{{@title}}<small>{{@subtitle}}</small></h1>
				<xmp is="ms-breadcrumb" ms-widget="{breadcrumb : @breadcrumb}"></xmp>
			</section>
			<section class="content">
			    <div class="row">
			    	<div ms-for="box in @smallBoxes" class="col-lg-3 col-xs-6">
						<xmp is="ms-small-box" ms-widget="box"></xmp>
					</div>
					
					<xmp is="ms-area-chart"></xmp>
					<xmp is="ms-donut-chart"></xmp>
					<xmp is="ms-bar-chart"></xmp>
					<xmp is="ms-line-chart"></xmp>
					<div class="box box-solid bg-teal-gradient">
						<xmp is="ms-calendar"></xmp>
					</div>
			    </div>
			    
			    <xmp is="ms-tab-panel">
			    	<div slot="tab1">
			    	
			    	</div>
			    </xmp>
			    
		    	<div class="chart tab-pane active" id="revenue-chart" style="position: relative; height: 300px;"></div>
				<div class="chart tab-pane" id="sales-chart" style="position: relative; height: 300px;"></div>
			    
			    <xmp is="ms-box-panel">
			    	<div slot="tools">
			    		<div class="btn-group" data-toggle="btn-toggle">
							<button type="button" class="btn btn-default btn-sm active">
								<i class="fa fa-square text-green"></i>
							</button>
							<button type="button" class="btn btn-default btn-sm">
								<i class="fa fa-square text-red"></i>
							</button>
						</div>
						<div class="btn-group">
							<button type="button" class="btn btn-info btn-sm" data-widget="collapse" data-toggle="tooltip" title="Collapse" style="margin-right: 5px;">
								<i class="fa fa-minus"></i>
			                </button>
							<button type="button" class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove">
			                	<i class="fa fa-times"></i>
			                </button>
		                </div>
			    	</div>
			    	<div slot="body">
			    	
			    	</div>
			    	<div slot="footer">
						<div class="input-group">
							<input class="form-control" placeholder="Type message...">
							<div class="input-group-btn">
								<button type="button" class="btn btn-success"><i class="fa fa-plus"></i></button>
							</div>
						</div>
			    	</div>
			    </xmp>
		    </section>
	    </div>
	  <!-- Main content -->
	  <section class="content">
	    <!-- Main row -->
	    <div class="row">
	    
	      <!-- Left col -->
	      <section class="col-lg-7 connectedSortable">
	        <!-- Custom tabs (Charts with tabs)-->
	        <div class="nav-tabs-custom">
	          <!-- Tabs within a box -->
	          <ul class="nav nav-tabs pull-right">
	            <li class="active"><a href="#revenue-chart" data-toggle="tab">Area</a></li>
	            <li><a href="#sales-chart" data-toggle="tab">Donut</a></li>
	            <li class="pull-left header"><i class="fa fa-inbox"></i> Sales</li>
	          </ul>
	          <div class="tab-content no-padding">
	            <!-- Morris chart - Sales -->
	            <div class="chart tab-pane active" id="revenue-chart" style="position: relative; height: 300px;"></div>
	            <div class="chart tab-pane" id="sales-chart" style="position: relative; height: 300px;"></div>
	          </div>
	        </div>
	        <!-- /.nav-tabs-custom -->
	
	        <!-- Chat box -->
	        <div class="box box-success">
	          <div class="box-header">
	            <i class="fa fa-comments-o"></i>
	
	            <h3 class="box-title">Chat</h3>
	
	            <div class="box-tools pull-right" data-toggle="tooltip" title="Status">
	              <div class="btn-group" data-toggle="btn-toggle">
	                <button type="button" class="btn btn-default btn-sm active"><i class="fa fa-square text-green"></i>
	                </button>
	                <button type="button" class="btn btn-default btn-sm"><i class="fa fa-square text-red"></i></button>
	              </div>
	            </div>
	          </div>
	          <div class="box-body chat" id="chat-box">
	            <!-- chat item -->
	            <div class="item">
	              <img src="/webjars/AdminLTE/2.3.8/dist/img/user4-128x128.jpg" alt="user image" class="online">
	
	              <p class="message">
	                <a href="#" class="name">
	                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 2:15</small>
	                  Mike Doe
	                </a>
	                I would like to meet you to discuss the latest news about
	                the arrival of the new theme. They say it is going to be one the
	                best themes on the market
	              </p>
	              <div class="attachment">
	                <h4>Attachments:</h4>
	
	                <p class="filename">
	                  Theme-thumbnail-image.jpg
	                </p>
	
	                <div class="pull-right">
	                  <button type="button" class="btn btn-primary btn-sm btn-flat">Open</button>
	                </div>
	              </div>
	              <!-- /.attachment -->
	            </div>
	            <!-- /.item -->
	            <!-- chat item -->
	            <div class="item">
	              <img src="/webjars/AdminLTE/2.3.8/dist/img/user3-128x128.jpg" alt="user image" class="offline">
	
	              <p class="message">
	                <a href="#" class="name">
	                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:15</small>
	                  Alexander Pierce
	                </a>
	                I would like to meet you to discuss the latest news about
	                the arrival of the new theme. They say it is going to be one the
	                best themes on the market
	              </p>
	            </div>
	            <!-- /.item -->
	            <!-- chat item -->
	            <div class="item">
	              <img src="/webjars/AdminLTE/2.3.8/dist/img/user2-160x160.jpg" alt="user image" class="offline">
	
	              <p class="message">
	                <a href="#" class="name">
	                  <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> 5:30</small>
	                  Susan Doe
	                </a>
	                I would like to meet you to discuss the latest news about
	                the arrival of the new theme. They say it is going to be one the
	                best themes on the market
	              </p>
	            </div>
	            <!-- /.item -->
	          </div>
	          <!-- /.chat -->
	          <div class="box-footer">
	            <div class="input-group">
	              <input class="form-control" placeholder="Type message...">
	
	              <div class="input-group-btn">
	                <button type="button" class="btn btn-success"><i class="fa fa-plus"></i></button>
	              </div>
	            </div>
	          </div>
	        </div>
	        <!-- /.box (chat box) -->
	
	        <!-- TO DO List -->
	        <div class="box box-primary">
	          <div class="box-header">
	            <i class="ion ion-clipboard"></i>
	
	            <h3 class="box-title">To Do List</h3>
	
	            <div class="box-tools pull-right">
	              <ul class="pagination pagination-sm inline">
	                <li><a href="#">&laquo;</a></li>
	                <li><a href="#">1</a></li>
	                <li><a href="#">2</a></li>
	                <li><a href="#">3</a></li>
	                <li><a href="#">&raquo;</a></li>
	              </ul>
	            </div>
	          </div>
	          <!-- /.box-header -->
	          <div class="box-body">
	            <ul class="todo-list">
	              <li>
	                <!-- drag handle -->
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <!-- checkbox -->
	                <input type="checkbox" value="">
	                <!-- todo text -->
	                <span class="text">Design a nice theme</span>
	                <!-- Emphasis label -->
	                <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
	                <!-- General tools such as edit or delete-->
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	              <li>
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <input type="checkbox" value="">
	                <span class="text">Make the theme responsive</span>
	                <small class="label label-info"><i class="fa fa-clock-o"></i> 4 hours</small>
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	              <li>
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <input type="checkbox" value="">
	                <span class="text">Let theme shine like a star</span>
	                <small class="label label-warning"><i class="fa fa-clock-o"></i> 1 day</small>
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	              <li>
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <input type="checkbox" value="">
	                <span class="text">Let theme shine like a star</span>
	                <small class="label label-success"><i class="fa fa-clock-o"></i> 3 days</small>
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	              <li>
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <input type="checkbox" value="">
	                <span class="text">Check your messages and notifications</span>
	                <small class="label label-primary"><i class="fa fa-clock-o"></i> 1 week</small>
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	              <li>
	                    <span class="handle">
	                      <i class="fa fa-ellipsis-v"></i>
	                      <i class="fa fa-ellipsis-v"></i>
	                    </span>
	                <input type="checkbox" value="">
	                <span class="text">Let theme shine like a star</span>
	                <small class="label label-default"><i class="fa fa-clock-o"></i> 1 month</small>
	                <div class="tools">
	                  <i class="fa fa-edit"></i>
	                  <i class="fa fa-trash-o"></i>
	                </div>
	              </li>
	            </ul>
	          </div>
	          <!-- /.box-body -->
	          <div class="box-footer clearfix no-border">
	            <button type="button" class="btn btn-default pull-right"><i class="fa fa-plus"></i> Add item</button>
	          </div>
	        </div>
	        <!-- /.box -->
	
	        <!-- quick email widget -->
	        <div class="box box-info">
	          <div class="box-header">
	            <i class="fa fa-envelope"></i>
	
	            <h3 class="box-title">Quick Email</h3>
	            <!-- tools box -->
	            <div class="pull-right box-tools">
	              <button type="button" class="btn btn-info btn-sm" data-widget="remove" data-toggle="tooltip" title="Remove">
	                <i class="fa fa-times"></i></button>
	            </div>
	            <!-- /. tools -->
	          </div>
	          <div class="box-body">
	            <form action="#" method="post">
	              <div class="form-group">
	                <input type="email" class="form-control" name="emailto" placeholder="Email to:">
	              </div>
	              <div class="form-group">
	                <input type="text" class="form-control" name="subject" placeholder="Subject">
	              </div>
	              <div>
	                <textarea class="textarea" placeholder="Message" style="width: 100%; height: 125px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
	              </div>
	            </form>
	          </div>
	          <div class="box-footer clearfix">
	            <button type="button" class="pull-right btn btn-default" id="sendEmail">Send
	              <i class="fa fa-arrow-circle-right"></i></button>
	          </div>
	        </div>
	
	      </section>
	      <!-- /.Left col -->
	      <!-- right col (We are only adding the ID to make the widgets sortable)-->
	      <section class="col-lg-5 connectedSortable">
	
	        <!-- Map box -->
	        <div class="box box-solid bg-light-blue-gradient">
	          <div class="box-header">
	            <!-- tools box -->
	            <div class="pull-right box-tools">
	              <button type="button" class="btn btn-primary btn-sm daterange pull-right" data-toggle="tooltip" title="Date range">
	                <i class="fa fa-calendar"></i></button>
	              <button type="button" class="btn btn-primary btn-sm pull-right" data-widget="collapse" data-toggle="tooltip" title="Collapse" style="margin-right: 5px;">
	                <i class="fa fa-minus"></i></button>
	            </div>
	            <!-- /. tools -->
	
	            <i class="fa fa-map-marker"></i>
	
	            <h3 class="box-title">
	              Visitors
	            </h3>
	          </div>
	          <div class="box-body">
	            <div id="world-map" style="height: 250px; width: 100%;"></div>
	          </div>
	          <!-- /.box-body-->
	          <div class="box-footer no-border">
	            <div class="row">
	              <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
	                <div id="sparkline-1"></div>
	                <div class="knob-label">Visitors</div>
	              </div>
	              <!-- ./col -->
	              <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
	                <div id="sparkline-2"></div>
	                <div class="knob-label">Online</div>
	              </div>
	              <!-- ./col -->
	              <div class="col-xs-4 text-center">
	                <div id="sparkline-3"></div>
	                <div class="knob-label">Exists</div>
	              </div>
	              <!-- ./col -->
	            </div>
	            <!-- /.row -->
	          </div>
	        </div>
	        <!-- /.box -->
	
	        <!-- solid sales graph -->
	        <div class="box box-solid bg-teal-gradient">
	          <div class="box-header">
	            <i class="fa fa-th"></i>
	
	            <h3 class="box-title">Sales Graph</h3>
	
	            <div class="box-tools pull-right">
	              <button type="button" class="btn bg-teal btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>
	              </button>
	              <button type="button" class="btn bg-teal btn-sm" data-widget="remove"><i class="fa fa-times"></i>
	              </button>
	            </div>
	          </div>
	          <div class="box-body border-radius-none">
	            <div class="chart" id="line-chart" style="height: 250px;"></div>
	          </div>
	          <!-- /.box-body -->
	          <div class="box-footer no-border">
	            <div class="row">
	              <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
	                <input type="text" class="knob" data-readonly="true" value="20" data-width="60" data-height="60" data-fgColor="#39CCCC">
	
	                <div class="knob-label">Mail-Orders</div>
	              </div>
	              <!-- ./col -->
	              <div class="col-xs-4 text-center" style="border-right: 1px solid #f4f4f4">
	                <input type="text" class="knob" data-readonly="true" value="50" data-width="60" data-height="60" data-fgColor="#39CCCC">
	
	                <div class="knob-label">Online</div>
	              </div>
	              <!-- ./col -->
	              <div class="col-xs-4 text-center">
	                <input type="text" class="knob" data-readonly="true" value="30" data-width="60" data-height="60" data-fgColor="#39CCCC">
	
	                <div class="knob-label">In-Store</div>
	              </div>
	              <!-- ./col -->
	            </div>
	            <!-- /.row -->
	          </div>
	          <!-- /.box-footer -->
	        </div>
	        <!-- /.box -->
	
	        <!-- Calendar -->
	        <div class="box box-solid bg-green-gradient">
	          <div class="box-header">
	            <i class="fa fa-calendar"></i>
	
	            <h3 class="box-title">Calendar</h3>
	            <!-- tools box -->
	            <div class="pull-right box-tools">
	              <!-- button with a dropdown -->
	              <div class="btn-group">
	                <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown">
	                  <i class="fa fa-bars"></i></button>
	                <ul class="dropdown-menu pull-right" role="menu">
	                  <li><a href="#">Add new event</a></li>
	                  <li><a href="#">Clear events</a></li>
	                  <li class="divider"></li>
	                  <li><a href="#">View calendar</a></li>
	                </ul>
	              </div>
	              <button type="button" class="btn btn-success btn-sm" data-widget="collapse"><i class="fa fa-minus"></i>
	              </button>
	              <button type="button" class="btn btn-success btn-sm" data-widget="remove"><i class="fa fa-times"></i>
	              </button>
	            </div>
	            <!-- /. tools -->
	          </div>
	          <!-- /.box-header -->
	          <div class="box-body no-padding">
	            <!--The calendar -->
	            <div id="calendar" style="width: 100%"></div>
	          </div>
	          <!-- /.box-body -->
	          <div class="box-footer text-black">
	            <div class="row">
	              <div class="col-sm-6">
	                <!-- Progress bars -->
	                <div class="clearfix">
	                  <span class="pull-left">Task #1</span>
	                  <small class="pull-right">90%</small>
	                </div>
	                <div class="progress xs">
	                  <div class="progress-bar progress-bar-green" style="width: 90%;"></div>
	                </div>
	
	                <div class="clearfix">
	                  <span class="pull-left">Task #2</span>
	                  <small class="pull-right">70%</small>
	                </div>
	                <div class="progress xs">
	                  <div class="progress-bar progress-bar-green" style="width: 70%;"></div>
	                </div>
	              </div>
	              <!-- /.col -->
	              <div class="col-sm-6">
	                <div class="clearfix">
	                  <span class="pull-left">Task #3</span>
	                  <small class="pull-right">60%</small>
	                </div>
	                <div class="progress xs">
	                  <div class="progress-bar progress-bar-green" style="width: 60%;"></div>
	                </div>
	
	                <div class="clearfix">
	                  <span class="pull-left">Task #4</span>
	                  <small class="pull-right">40%</small>
	                </div>
	                <div class="progress xs">
	                  <div class="progress-bar progress-bar-green" style="width: 40%;"></div>
	                </div>
	              </div>
	              <!-- /.col -->
	            </div>
	            <!-- /.row -->
	          </div>
	        </div>
	        <!-- /.box -->
	
	      </section>
	      <!-- right col -->
	    </div>
	    <!-- /.row (main row) -->
	
	  </section>
	  <!-- /.content -->
	</div>
	<!-- /.content-wrapper -->
	
	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark">
	  <!-- Create the tabs -->
	  <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
	    <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
	    <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
	  </ul>
	  <!-- Tab panes -->
	  <div class="tab-content">
	    <!-- Home tab content -->
	    <div class="tab-pane" id="control-sidebar-home-tab">
	      <h3 class="control-sidebar-heading">Recent Activity</h3>
	      <ul class="control-sidebar-menu">
	        <li>
	          <a href="javascript:void(0)">
	            <i class="menu-icon fa fa-birthday-cake bg-red"></i>
	
	            <div class="menu-info">
	              <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
	
	              <p>Will be 23 on April 24th</p>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <i class="menu-icon fa fa-user bg-yellow"></i>
	
	            <div class="menu-info">
	              <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>
	
	              <p>New phone +1(800)555-1234</p>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
	
	            <div class="menu-info">
	              <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>
	
	              <p>nora@example.com</p>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <i class="menu-icon fa fa-file-code-o bg-green"></i>
	
	            <div class="menu-info">
	              <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>
	
	              <p>Execution time 5 seconds</p>
	            </div>
	          </a>
	        </li>
	      </ul>
	      <!-- /.control-sidebar-menu -->
	
	      <h3 class="control-sidebar-heading">Tasks Progress</h3>
	      <ul class="control-sidebar-menu">
	        <li>
	          <a href="javascript:void(0)">
	            <h4 class="control-sidebar-subheading">
	              Custom Template Design
	              <span class="label label-danger pull-right">70%</span>
	            </h4>
	
	            <div class="progress progress-xxs">
	              <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <h4 class="control-sidebar-subheading">
	              Update Resume
	              <span class="label label-success pull-right">95%</span>
	            </h4>
	
	            <div class="progress progress-xxs">
	              <div class="progress-bar progress-bar-success" style="width: 95%"></div>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <h4 class="control-sidebar-subheading">
	              Laravel Integration
	              <span class="label label-warning pull-right">50%</span>
	            </h4>
	
	            <div class="progress progress-xxs">
	              <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
	            </div>
	          </a>
	        </li>
	        <li>
	          <a href="javascript:void(0)">
	            <h4 class="control-sidebar-subheading">
	              Back End Framework
	              <span class="label label-primary pull-right">68%</span>
	            </h4>
	
	            <div class="progress progress-xxs">
	              <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
	            </div>
	          </a>
	        </li>
	      </ul>
	      <!-- /.control-sidebar-menu -->
	
	    </div>
	    <!-- /.tab-pane -->
	    <!-- Stats tab content -->
	    <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
	    <!-- /.tab-pane -->
	    <!-- Settings tab content -->
	    <div class="tab-pane" id="control-sidebar-settings-tab">
	      <form method="post">
	        <h3 class="control-sidebar-heading">General Settings</h3>
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Report panel usage
	            <input type="checkbox" class="pull-right" checked>
	          </label>
	
	          <p>
	            Some information about this general settings option
	          </p>
	        </div>
	        <!-- /.form-group -->
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Allow mail redirect
	            <input type="checkbox" class="pull-right" checked>
	          </label>
	
	          <p>
	            Other sets of options are available
	          </p>
	        </div>
	        <!-- /.form-group -->
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Expose author name in posts
	            <input type="checkbox" class="pull-right" checked>
	          </label>
	
	          <p>
	            Allow the user to show his name in blog posts
	          </p>
	        </div>
	        <!-- /.form-group -->
	
	        <h3 class="control-sidebar-heading">Chat Settings</h3>
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Show me as online
	            <input type="checkbox" class="pull-right" checked>
	          </label>
	        </div>
	        <!-- /.form-group -->
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Turn off notifications
	            <input type="checkbox" class="pull-right">
	          </label>
	        </div>
	        <!-- /.form-group -->
	
	        <div class="form-group">
	          <label class="control-sidebar-subheading">
	            Delete chat history
	            <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
	          </label>
	        </div>
	        <!-- /.form-group -->
	      </form>
	    </div>
	    <!-- /.tab-pane -->
	  </div>
	</aside>
	<!-- /.control-sidebar -->
	<!-- Add the sidebar's background. This div must be placed
	     immediately after the control sidebar -->
	<div class="control-sidebar-bg"></div>
</body>