function getQueryParam(name , path) {
    var regex = RegExp('[?&]' + name + '=([^&?]*)');

    var match = regex.exec(location.search) || regex.exec(path);
    return match && decodeURIComponent(match[1]);
}

var AM_ROOT;
var EXAMPLE_ROOT;

(function() {
	
	var scriptEls = document.getElementsByTagName('script');
 	var path = scriptEls[scriptEls.length - 1].src;
 	var extVersion = getQueryParam('version' , path) || '5.1.0';
 	var theme = getQueryParam('theme' , path) || 'neptune';
 	var ctx = CTX || '';
 	var debug = getQueryParam('debug' , path) || 'false';
 	var locale = getQueryParam('ext_locale' , path) || 'zh_CN';
 	
 	var jsRoot;
 	var cssRoot;
 	var jsInclude;
 	var localeJsInclude;
 	var cssInclude;
 	AM_ROOT = ctx + '/javascript/codemonkey-em/';
 	var customCssRoot = ctx + '/css/codemonkey-em/';
 	
 	//设置ext引用路径
 	if(extVersion >= '4.0.0' && extVersion < '5.0.0'){
 		jsRoot = ctx + '/javascript/extjs/' + extVersion + '/';
 		cssRoot = jsRoot + 'resources/css/';
 		jsInclude =  jsRoot + 'ext-all.js';
 		if(debug === 'true'){
 			jsInclude =  jsRoot + 'ext-all-debug.js';
 		}
 		cssInclude = cssRoot + '/ext-all-' + theme + '.css';
 		EXAMPLE_ROOT = jsRoot + 'examples/';
 		localeJsInclude = jsRoot + 'locale/ext-lang-' + locale + '.js';
 	}else if(extVersion >= '5.0.0' && extVersion < '6.0.0'){
 		
 	}else if(extVersion >= '6.0.0'){
 		jsRoot = ctx + '/javascript/extjs/' + extVersion + '/classic/';
 		cssRoot = jsRoot + 'resources/css/theme-' + theme + '/';
 		jsInclude =  jsRoot + 'ext-all.js';
 		if(debug === 'true'){
 			jsInclude =  jsRoot + 'ext-all-debug.js';
 		}
 		cssInclude = cssRoot + '/theme-' + theme + '-all.css';
 		EXAMPLE_ROOT = jsRoot + 'packages/ux';
 		localeJsInclude = jsRoot + 'locale/locale-' + locale + '.js';
 	}
 	
 	document.write('<script type="text/javascript" src="' + jsInclude + '"></script>');
	document.write('<script type="text/javascript" src="' + EXAMPLE_ROOT + '/classic/ux-debug.js"></script>');
 	document.write('<link rel="stylesheet" type="text/css" href="' + cssInclude + '"/>');
 	
 	if(extVersion >= '6.0.0'){
 		//charts
 		var chartsJs = jsRoot + '/charts.js';
 		if(debug === 'true'){
 			chartsJs =  jsRoot + '/charts-debug.js';
 		}
 		document.write('<script type="text/javascript" src="' + chartsJs + '"></script>');
 		
 		var chartsCss = cssRoot + 'charts-all.css';
 	 	document.write('<link rel="stylesheet" type="text/css" href="' + chartsCss + '"/>');
 	}
 	
	//添加自定义文件
 	var customJsIncludes = [
        'utils/CONSTANTS' , 
        'utils/NS' , 
        'utils/ExtUtils' , 
        'utils/ExtDateUtils',
        
        'override/ExtFix' , 
        
        'raphael/RaphaelUtils' , 
        
        'locale/i18n-' + locale,
        
        'field/DateTimeField',
        
        'field/EnumSelect',
        'field/EntitySelect',
        'field/SearchingSelect',
        'field/BaseDictionarySelect',
        'field/PopupSelect',
        'grid/ActionTextColumn',
        'plugins/ImageUpload',
        
        'chart/BaseChart',
        'chart/PolarChart',
        'chart/XYChart',
        'chart/GaugeChart',
        'chart/PieChart',
        'chart/RadarChart',
        'chart/ScatterChart'
    ];
 	
 	for(var i = 0 ; i < customJsIncludes.length ; i++){
    	 document.write('<script type="text/javascript" src="' + AM_ROOT + customJsIncludes[i] + '.js"></script>');
    }
 	
 	var v = extVersion.substring(0 , extVersion.indexOf('.'));
 	
 	var customCssIncludes = ['custom' , 'portal' , 'icons' , 'ext-override-' + v , 'HtmlEditorImageUpload_styles' , 'HtmlEditorImageUpload_iframe_styles'];
 	
 	for(var i = 0 ; i < customCssIncludes.length ; i++){
 		document.write('<link rel="stylesheet" type="text/css" href="' + customCssRoot + customCssIncludes[i] + '.css"/>');
 	}
 	
})();
