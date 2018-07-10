<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<head>
<title>${projectName}</title>

<script type="text/javascript" src="${r"${ctx}"}/webjars/jquery/1.11.1/jquery.js"></script>

<link rel="stylesheet" href="${r"${ctx}"}/webjars/bootstrap/3.3.4/css/bootstrap.css"/>
<script type="text/javascript" src="${r"${ctx}"}/webjars/bootstrap/3.3.4/js/bootstrap.js"></script>


<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<link rel="stylesheet" href="${r"${ctx}"}/css/bootstrap-custom/sticky-footer-navbar.css"/>

<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<!--<script type="text/javascript" src="${r"${ctx}"}/javascript/bootstrap-custom/ie-emulation-modes-warning.js"></script>-->

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script type="text/javascript" src="${r"${ctx}"}/javascript/bootstrap-custom/ie10-viewport-bug-workaround.js"></script>

<link rel="stylesheet" href="${r"${ctx}"}/css/bootstrap-custom/login.css"/>

</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top header">
		<div class="container">
			<a class="navbar-brand text3D">${projectName}</a>
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
							<div class="login_error">${r"${errorMsg}"}</div>
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