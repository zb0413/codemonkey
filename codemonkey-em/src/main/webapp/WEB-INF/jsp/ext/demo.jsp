<!DOCTYPE html>

<body>
	<div layout:fragment="script">
		<script type="text/javascript">
			Ext.application({
				name: 'Demo',
			    appFolder : CTX + '/javascript/example/demo',
			    autoCreateViewport: true
			});
		</script>
	</div>
	<div layout:fragment="title">
		<h3>demo</h3>
	</div>
</body>
