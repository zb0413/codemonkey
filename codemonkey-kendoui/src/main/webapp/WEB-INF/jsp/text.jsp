<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	angular.module("KendoDemos", [ "kendo.directives" ])
    .controller("MyCtrl", function($scope){
   	
    });
	</script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="demo-section k-content" ng-controller="MyCtrl">
			<div id="tickets">
				<form id="ticketsForm">
					<ul id="fieldlist">
						<li><label for="fullname" class="required">文本框</label>
						<input type="text" name="fullname" class="k-textbox" placeholder="Full name">
						</li>
						<li><label for="time">时间</label>
						<input kendo-date-picker="" k-format="'yyyy-MM-dd'" name="time">
						</li>
						<!--decimal小数  max最大值      min最小值     format常用的有c（货币形式）、n（数值形式）和p（百分比形式） step每次增加和减少的步长-->
						<li><label for="amount">数字框</label> 
						<input kendo-numeric-text-box="" k-decimal="2"  name="Amount" type="text" min="1" max="10" value="1" >
						</li>
					</ul>
				</form>
			</div>
		</div>

</body>