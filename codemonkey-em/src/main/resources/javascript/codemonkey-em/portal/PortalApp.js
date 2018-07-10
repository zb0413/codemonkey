Ext.define('AM.portal.PortalApp', {
	
    extend: 'Ext.panel.Panel',
    
    alias: 'widget.portalapp',
    
    stateful : true,
    
    stateId : 'portalapp-stateId',
    
    portalColumns : 2,
    
    portletCount : 0,
    
    uses: ['AM.portal.PortalPanel', 'AM.portal.PortalColumn' , 'AM.portal.Portlet'],
    
    onRemovePortlet : function(portlet){},
    
    onAddPortlet : function(view){},
    
    onReorderPortlet : function(){},
    
    constructor : function(){
    	var me = this;
		
//    	document.write('<link rel="stylesheet" type="text/css" href="' + PAGE_DATA['ctx'] + '/css/portal.css"/>');
		
		var portalColumns = this.initPortalColumns();
		
        Ext.apply(this, {
            layout: {
                type: 'border',
                padding: '0 5 5 5' // pad the layout from the window edges
            },
            items: [{
                xtype: 'panel',
                region: 'center',
                layout: 'border',
                items: [{
                    xtype: 'portalpanel',
                    region: 'center',
                    items: portalColumns,
                    listeners : {
            				drop : function(){
            					me.onDropPortlet();
            				}
            		}
                }]
            }]
        });
        this.callParent(arguments);
        
        this.initPortalItems();
        
        this.afterCreate();
        
    },
    
    onDropPortlet  :  function(){
    	
    	var portalApp = this;
    	
    	var portalColumns = portalApp.query('portalcolumn');
    	
    	if(!portalColumns || portalColumns.length == 0){
    		return;
    	}
    	
    	for(var i = 0 ; i < portalColumns.length ; i++){
    		var portlets = portalColumns[i].query('apppportletpanel');
    		if(!portlets || portlets.length == 0){
        		continue;
        	}
    		
    		for(var j = 0 ; j < portlets.length ; j++){
    			portlets[j].colIndex = i;
    			portlets[j].sortIndex = j;
    		}
    	}
    	
    	this.onReorderPortlet();
    },
    
    initPortalColumns : function(){
    	var cols = [];
    	if(this.portalColumns){
    		for(var i = 0 ; i < this.portalColumns ; i++){
    			cols.push({
    				xtype: 'portalcolumn',
    				items : []
    			});
    		}
    	}
    	return cols;
    },
    
    /**
     * @param array[{items:[]},{items:[]}]
     */
    initPortalItems : function(){
    	
    	var array = this.portalItems;
    	
    	if(!array || array.length == 0){
    		return;
    	}
    	
    	for(var i = 0 ; i < array.length ; i++){
    		this.addPortlet(array[i]);
    	}
    },
    
    addPortlet : function(view , colIndex , title){
    	
    	colIndex = colIndex || this.portletCount %  this. portalColumns ;
        
    	var portCols = this.query('portalcolumn');
    	if(portCols && portCols.length - 1 >= colIndex){
    		portCols[colIndex].add({
        		height : 300,
        		title : title,
        		items : [view]
        	});
    		this.portletCount++;
    		
    		view.colIndex =  colIndex;
    		
    		var portlets = portCols[colIndex].query('apppportletpanel');

    		view.sortIndex = 0;
    		
    		if(portlets){
    			view.sortIndex = portlets.length + 1;
    		}

//    		portCols[colIndex].doLayout();
    		
    		this.onAddPortlet(view);
    	}
    },
    
    removePortlet : function(portlet){
    	var view = portlet.down('apppportletpanel');
    	this.onRemovePortlet(view);
    }
});