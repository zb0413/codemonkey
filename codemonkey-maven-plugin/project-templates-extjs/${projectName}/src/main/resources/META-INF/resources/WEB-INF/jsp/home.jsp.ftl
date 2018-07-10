<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<head>
 	<title>${projectName}</title>
</head>
<body>
	<div>
		<script type="text/javascript">
			Ext.Loader.setConfig({
				enabled : true,
				paths : {
					'AM' : AM_ROOT,
					'Ext.ux' : EXAMPLE_ROOT
				}
			});
		
			Ext.application({
				name: '${projectName}',
			    appFolder : '../javascript/${projectName}',
			    autoCreateViewport: true
			});
		</script>
	</div>
</body>