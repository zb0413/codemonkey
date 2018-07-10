<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
		 angular.module("KendoDemos", [ "kendo.directives" ])
		    .controller("MyCtrl", function($scope){
		    	initSpreadsheet($scope);
		    });
		 
		 function initSpreadsheet($scope){
			 $scope.spreadsheetOptions = {
				columns: 20,
		        rows: 100,
	            toolbar: false,
	            sheetsbar: false,
	            sheets: [{
	                name: "Products",
	                height : 200,
	                dataSource: KendoUtils.createDataSource({url: "/kendoui/spreadSheetData"}),
	                rows: [{
	                    height: 40
	                }],
	                columns: [
	                    { width: 100 },
	                    { width: 415 },
	                    { width: 145 },
	                    { width: 145 },
	                    { width: 145 }
	                ]
	            }]
	        };
		 }
	</script>
</head>
<body> 
	<div ng-app="KendoDemos">
		<div class="row main_content" ng-controller="MyCtrl">
			<div kendo-spreadsheet="" k-options="spreadsheetOptions"></div>
		</div>
	</div>   
</body>