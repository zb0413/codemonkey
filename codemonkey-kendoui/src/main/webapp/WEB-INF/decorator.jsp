<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Layout</title>
		
		<link href="/css/kendoui.professional.2016.1.412/content/shared/examples-offline.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.common.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.rtl.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.default.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.dataviz.min.css" rel="stylesheet">
		
		<link href="/css/codemonkey-kendoui/codemonkey-kendoui.css" rel="stylesheet">
		<!-- 
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/jquery.min.js"></script>
		 -->
		<script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.js"></script>
		
		<script type="text/javascript" src="${ctx}/webjars/underscorejs/1.8.3/underscore-min.js"></script>
		
		 
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/angular.min.js"></script>
		<script type="text/javascript" src="/javascript/codemonkey-kendoui/kendo.all.js"></script>
		 <!-- 
		 <script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.all.min.js"></script>
		 -->
		<script type="text/javascript" src="/javascript/codemonkey-kendoui/KendoUtils.js"></script>

		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/cultures/kendo.culture.zh-CN.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/messages/kendo.messages.zh-CN.min.js"></script>  
		
		<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/css/bootstrap.min.css"/>
		<script type="text/javascript" src="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		
		<link rel="stylesheet" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css"/>
		
		<sitemesh:write property='head' />
		<script type="text/javascript">
		    var PAGE_DATA = ${pageData};
		    var CTX = "${ctx}";
		    
		    kendo.culture("zh-CN"); 
		</script>
	</head>
	<body>
		<sitemesh:write property='body' />
	</body>

</html>
