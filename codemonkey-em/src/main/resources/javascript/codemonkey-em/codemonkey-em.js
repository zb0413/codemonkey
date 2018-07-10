var C;
var i18n;
var NS;
var ExtUtils;
var APP;
var RaphaelUtils;
var ExtDateUtils;
var CONSTANTS;

Ext.Loader.setConfig({
	enabled : true,
	paths : {
		'AM' : AM_ROOT,
		'Ext.ux' : EXAMPLE_ROOT
	}
});

(function() {
	
    
    C = Ext.create('AM.utils.CONSTANTS');
    i18n = Ext.create('AM.locale.i18n');
	NS = Ext.create('AM.utils.NS');
    ExtUtils = Ext.create('AM.utils.ExtUtils');
    ExtDateUtils = Ext.create('AM.utils.ExtDateUtils');
    CONSTANTS = Ext.create('AM.utils.CONSTANTS');
    
    var ExtFix = Ext.create('AM.override.ExtFix');
    ExtFix.fix();
    
    RaphaelUtils = Ext.create('AM.raphael.RaphaelUtils');

})();
