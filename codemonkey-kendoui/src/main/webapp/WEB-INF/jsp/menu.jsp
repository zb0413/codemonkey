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
	        $scope.menuOrientation = "horizontal";
	        $scope.onSelect = function(ev) {
	            alert($(ev.item.firstChild).text());
	        };
	    });
   </script> 
</head>
<body> 
	<div id="example" ng-app="KendoDemos"> 
	   <div class="demo-section k-content" ng-controller="MyCtrl"> 
	     <form id="ticketsForm"> 
	       <label>Preview</label>
	       <ul kendo-menu="" k-orientation="menuOrientation"
	            k-rebind="menuOrientation" k-on-select="onSelect(kendoEvent)">
	            <li>Men's
	            	<ul>
	                    <li>Footwear
	                        <ul>
	                            <li>Leisure Trainers</li>
	                            <li>Running Shoes</li>
	                            <li>Outdoor Footwear</li>
	                            <li>Sandals/Flip Flops</li>
	                            <li>Footwear Accessories</li>
	                        </ul>
	                    </li>
	                    <li>Leisure Clothing
	                        <ul>
	                            <li>T-Shirts</li>
	                            <li>Hoodies & Sweatshirts</li>
	                            <li>Jackets</li>
	                            <li>Pants</li>
	                            <li>Shorts</li>
	                        </ul>
	                    </li>
	                    <li>Sports Clothing
	                        <ul>
	                            <li>Football</li>
	                            <li>Basketball</li>
	                            <li>Golf</li>
	                            <li>Tennis</li>
	                            <li>Swimwear</li>
	                        </ul>
	                    </li>
	                    <li>Accessories</li>
	                </ul>
	            </li>
	            <li>Women's
	                <ul>
	                    <li>Footwear
	                        <ul>
	                            <li>Leisure Trainers</li>
	                            <li>Running Shoes</li>
	                            <li>Outdoor Footwear</li>
	                            <li>Sandals/Flip Flops</li>
	                            <li>Footwear Accessories</li>
	                        </ul>
	                    </li>
	                    <li>Leisure Clothing
	                        <ul>
	                            <li>T-Shirts</li>
	                            <li>Jackets</li>
	                        </ul>
	                    </li>
	                    <li>Sports Clothing
	                        <ul>
	                            <li>Basketball</li>
	                            <li>Golf</li>
	                            <li>Tennis</li>
	                            <li>Swimwear</li>
	                        </ul>
	                    </li>
	                    <li>Accessories</li>
	                </ul>
	            </li>
	            <li>Boy's
	                <ul>
	                    <li>Footwear
	                        <ul>
	                            <li>Leisure Trainers</li>
	                            <li>Running Shoes</li>
	                            <li>Outdoor Footwear</li>
	                            <li>Sandals/Flip Flops</li>
	                            <li>Footwear Accessories</li>
	                        </ul>
	                    </li>
	                    <li>Leisure Clothing
	                        <ul>
	                            <li>T-Shirts</li>
	                            <li>Hoodies & Sweatshirts</li>
	                            <li>Jackets</li>
	                            <li>Pants</li>
	                            <li>Shorts</li>
	                        </ul>
	                    </li>
	                    <li>Sports Clothing
	                        <ul>
	                            <li>Football</li>
	                            <li>Basketball</li>
	                            <li>Rugby</li>
	                            <li>Tennis</li>
	                            <li>Swimwear</li>
	                        </ul>
	                    </li>
	                    <li>Accessories</li>
	                </ul>
	            </li>
	        </ul>
		</div>   
	</div> 
</body>