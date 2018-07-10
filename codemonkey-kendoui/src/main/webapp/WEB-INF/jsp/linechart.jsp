<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
    	$scope.kendoChartOptions = {
				dataSource : KendoUtils.createDataSource({
					url : "/kendoui/chartdata"
				}),
				title : {
					text : "图表"
				},
				legend : {
					position : "bottom"
				},
				seriesDefaults : {
					type : "line"
				},
				series : [ {
					field : "nuclear",
					name : "方块"
				}, {
					field : "hydro",
					name : "三角"
				} ],
				valueAxis : {
					line : {
						visible : false
					}
				},
				categoryAxis : {
					field : "year",
					majorGridLines : {
						visible : false
					}
				},
				tooltip : {
					visible : true,
					format : "{0}"
				}
			};
		});
	</script>
</head>

<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content wide" ng-controller="MyCtrl">
			<div id="chart" k-options="kendoChartOptions"></div>
		</div>
	</div>
</body>