<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=2.0">
		<meta http-equiv="pragma" content="no-cache">
		
		<title>项目管理</title>
		<link rel="shortcut icon" type="image/ico" href="http://www.datatables.net/favicon.ico">
		
		<script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.js"></script>
		
		<link rel="stylesheet" href="${ctx}/webjars/bootstrap/3.2.0/css/bootstrap.css"/>
		<script type="text/javascript" src="${ctx}/webjars/bootstrap/3.2.0/js/bootstrap.js"></script>
		
		<script type="text/javascript" src="${ctx}/webjars/underscorejs/1.8.3/underscore-min.js"></script>
		
		<!-- adminLTE -->
		<link href="${ctx}/css/AdminLTE-2.1.1/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
	    <!-- AdminLTE Skins. Choose a skin from the css/skins 
	         folder instead of downloading all of them to reduce the load. -->
	    <link href="${ctx}/css/AdminLTE-2.1.1/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />
	    
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/app.min.js" type="text/javascript"></script>
	    <!-- AdminLTE for demo purposes -->
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/demo.js" type="text/javascript"></script>
	    
	    <!-- DATA TABES SCRIPT -->
	    <link href="${ctx}/javascript/AdminLTE-2.1.1/plugins/datatables/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/datatables/jquery.dataTables.js" type="text/javascript"></script>
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/datatables/dataTables.bootstrap.min.js" type="text/javascript"></script>
	    <!-- SlimScroll -->
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	    <!-- FastClick -->
	    <script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/fastclick/fastclick.min.js"></script>
	
	    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	        <script src="${ctx}/webjars/html5shiv/3.7.2/html5shiv.min.js"></script>
	        <script src="${ctx}/webjars/respond/1.4.2/dest/respond.min.js"></script>
	    <![endif]-->
    	<link href="${ctx}/javascript/AdminLTE-2.1.1/plugins/datepicker/datepicker3.css" rel="stylesheet" type="text/css" />
    	<script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/datepicker/bootstrap-datepicker.js"></script>
    	<script src="${ctx}/javascript/AdminLTE-2.1.1/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
    	
    	<link rel="stylesheet" href="${ctx}/webjars/ionicons/2.0.1/css/ionicons.min.css"/>
    	<link rel="stylesheet" href="${ctx}/webjars/font-awesome/4.3.0/css/font-awesome.min.css"/>
    	<!-- end adminLTE -->
		
		<link href="${ctx}/css/bootstrap-custom/bootstrap-custom.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript" src="${ctx}/javascript/codemonkey-jquery/CONSTANTS.js"></script>
		<script type="text/javascript" src="${ctx}/javascript/codemonkey-jquery/JQUtils.js"></script>
		<script type="text/javascript" src="${ctx}/javascript/codemonkey-jquery/NS.js"></script>
		<script type="text/javascript" src="${ctx}/javascript/codemonkey-jquery/FormPage.js"></script>
		<script type="text/javascript" src="${ctx}/javascript/codemonkey-jquery/ListPage.js"></script>
		
		<sitemesh:write property='head' />
	</head>
	<body class="skin-blue sidebar-mini">
		<input id="ctx" type="hidden" value="${ctx}">
		
		<script type="text/javascript">
			$(document).ready(function(){
				
				//调整页面高度
				adjustPage();
				
				//控件
				//日期
				$('input[inputType=datepicker]').datepicker({
				    format: 'yyyy-mm-dd',
				    language: 'zh-CN'
				}).attr('readOnly' , true);
				
				//实体选择控件
				var entityselect = $('select[inputType=entityselect]');
				entityselect.each(function(){
					var me = $(this);
					JQUtils.createEntitySelect(me);
				});
				
				//枚举选择控件
				var enumselect = $('select[inputType=enumselect]');
				enumselect.each(function(){
					var me = $(this);
					JQUtils.createEnumSelect(me);
				});
				
				//弹出列表
				var popupselect = $('input[inputType=popupselect]');
				popupselect.each(function(){
					var me = $(this);
					JQUtils.createPopupSelect(me);
				});
			});
			
			function adjustPage(){
				var bodyHeight = $('body').height();
				var footerHeight = $('.main-footer').height();
				var headerHeight = $('.main-header').height();
				
				var conntentHeight = bodyHeight - headerHeight - footerHeight + 10;
				$('.content-wrapper').height(conntentHeight);
			}
		</script>
		
		
		<div class="wrapper">
	
			<header class="main-header">
	
				<!-- Logo -->
				<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
					<span class="logo-mini"><b>A</b>LT</span> <!-- logo for regular state and mobile devices -->
					<span class="logo-lg"><b>Admin</b>LTE</span>
				</a>
	
				<!-- Header Navbar: style can be found in header.less -->
				<nav class="navbar navbar-static-top" role="navigation">
					<!-- Sidebar toggle button-->
					<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
						role="button"> <span class="sr-only">Toggle navigation</span>
					</a>
					<!-- Navbar Right Menu -->
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<li>
				            	<a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
				            </li>
						</ul>	
					</div>
				</nav>
			</header>
			<!-- Left side column. contains the logo and sidebar -->
			<aside class="main-sidebar">
				<!-- sidebar: style can be found in sidebar.less -->
				<section class="sidebar">
					<!-- Sidebar user panel -->
					<div class="user-panel">
						<div class="pull-left image">
							<img src="${ctx}/css/AdminLTE-2.1.1/img/user2-160x160.jpg" class="img-circle"
								alt="User Image" />
						</div>
						<div class="pull-left info">
							<p>Alexander Pierce</p>
	
							<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
						</div>
					</div>
					<!-- search form -->
					<form action="#" method="get" class="sidebar-form">
						<div class="input-group">
							<input type="text" name="q" class="form-control"
								placeholder="Search..." /> <span class="input-group-btn">
								<button type='submit' name='search' id='search-btn' class="btn btn-flat">
									<i class="fa fa-search"></i>
								</button>
							</span>
						</div>
					</form>
					<!-- /.search form -->
					<!-- sidebar menu: : style can be found in sidebar.less -->
					<ul class="sidebar-menu">
						<li class="header">MAIN NAVIGATION</li>
						<li class="active treeview">
							<a href="#"> 
								<i class="fa fa-dashboard"></i> 
								<span>图表</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li>
									<a href="/bootstrap/demo/charts/barChart">
										<i class="fa fa-circle-o"></i>
										柱状图
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/charts/pieChart">
										<i class="fa fa-circle-o"></i>
										饼状图
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/charts/lineChart">
										<i class="fa fa-circle-o"></i>
										折线图
									</a>
								</li>
							</ul>
							
						</li>
						
						<li class="active treeview">
							<a href="#"> 
								<i class="fa fa-dashboard"></i> 
								<span>日程表*</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/timeline/timeline"> 
								<i class="fa fa-dashboard"></i> 
								<span>时间线</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
						</li>
						<li class="active treeview">
							<a href="#"> 
								<i class="fa fa-dashboard"></i> 
								<span>日程表*</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
						</li>
						<li class="active treeview">
							<a href="#"> 
								<i class="fa fa-dashboard"></i> 
								<span>面板</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li>
									<a href="/bootstrap/demo/panel/panel">
										<i class="fa fa-circle-o"></i>
										面板
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/panel/color">
										<i class="fa fa-circle-o"></i>
										颜色
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/panel/alert">
										<i class="fa fa-circle-o"></i>
										alert
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/panel/tabs">
										<i class="fa fa-circle-o"></i>
										tabs
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/panel/progressBars">
										<i class="fa fa-circle-o"></i>
										进度条
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/panel/accordionCarousel">
										<i class="fa fa-circle-o"></i>
										Accordion & Carousel
									</a>
								</li>
							</ul>
							
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/table/tables"> 
								<i class="fa fa-dashboard"></i> 
								<span>表格</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li>
									<a href="/bootstrap/demo/table/simpleTables">
										<i class="fa fa-circle-o"></i>
										简单样式的表格
									</a>
								</li>
							</ul>	
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/modal/base"> 
								<i class="fa fa-dashboard"></i> 
								<span>弹出窗口</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li>
									<a href="/bootstrap/demo/modal/base">
										<i class="fa fa-circle-o"></i>
										弹出窗口基本实例
									</a>
								</li>
								<li>
									<a href="/bootstrap/demo/modal/modalJs">
										<i class="fa fa-circle-o"></i>
										用js控制弹出窗口
									</a>
								</li>
								
							</ul>	
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/form/elements"> 
								<i class="fa fa-dashboard"></i> 
								<span>form</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
							<ul class="treeview-menu">
								<li>
									<a href="/bootstrap/demo/form/elements">
										<i class="fa fa-circle-o"></i>
										form基本元素
									</a>
								</li>
								<li>
									<a href="/bootstrap/demo/form/advancedElements">
										<i class="fa fa-circle-o"></i>
										form高级元素
									</a>
								</li>
								<li class="active">
									<a href="/bootstrap/demo/form/layout">
										<i class="fa fa-circle-o"></i>
										form布局
									</a>
								</li>
							</ul>	
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/button/button"> 
								<i class="fa fa-dashboard"></i> 
								<span>按钮</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
						</li>
						<li class="active treeview">
							<a href="/bootstrap/demo/icons/icons"> 
								<i class="fa fa-dashboard"></i> 
								<span>图标</span> 
								<i class="fa fa-angle-left pull-right"></i>
							</a>
						</li>
					</ul>	
				</section>
				<!-- /.sidebar -->
			</aside>
	
			<!-- Content Wrapper. Contains page content -->
			<div class="content-wrapper">
				<!-- Content Header (Page header) -->
				<section class="content-header">
					<h1>
						Dashboard <small>Version 2.0</small>
					</h1>
					<ol class="breadcrumb">
						<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
						<li class="active">Dashboard</li>
					</ol>
				</section>
	
				<!-- Main content -->
				<section class="content">
					<sitemesh:write property='body' />
				</section>
				<!-- /.content -->
			</div>
			<!-- /.content-wrapper -->
	
			<footer class="main-footer">
				<div class="pull-right hidden-xs">
					<b>Version</b> 2.0
				</div>
				<strong>Copyright &copy; 2014-2015 <a
					href="http://almsaeedstudio.com">Almsaeed Studio</a>.
				</strong> All rights reserved.
			</footer>
	
		</div>
		<!-- ./wrapper -->
	</body>
</html>