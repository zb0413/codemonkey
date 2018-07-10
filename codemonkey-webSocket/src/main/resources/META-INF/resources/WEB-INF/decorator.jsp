<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>${projectName}</title>
		
		<script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.min.js"></script>
		<script type="text/javascript" src="${ctx}/webjars/underscorejs/1.8.3/underscore-min.js"></script>
		
		<sitemesh:write property='head' />
		<script type="text/javascript">
		    var PAGE_DATA = ${pageData};
		    var CTX = "${ctx}";
		</script>
		
	</head>
	<body>
		<sitemesh:write property='body' />
	</body>

</html>
