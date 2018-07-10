<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<head>
 	<title>yyxh-baoming-web</title>
	<link href="${ctx}/css/AdminLTE-2.1.1/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/css/AdminLTE-2.1.1/css/skins/skin-blue.min.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/javascript/AdminLTE-2.1.1/app.min.js" type="text/javascript"></script>
</head>
<div class="skin-blue" ng-app="KendoDemos">
	<script type="text/javascript">
		angular.module("KendoDemos", [ "kendo.directives" ])
			.controller("MyCtrl", function($scope){
				$scope.functionNodes = PAGE_DATA.functionNodes;
				$scope.currentFunctionUrl = PAGE_DATA.functionNodes[0].url;
				
				$scope.functionNodeOnClick = function(fn){
					var url = fn.url;
					var time = (new Date()).getTime();
					if(url.indexOf('?') > 0){
						$scope.currentFunctionUrl = fn.url+"&dc=" + time;
					}else{
						$scope.currentFunctionUrl = fn.url+"?dc=" + time;
					}
				};
				
				$scope.clickModifyPassword = function(){
					$scope.showChangePassword = true;
				};
				
				$scope.doChangePassword = function(){
					if($scope.password != $scope.confirmPassword){
						alert('输入密码不一致');
						return;
					}
					
					$.post('/auth/changePassword' , {needOldPassword : false , password: $scope.password} , function(result){
						$scope.showChangePassword = false;
						$scope.$apply();
					},"json");
					
				};
				
				$scope.logout = function(){
					window.location = "/auth/logout";
				}
			});
	</script>
	<div ng-controller="MyCtrl" class="wrapper">
		<header class="main-header">
			<!-- Logo -->
			<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>A</b>LT</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>Admin</b>LTE</span>
			</a>

			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"> 
					<span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<li>
			            	<a href="#" data-toggle="control-sidebar">
			            		<i class="fa fa-gears"></i>
			            	</a>
			            </li>
					</ul>	
				</div>
			</nav>
		</header>
		
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
						<p>管理员</p>

						<a href="#" ng-click="clickModifyPassword()">
							<i class="fa fa-circle text-success"></i>修改密码
						</a>
						<a href="#" ng-click="logout()">
							<i class="fa fa-circle text-success"></i>退出
						</a>
					</div>
					<div ng-show="showChangePassword">
						<div class="form-group">
							<input type="password" ng-model="password" class="form-control" placeholder="密码" />
							<input type="password" ng-model="confirmPassword" class="form-control" placeholder="确认密码" />
							<button class="btn btn-sm btn-primary" ng-click="doChangePassword()">修改</button>
						</div>
					</div>
				</div>
				
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li class="header">导航</li>
					<li ng-repeat="fn in functionNodes"  class="active treeview">
						<a href="#" ng-click="functionNodeOnClick(fn)">
							<i class="fa fa-dashboard"></i>
							<span>{{fn.name}}</span> 
							<i class="fa fa-angle-left pull-right"></i>
						</a>
					</li>
				</ul>	
			</section>
			<!-- /.sidebar -->
		</aside>
		
		<div class="content-wrapper">
			<section class="content">
				<iframe ng-src="{{currentFunctionUrl}}" seamless frameborder="0"></iframe >
			</section>
		</div>
		
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 2.0
			</div>
			<strong>Copyright &copy; 2014-2015 <a
				href="http://almsaeedstudio.com">Almsaeed Studio</a>.
			</strong> All rights reserved.
		</footer>
	</div>
</div>