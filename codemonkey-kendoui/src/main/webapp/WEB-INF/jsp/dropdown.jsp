<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
   	 $scope.productsDataSource = KendoUtils.createDataSource({url: "/kendoui/dropdownlist"}); 
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
						<li><label for="xl">下拉</label>
						<select kendo-drop-down-list="" k-data-text-field="'ProductName'" k-data-value-field="'ProductID'" k-data-source="productsDataSource" ></select>
						</li>
						
					</ul>
				</form>
			</div>
		</div>
</body>