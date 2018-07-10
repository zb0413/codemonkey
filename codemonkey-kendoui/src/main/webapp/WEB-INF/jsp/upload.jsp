<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	
		 angular.module("KendoDemos", [ "kendo.directives" ])
	     .controller("MyCtrl", function($scope){
	    	 
	    	 $scope.uploadOptions = {
	    		async: {
	     	       saveUrl: "/kendoui/uploadFile/create"
	    	    },
	    	    
	    		success: function(e){
	    			alert(e.response.data);
	    			$scope.$apply();
	    		}
	    	 }
	     });
    </script>
</head>
<body>
	<div id="example" ng-app="KendoDemos">
		<div class="box wide" ng-controller="MyCtrl">
			<div class="box-col">
				<h4>上传</h4>
				<input kendo-upload="" type="file" name="file" k-options="uploadOptions"/>
			</div>
		</div>
	</div>
</body>