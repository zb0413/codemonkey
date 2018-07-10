<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
   	 $scope.selectOptions = {
			dataTextField: "ProductName",
           dataValueField: "ProductID",
           dataSource: KendoUtils.createDataSource({url: "/kendoui/multilist"})
	     };
    });
	</script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<div id="tickets">
				<form id="ticketsForm">
					<ul id="fieldlist">
						
						<!--autoBind自动绑定  cascadeFrom联动  optionlabel默认lable -->
						<li><label for="dx">多选</label>
						<select kendo-multi-select="" k-options="selectOptions"></select>
						</li>
						
					</ul>
				</form>
			</div>
		</div>
</body>