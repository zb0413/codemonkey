<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
        $scope.showInContainer = function() {
            var date = new Date();
            date = kendo.toString(date, "HH:MM:ss.") + kendo.toString(date.getMilliseconds(), "000");
            $scope.notf2.show(date, "info");
        };
        $scope.dismissAll = function() {
            $scope.notf1.hide();
            $scope.notf2.hide();
        };
    })
    .controller("MyCtrl1", function($scope, $timeout){
          function updateTime() {
              $scope.time = kendo.toString(new Date(), "H:mm:ss.fff tt");
              $timeout(updateTime, 78);
          };
          $scope.theContent = "The <b>tooltip</b><br />Time is: {{time}}";
          updateTime();
      })
	</script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<h4>消息中心:</h4>
			<p>
				<button class="k-button" ng-click="notf1.show('我是错误消息', 'error')">右下角弹出</button>
				<br>
				<button class="k-button" ng-click="showInContainer()">区域弹出</button>
				<br>
				<button class="k-button" ng-click="dismissAll()">移除消息</button>
			</p>
			<span kendo-notification="notf1"></span>
			<span kendo-notification="notf2" k-append-to="'#container'"></span>

		</div>
		<div id="container" class="demo-section k-content"></div>
		<div ng-controller="MyCtrl1">
			<div kendo-tooltip="" k-content="'The <b>tooltip</b><br />Time is: {{time}}'" class="k-group">I have a tooltip.</div>
			<div kendo-tooltip="" k-content="theContent" class="k-group">I also have a tooltip.</div>
		</div>
	</div>
</body>