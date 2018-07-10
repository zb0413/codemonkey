<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
	<head>
	 	<title>绩效考评</title>
	</head>
	<body>
		<div ng-app="KendoDemos">
			<div class="top_bar" ng-controller="ToolBarCtrl">
			   <div kendo-toolbar k-options="toolbarOptions"></div>
			</div>
		
			<div class="row main_content" ng-controller="MyCtrl">
		        <!-- TreeView -->
		        <div class="col-sm-2">
					<div kendo-tree-view="tree" k-data-text-field="'name'" k-data-source="treeData" ng-click="functionNodeOnclick($event)">
					</div>
				</div>
				<div class="col-sm-10">
					<iframe ng-src="{{currentFunction}}" width="100%" height="100%" seamless frameborder="0"></iframe >
				</div>
			</div>
			<div class="bottom_bar">
			</div>
		</div>
	
	    <script type="text/javascript">
	    	
			$(document).ready(function(){
				onWindowResize();
			});
	    	
	    	function onWindowResize(){
	    		var height = $(document).height();
	    		height = height - $('.top_bar').height() - $('.bottom_bar').height();
	    		$('.main_content').height(height);
	    	}
	    	
	    	var functionNodes = [
				{"id":"100","name":"页面","parent":"","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"101","name":"列表","url" : "/ext/appUserList/show" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"102","name":"表单","url" : "/ext/appUser/show" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"103","name":"控件", "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1031","name":"数字日期","url" : "/kendoui/text" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1032","name":"下拉","url" : "/kendoui/dropdown" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1034","name":"条码","url" : "/kendoui/barcode" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1035","name":"多选","url" : "/kendoui/multiselect" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1036","name":"自动填充","url" : "/kendoui/auto" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1037","name":"验证","url" : "/kendoui/form" , "parent":"103","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"104","name":"消息","url" : "/kendoui/layout" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"105","name":"导航","url" : "/kendoui/navigation" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1051","name":"按钮","url" : "/kendoui/button" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1052","name":"菜单","url" : "/kendoui/menu" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1053","name":"tab页","url" : "/kendoui/tabpage" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1054","name":"toolbar","url" : "/kendoui/toolbar" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1055","name":"treeview","url" : "/kendoui/treeview" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1052","name":"panelBar","url" : "/kendoui/panelBar" , "parent":"105","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"106","name":"图表", "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1061","name":"柱图","url" : "/kendoui/columnchart" , "parent":"106","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1062","name":"折线图","url" : "/kendoui/linechart" , "parent":"106","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1063","name":"饼图","url" : "/kendoui/piechart" , "parent":"106","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"1064","name":"横形图","url" : "/kendoui/barchart" , "parent":"106","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"107","name":"上传","url" : "/kendoui/upload" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"108","name":"Gantt","url" : "/kendoui/gantt" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"},
				{"id":"109","name":"spreadSheet","url" : "/kendoui/spreadSheet" , "parent":"100","parent_name":"","code":"FN06","reportName":"","sortIndex":"5","available":true,"description":"","viewConfig":"","creationDate":"","version":"0","iconClass":"module_06","searchParams":"","delFlg":"false","modificationDate":"","createdBy":"","pageParams":"","modifiedBy":"","viewClass":"","moduleId":"zhd-me2-hospital"}
				
	    	];
	
			var dataSrouce = KendoUtils.buildTrees(functionNodes , "parent" , "id" );
	    
			angular.module("KendoDemos", [ "kendo.directives" ])
				.controller("MyCtrl", 
					function ($scope) {
						$scope.currentFunction = "/auth/login";
						$scope.treeData = dataSrouce;
						
						$scope.functionNodeOnclick = function(e){
							var dataItem = this.tree.dataItem(e.target);
							$scope.currentFunction = dataItem.url;
							event.preventDefault();
						}
					}
				)
				.controller("ToolBarCtrl", 
					function($scope){
						$scope.toolbarOptions = {
							items: [
					          { type: "button", spriteCssClass: "fa fa-paper-plane", text: "Paper plane" },
					          { type: "button", spriteCssClass: "fa fa-plane", text: "Plane" },
					          { type: "button", spriteCssClass: "fa fa-space-shuttle", text: "Space shuttle" }
					        ]
						};
					}
				);
	    </script>
	</div>
</body>
