<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
        $scope.barcode = "Cheese";
    });
	</script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<div id="tickets">
				<form id="ticketsForm">
					<ul id="fieldlist">
						<li><input ng-model="barcode" class="k-textbox"> </li>
						<li><label>对应条形码</label> <span kendo-barcode="" k-type="'code128'" ng-model="barcode"></span></li>
					</ul>
				</form>
			</div>
		</div>
</body>