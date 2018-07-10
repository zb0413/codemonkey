<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>

<div id="example" ng-app="KendoDemos">
    <div ng-controller="MyCtrl">
	    <form kendo-validator="validator" ng-submit="validate($event)">
	        <ul id="fieldlist">
	            <li>
	                <label for="fullname" class="required">username</label>
	                <input type="text" name="username" ng-model="model.username"/>
	            </li>
	            <li>
	                <label for="password" class="required">password</label>
	                <input type="text" name="password" ng-model="model.password"/>
	                <span class="k-invalid-msg" data-for="password"></span>
	            </li>
	        </ul>
	        <button ng-click="doSave(model)">保存</button>
	    </form>
    </div>
</div>

<script type="text/javascript">
	var readParams = ${readParams};
    angular.module("KendoDemos", [ "kendo.directives" ])
        .controller("MyCtrl", function($scope){
        	KendoUtils.formApp($scope , {
        		readUrl : "/ext/appUser/read" , 
        		submitUrl : "/ext/appUser/update" , 
        		readParams : readParams
        	});
        });
</script>