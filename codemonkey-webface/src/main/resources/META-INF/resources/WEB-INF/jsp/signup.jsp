<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<head>
<title>codemonkey-training-web</title>

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top header">
		<div class="container">
			<a class="navbar-brand text3D">codemonkey-training-web</a>
		</div>
	</nav>

	<div class="container">
		<div class="row">
			<div class="col-md-7">
				<div class="login_pictures_picture"
					style="background-image: url('https://res.mail.qq.com/zh_CN/htmledition/images/tg-smile1dccf6.jpg'); background-position: 234px 130px;">
				</div>
			</div>
			<div class="col-md-5">
				<div class="panel panel-default login-panel vertical-middle-sm">
					<div class="panel-heading">
						<h3 class="form-signin-heading">登录</h3>
					</div>
					<div class="panel-body">
						<form class="form-signin">
							<div class="form-group">
								<label for="username" class="sr-only">用户名</label> <input
									type="text" name="username" class="form-control"
									placeholder="用户名" />
							</div>
							<div class="form-group">
								<label for="inputPassword" class="sr-only">密码</label> <input
									type="password" name="password" class="form-control"
									placeholder="Password" />
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