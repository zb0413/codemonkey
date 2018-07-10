<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<div id="example" ng-app="KendoDemos">
    <div ng-controller="MyCtrl">
    	<form>
    		<label>username</label>
    		<input ng-model="queryInfo.username_Like"/>
    		<label>password</label>
    		<input ng-model="queryInfo.password_Like"/>
    		<button ng-click="doSearch()">search</button>
    	</form>
        <kendo-grid options="mainGridOptions">
        </kendo-grid>
    </div>
</div>

<script type="text/javascript">
    angular.module("KendoDemos", [ "kendo.directives" ])
        .controller("MyCtrl", function($scope){
        	KendoUtils.listApp($scope , {
        		url : '/ext/appUserList/read' , 
        		gridOptions: 'mainGridOptions',
        		columns : [{
                    field: "username",
                    title: "username",
                    width: "120px"
                },{
                    field: "id",
                    title: "id",
                    width: "120px"
                },{
                	command: { 
                		text: "Details", 
                		click: function(e){
                			$scope.showDetails(e);
                		} 
                	}, 
               		title: " ", 
               		width: "180px" 
                }]
        	});
        	
            $scope.showDetails = function (e){
               	e.preventDefault();
				KendoUtils.iframeWin({
					url : '/ext/appUser/show',
					readParams : {id : 1}
				});
            }
        });
</script>