<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>医院协会报名</title>
		
		<link href="/css/kendoui.professional.2016.1.412/content/shared/examples-offline.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.common.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.rtl.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.default.min.css" rel="stylesheet">
		<link href="/css/kendoui.professional.2016.1.412/kendo.dataviz.min.css" rel="stylesheet">
		
		<link href="/css/codemonkey-kendoui/codemonkey-kendoui.css" rel="stylesheet">
		<!-- 
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/jquery.min.js"></script>
		 -->
		<script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.min.js"></script>
		
		<script type="text/javascript" src="${ctx}/webjars/underscorejs/1.8.3/underscore-min.js"></script>
		 
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/angular.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.core.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.angular.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.data.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.selectable.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.list.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.virtuallist.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.combobox.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.dropdownlist.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.calendar.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.datepicker.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.popup.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.button.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.toolbar.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.userevents.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.dom.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.draganddrop.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.window.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.color.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.colorpicker.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.editor.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.numerictextbox.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.upload.min.js"></script>
		
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.columnsorter.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.pager.min.js"></script>
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.grid.min.js"></script>
		
		<!-- 
		<script type="text/javascript" src="/javascript/kendoui.professional.2016.1.412/kendo.all.min.js"></script>
		<script type="text/javascript" src="/javascript/codemonkey-kendoui/kendo.all.js"></script>
		 -->
		<script type="text/javascript" src="/javascript/codemonkey-kendoui/KendoUtils.js"></script>
		
		<link rel="stylesheet" href="/webjars/bootstrap/3.3.4/css/bootstrap.min.css"/>
		<script type="text/javascript" src="/webjars/bootstrap/3.3.4/js/bootstrap.min.js"></script>
		
		<link rel="stylesheet" href="/webjars/font-awesome/4.6.3/css/font-awesome.min.css"/>
		
		<!--[if lt IE 8]>
			<script src="/webjars/json2/20140204/json2.min.js"></script>
		<![endif]-->
		
		<sitemesh:write property='head' />
		<script type="text/javascript">
		    var PAGE_DATA = ${pageData};
		    var CTX = "${ctx}";
		    
		    function resizeContent(){
		    	var height = $(document).height();
		    	var navbarHeight = $('.navbar').height() || 0;
		    	var footerHeight = $('.main-footer').height() || 0;
		    	var breadcrumbHeight = $('.breadcrumb').height() || 0;
		    	$('.content > iframe').css('min-height' , 625);
		    }
		    
		    $(document).ready(function(){
		    	resizeContent();
		    });
		    
		    $(window).resize(function(){
		    	//resizeContent();
		    });
		    
		</script>
	</head>
	<body>
		<sitemesh:write property='body' />
	</body>

</html>
