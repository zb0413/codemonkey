<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>web face</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<!-- Bootstrap 3.3.6 -->
		<link rel="stylesheet" href="/webjars/AdminLTE/2.3.8/bootstrap/css/bootstrap.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" href="/webjars/AdminLTE/2.3.8/dist/css/AdminLTE.min.css">
		<!-- AdminLTE Skins. Choose a skin from the css/skins
		     folder instead of downloading all of them to reduce the load. -->
		<link rel="stylesheet" href="/webjars/AdminLTE/2.3.8/dist/css/skins/_all-skins.min.css">
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="/webjars/font-awesome/4.5.0/css/font-awesome.min.css">
		
		
		<link rel="stylesheet" href="/css/codemonkey-webface/codemonkey-webface.css">
		
		
		<sitemesh:write property='head' />
		
		<!-- jQuery 2.2.3 -->
		<script src="/webjars/AdminLTE/2.3.8/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<!-- Bootstrap 3.3.6 -->
		<script src="/webjars/AdminLTE/2.3.8/bootstrap/js/bootstrap.min.js"></script>
		
		<!-- Morris chart -->
		<link rel="stylesheet" href="/webjars/AdminLTE/2.3.8/plugins/morris/morris.css">
		<script src="/webjars/raphaeljs/2.1.4/raphael-min.js"></script>
		<script src="/webjars/AdminLTE/2.3.8/plugins/morris/morris.min.js"></script>

		<!-- datepicker -->
		<link rel="stylesheet" href="/webjars/AdminLTE/2.3.8/plugins/datepicker/datepicker3.css">
		<script src="/webjars/AdminLTE/2.3.8/plugins/datepicker/bootstrap-datepicker.js"></script>

		<!-- AdminLTE App -->
		<script src="/webjars/AdminLTE/2.3.8/dist/js/app.min.js"></script>
		
		<script src="/javascript/avalon/2.2.4/avalon.js"></script>
		<script src="/javascript/codemonkey-webface/AvalonUtils.js"></script>
		
		<script src="/javascript/codemonkey-webface/components/messages-menu.js"></script>
		<script src="/javascript/codemonkey-webface/components/notifications-menu.js"></script>
		<script src="/javascript/codemonkey-webface/components/tasks-menu.js"></script>
		<script src="/javascript/codemonkey-webface/components/user-menu.js"></script>
		<script src="/javascript/codemonkey-webface/components/user-panel.js"></script>
		<script src="/javascript/codemonkey-webface/components/search-form.js"></script>
		<script src="/javascript/codemonkey-webface/components/navi-tree.js"></script>
		<script type="text/javascript">
			var vm = avalon.define({
			    $id: 'main-header',
			    messagesMenu : {
			    	header : 'you have 4 meesages',
					num : 4,
					footer : 'View All Messages',
					footerLink : '#',
					messages : [
			            {img : '/webjars/AdminLTE/2.3.8/dist/img/user2-160x160.jpg' , title:'Support Team' , time : '5 min' , desc : 'Why not buy a new awesome theme?' , link : '#'}
			        ]
			    }
			});
		</script>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<header ms-controller="main-header" class="main-header">
				<!-- Logo -->
				<a href="index2.html" class="logo">
					<!-- mini logo for sidebar mini 50x50 pixels -->
					<span class="logo-mini"><b>A</b>LT</span>
					<!-- logo for regular state and mobile devices -->
					<span class="logo-lg"><b>Admin</b>LTE</span>
				</a>
				<!-- Header Navbar: style can be found in header.less -->
				<nav class="navbar navbar-static-top">
					<!-- Sidebar toggle button-->
					<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
					  <span class="sr-only">Toggle navigation</span>
					</a>
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<xmp is="ms-messages-menu" ms-widget="@messagesMenu" ></xmp>
							<xmp is="ms-notifications-menu"></xmp>
							<xmp is="ms-tasks-menu"></xmp>
							<xmp is="ms-user-menu"></xmp>
							<li>
								<a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
							</li>
						</ul>
					</div>
				</nav>
			</header>	
			
			<aside class="main-sidebar">
				<section class="sidebar">
					<xmp is="ms-user-panel"></xmp>
					<!-- search form -->
					<xmp is="ms-search-form"></xmp>
					<ul class="sidebar-menu">
						<xmp is="ms-navi-tree"></xmp>
					</ul>
				</section>
				<!-- /.sidebar -->
			</aside>
			
			<sitemesh:write property='body' />
		
			<footer class="main-footer">
				<div class="pull-right hidden-xs">
				  <b>Version</b> 2.3.8
				</div>
				<strong>Copyright &copy; 2014-2016 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights
				 reserved.
			</footer>
		</div>	
	</body>
</html>
