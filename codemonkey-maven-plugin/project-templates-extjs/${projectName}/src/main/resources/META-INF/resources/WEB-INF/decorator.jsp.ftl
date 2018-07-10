<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Layout</title>
		
		<!--  raphael -->
		<script type="text/javascript" src="${r"${ctx}"}/webjars/raphaeljs/2.1.2/raphael.js"></script>
		
		<script type="text/javascript">
		    var PAGE_DATA = ${r"${pageData}"};
		    var CTX = "${r"${ctx}"}";
		</script>
		<!-- 
		<script type="text/javascript" src="${r"${ctx}"}/javascript/codemonkey-em/codemonkey-ext-includes2.js?debug=false&ext_locale=zh_CN&version=4.2.3.1477"></script>
		-->
		<script type="text/javascript" src="${r"${ctx}"}/javascript/codemonkey-em/codemonkey-ext-includes2.js?debug=false&ext_locale=zh_CN&version=6.0.0&theme=triton"></script>
		<script type="text/javascript" src="${r"${ctx}"}/javascript/codemonkey-em/codemonkey-em.js"></script>
		<sitemesh:write property='head' />
		
	</head>
	<body>
		<sitemesh:write property='body' />
	</body>
</html>
