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
         
        $scope.click = function(dataItem) {
          alert(dataItem.text);
        };
    });
   </script> 
</head>
<body> 
	<div id="example" ng-app="KendoDemos"> 
	   <div class="demo-section k-content" ng-controller="MyCtrl"> 
		 <label >tab 页</label>
		 	<div kendo-tab-strip="" k-content-urls="[ null, null]"> 
	     	<!-- tab list --> 
		      	<ul> 
		      	 	<li class="k-state-active">First tab</li> 
		       		<li>Second tab</li> 
		      	</ul> 
		    	<div style="padding: 1em">
		        	This is the first tab 
				</div> 
		      	<div style="padding: 1em">
		        	This is the second tab 
		      	</div> 
	     	</div> 
	    </div> 
	</div> 
</body>