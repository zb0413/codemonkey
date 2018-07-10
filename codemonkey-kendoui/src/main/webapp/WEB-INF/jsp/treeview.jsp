<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<head>
	<script type="text/javascript">
	
	    var functionNodes = [
	        				{"id":"100","name":"页面","parent":"","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"101","name":"列表","url" : "/ext/appUserList/show" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"102","name":"表单","url" : "/ext/appUser/show" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"103","name":"控件", "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"1031","name":"数字日期","url" : "/kendoui/text" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"1032","name":"下拉","url" : "/kendoui/dropdown" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"105","name":"导航","url" : "/kendoui/navigation" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"1051","name":"按钮","url" : "/kendoui/button" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
	        				{"id":"1052","name":"菜单","url" : "/kendoui/menu" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"}
	         				
	     ];
	    
	    var dataSrouce = KendoUtils.buildTrees(functionNodes , "parent" , "id" );
	    
	    angular.module("KendoDemos", [ "kendo.directives" ])
	    .controller("MyCtrl", function($scope){
	    	
	    	$scope.treeData = dataSrouce;
	    	$scope.functionNodeOnclick = function(e){
	    		event.preventDefault();
				var dataItem = this.tree.dataItem(e.target);
				alert( dataItem.url);
			}
	                                                                       
	    });
   </script> 
</head>

<body> 
	<div id="example" ng-app="KendoDemos"> 
	   <div class="row main_content" ng-controller="MyCtrl"> 
	       <div class="col-sm-2">
				<div kendo-tree-view="tree" k-data-text-field="'name'" k-data-source="treeData" ng-click="functionNodeOnclick($event)">
				</div>
		   </div>
		   <div class="col-sm-10">
				<iframe ng-src="{{currentFunction}}" width="100%" height="100%" seamless frameborder="0"></iframe>
		   </div>
		</div>
	</div> 
 </body>