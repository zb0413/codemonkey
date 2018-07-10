(function() {
    
    var includeFiles = ['/utils/CONSTANTS' , '/utils/NS' , '/utils/ExtUtils' , '/override/ExtFix' , '/raphael/RaphaelUtils'];

    var scriptEls = document.getElementsByTagName('script');
    var	root = scriptEls[scriptEls.length - 1].src;
    var ctx = getQueryParam('ctx' , root) || "";
    var path = getQueryParam('path' , root) || ctx + '/javascript/codemonkey-em';
    var locale = getQueryParam('locale' , root) || 'zh_CN';
    
    Ext.Loader.setConfig({
		enabled : true,
		paths : {
			'AM' : path,
			'Ext.ux' : EXT_ROOT + 'examples/ux'
		}
	});
    
    for(var i = 0 ; i < includeFiles.length ; i++){
    	 document.write('<script type="text/javascript" src="' + path + includeFiles[i] + '.js"></script>');
    }
    
	document.write('<script type="text/javascript" src="' + path + "/locale/i18n-" + locale + '.js"></script>');
    
})();
