Ext.define('codemonkey-activiti.view.ProcessDesignerView', {
    
	extend: 'Ext.panel.Panel',
	
	constructor : function(config) {

		Kickstart.load();
		ORYX;
		var me = this;
		
		Ext.apply(me, config);

		var naviTree = this.buildNaviTree();
		var designer = this.buildDesigner();
		var propGrid = this.buildPropGrid();
		
		me.pageLayout(naviTree , designer , propGrid);

		me.callParent();
		
		me.loadDefaultData();
	},
	
	pageLayout : function(naviTree , designer , grid){
		this.layout = 'border';
		Ext.apply(naviTree , {
			region: 'west',
		    iconCls : 'icon-navigation',
		    title: '导航',
		    split: true,
		    width: 200,
		    minWidth: 175,
		    maxWidth: 400,
		    collapsible: true,
		    animCollapse: true,
		    margins: '0 0 0 5'
		});
		Ext.apply(designer , {region : 'center'});
		
		Ext.apply(grid , {
			region : 'east',
			iconCls : 'icon-navigation',
		    title: '属性',
		    split: true,
		    width: 200,
		    minWidth: 175,
		    maxWidth: 400,
		    collapsible: true,
		    animCollapse: true,
		    margins: '0 0 0 5'
		});
		this.items = [naviTree , designer , grid];
	},
	
	buildPropGrid : function(){
		var columns = [{
			//id: 'name',
			header: 'name',
			dataIndex: 'name',
			width: 90,
			sortable: true
		},{
			//id: 'value',
			header: 'value',
			dataIndex: 'value',
			width: 110,
			editor: {xtype : 'textfield', allowBlank: false}
		},{
			header: "Pop",
			dataIndex: 'popular',
			hidden: true,
			sortable: true
		}];

		var store = ExtUtils.buildStoreByColumns("workflow-properties" , columns);
    	
     	var grid = ExtUtils.grid({
			store : store , 
			columns : columns, 
			sortableColumns :false ,
			
			clicksToEdit: 1,
			stripeRows: true,
			autoExpandColumn: "propertywindow_column_value",
			width:'auto',
			border:false, //xiongchun
			// the column model
			colModel: this.columnModel,
			enableHdMenu: false,
//			view: new Ext.grid.GroupingView({
//				forceFit: true,
//				groupTextTpl: '<span style="font-weight: normal;">{[values.rs.first().data.popular ? ORYX.I18N.PropertyWindow.oftenUsed : ORYX.I18N.PropertyWindow.moreProps]}</span>'
//			}),
			plugins: [Ext.create('Ext.grid.plugin.CellEditing')]
		});
		return grid;
	},
	
	buildNaviTree : function(){
		var naviTree = {
			itemId : 'shapeRepository',
			xtype : 'treepanel',
            cls:'shaperepository',
            
//			loader: new Ext.tree.TreeLoader(),
//			root: this.shapeList,
			autoScroll:true,
			rootVisible: false,
			useArrows: true,
			lines: false,
			border:false,
			anchors: '0, -30'
		};
		
		return naviTree
	},
	
	buildDesigner : function(){
		var toolbar = this.buildToolbar();
		var panel = {
			xtype : 'panel',
			tbar : toolbar
		};
		
		return panel;
		
	},
	
	buildToolbar : function(){
		var toolbar = [{
			text : 'save'
		},'-',{
			text : 'cut'
		},{
			text : 'paste'
		},{
			text : 'delete'
		},'-',{
			text : 'undo'
		},{
			text : 'redo'
		},'-',{
			text : 'align-middle'
		},{
			text : 'align-center'
		},{
			text : 'same-size'
		},'-',{
			text : 'zoom-in'
		},{
			text : 'zoom-out'
		},{
			text : 'zoom-standard'
		},{
			text : 'zoom-fit'
		}];
		return toolbar;
	},
	
	loadDefaultData : function(){
		
		Kickstart.kick();
		
		var treePanel = this.down("#shapeRepository");
	
		ExtUtils.doUrlAction(NS.url('stencilset') , {} , function(result){
			debugger;
			var data = Ext.decode(result.data);
			treePanel.getStore().setRootNode({expanded: true, children : data.stencils});
			
		} , function(){
			
		});
	}
	
});