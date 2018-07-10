<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
    angular.module("KendoDemos", [ "kendo.directives" ])
    	.controller("MyCtrl", function($scope){
        	$scope.countryNames = KendoUtils.createDataSource({url: "/kendoui/autocomplete"});
         });
	</script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<div id="tickets">
				<form id="ticketsForm">
					<ul id="fieldlist">
						<li><label for="search" class="required">自动填充</label> 
						<input type="search" kendo-auto-complete="" k-data-source="countryNames" ></li>
					</ul>
				</form>
			</div>
		</div>
</body>