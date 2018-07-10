<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>医院绩效管理系统</title>
	</head>
	<body class="login-page">
		<div class="login-box">
			<div class="login-box-body">
				<div class="login-logo text-primary">
					<b>医院绩效评价信息平台</b>
				</div>
				<div>
					<p th:text="${errorMsg}"></p>
				</div>
				<form method="post">
					<div class="form-group has-feedback">
						<input type="text" name="username" class="form-control" placeholder="用户名" /> 
						<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
					</div>
					<div class="form-group has-feedback">
						<input type="password" name="password" class="form-control" placeholder="密码" />
						<span class="glyphicon glyphicon-lock form-control-feedback"></span>
					</div>
					<div class="row">
						<!-- /.col -->
						<div class="col-xs-4">
							<button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
						</div>
						<div class="col-xs-4">
							<button type="reset" class="btn btn-warning btn-block btn-flat">重置</button>
						</div>	
						<!-- /.col -->
					</div>
				</form>
			</div>
			<!-- /.login-box-body -->
		</div>
		<!-- /.login-box -->
	</body>
</html>