<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script>
	    angular.module("KendoDemos", [ "kendo.directives" ])
	    .controller("MyCtrl", function($scope){
	                                                                 
	    });
   </script>
   <style>
		.demo-section p {
			margin: 0 0 30px;
			line-height: 50px;
		}
		
		.k-primary {
			min-width: 150px;
		}
	</style>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<form id="ticketsForm">
				<label>基础按钮</label>
				<p>
					<kendo-button class="k-primary" ng-click="onClick1()">默认按钮 </kendo-button>
					<kendo-button on-click="onClick2()"> Button </kendo-button>
				</p>
				<h4>Disabled buttons</h4>
				<p>
					<kendo-button enable="false" class="k-primary"> 默认按钮</kendo-button>
					<kendo-button enable="false"> Disabled Button </kendo-button>
				</p>
				<p>
					<label><input type="checkbox" ng-model="disabled">勾选禁用</label>
					<kendo-button ng-disabled="disabled"> 可禁用的按钮 </kendo-button>
				</p>

				<h4>Buttons with icons</h4>
				<p>
					<kendo-button sprite-css-class="'k-icon k-i-funnel'">Filter </kendo-button>
					<kendo-button icon="'funnel-clear'"> Clear Filter </kendo-button>
					<kendo-button sprite-css-class="'k-icon k-i-refresh'">
					<span class="k-sprite">Refresh</span> </kendo-button>
				</p>
			</form>
		</div>
</body>