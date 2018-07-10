<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<head>
	<title>医院协会报名</title>
	<script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.min.js"></script>
	<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/css/bootstrap.min.css"/>
	<script type="text/javascript" src="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<div class="panel panel-default login-panel vertical-middle-sm">
					<div class="panel-heading">
						<h3 class="form-signin-heading">登录</h3>
					</div>
					<div class="panel-body">
						<form class="form-signin" action="/auth/login" method="post">
							<div class="form-group">
								<label for="username" class="sr-only">用户名</label>
								<input type="text" name="username" class="form-control" placeholder="用户名" />
							</div>
							<div class="form-group">
								<label for="inputPassword" class="sr-only">密码</label> 
								<input type="password" name="password" class="form-control" placeholder="密码" />
							</div>
							<div class="form-group">
								<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
							</div>
							<div class="login_error">${errorMsg}</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /.container -->
	<div class="footer">
		<div class="container">
			<p class="text-muted text-center">
				<span class="gray">©1998 - 2014 . All Rights Reserved.</span>
			</p>
		</div>
	</div>
</body>
</html>