<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<style>
        .demo-section p {
            margin: 0 0 30px;
            line-height: 50px;
        }
        
        .k-primary {
            min-width: 150px;
        }    
    </style> 
    <script>
	    angular.module("KendoDemos", [ "kendo.directives" ])
	    .controller("MyCtrl", function($scope){
	        
	        $scope.toolbarOptions = {
	                items: [
	                    { type: "button", text: "Button" },
	                    { type: "button", text: "Toggle Button", togglable: true } 
	                ]
	            };
	                                                                       
	    });
   </script> 
</head>
<body> 
	<div id="example" ng-app="KendoDemos"> 
	   <div class="demo-section k-content" ng-controller="MyCtrl"> 
	    <div id="tickets"> 
	     <form id="ticketsForm"> 
		     
		    <label >toolbar </label>
		      <div kendo-toolbar="" k-options="toolbarOptions"></div> 
	     </form> 
	    </div> 
	</div> 
</body>