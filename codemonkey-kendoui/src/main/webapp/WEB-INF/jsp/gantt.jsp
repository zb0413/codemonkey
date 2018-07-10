<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
 <body> 
  <div id="example"> 
   <div ng-app="KendoDemo" ng-controller="MyCtrl"> 
    <div kendo-gantt="" k-options="ganttOptions"></div> 
   </div> 
  </div> 
   <script>
   angular.module("KendoDemo", ["kendo.directives"])
   .controller("MyCtrl", function($scope) {
       var serviceRoot = "/kendoui";
       var tasksDataSource = new kendo.data.GanttDataSource({
           transport: {
               read: {
                   url: serviceRoot + "/ganttList",
                   dataType: "json"
               }
           }
       });
       
       var dependenciesDataSource = new kendo.data.GanttDependencyDataSource({
           transport: {
               read: {
                   url: serviceRoot + "/ganttDependency",
                   dataType: "json"
               }
           }
       });

       $scope.ganttOptions = {
           dataSource: tasksDataSource,
           dependencies: dependenciesDataSource,
           views: [
               "day",
               { type: "week", selected: true },
               "month"
           ],
           columns: [
               { field: "id", title: "ID", width: 60 },
               { field: "title", title: "Title", editable: true },
               { field: "start", title: "Start Time", format: "{0:MM/dd/yyyy}", width: 100 },
               { field: "end", title: "End Time", format: "{0:MM/dd/yyyy}", width: 100 }
           ],
           height: 400,

           showWorkHours: false,
           showWorkDays: false
       };
   });

$(document).bind("kendo:skinChange", function() {
   var gantt = $("div [kendo-gantt]").data("kendoGantt");

   if (gantt) {
       gantt.refresh();
   }
});


   </script> 
 </body>