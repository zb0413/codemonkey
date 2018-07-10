var ReportUtils = {
		
		reportRoot : 'jasper',
		
		pdfReport : function(reportName , parameters){
			var url = PAGE_DATA['ctx'] + '/' + this.reportRoot + '/pdf?reportName=' + reportName;
			
			if(parameters){
				for(var p in parameters){
					if(Ext.isObject(parameters[p]) || Ext.isArray(parameters[p])){
						parameters[p] = Ext.encode(parameters[p]);
					}
				}
				url += '&parameters=' + Ext.encode(parameters);
			}
			window.open(url);
		}
};