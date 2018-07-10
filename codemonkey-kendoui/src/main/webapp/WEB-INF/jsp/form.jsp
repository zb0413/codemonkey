<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	 angular.module("KendoDemos", [ "kendo.directives" ])
     .controller("MyCtrl", function($scope){
    	 $scope.validate = function(event) {
             event.preventDefault();

             if ($scope.validator.validate()) {
                 $scope.validationMessage = "Hooray! Your tickets has been booked!";
                 $scope.validationClass = "valid";
             } else {
                 $scope.validationMessage = "Oops! There is invalid data in the form.";
                 $scope.validationClass = "invalid";
             }
         }
    	 
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
						<input type="text"  name="fullname" class="k-textbox" placeholder="Full name" required validationmessage="Enter {0}" >
						</li>
						<li class="confirm">
							<button class="k-button k-primary" type="submit">提交</button>
						</li>
						<li class="status {{ validationClass }}">{{ validationMessage}}</li>
					</ul>
				</form>
			</div>
		</div>
</body>