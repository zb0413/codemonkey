Ext.define('AM.view.FlowEditPanel', {
	
	extend: 'AM.view.FlowShowPanel',
	
	alias: 'widget.floweditpanel',
	
	constructor : function(config) {
		
		var me = this;
		
		this.buildCmp();
		
		this.pageLayout();
		
		var c = {
			listeners : {
				'afterrender' : function(){
//					this.init();
				}
			}
		};
		
		Ext.apply(c , config);
		
		Ext.apply(this , c);
		
		this.callParent();
		
	},
	
	buildCmp : function(){
		Ext.define('flowTree' , {
		    extend: 'Ext.data.Model',
		    fields: ['id' , 'name' , 'text']
		});
		
		 var store = ExtUtils.treeStore({
			model : 'flowTree',
			root : {
				children : []
			}
	    });
		 
		this.tree = {
			region : "west",
			animate : false,
			store : store,
			collapsible : true,
			width : 180,
			rootVisible : false,
			title : "流程列表",
			split : true,
			xtype : "treepanel",
			listeners : {
				itemdblclick : function(view, record, item,index, event, options) {
					
				}
			}
		};
		
		this.editorToolbar = {
				region : "north",
				xtype : 'toolbar',
				items : [{
					tooltip : "创建新的流程",
					xtype : "splitbutton",
					text : "新建流程",
					listeners : {
						click : function(item, event, options) {
							newFlow();
						}
					},
					menu : {
						xtype : "menu",
						items : [ {
							tooltip : "删除选择的流程",
							text : "删除流程",
							listeners : {}
						}, {
							tooltip : "复制选择的流程",
							text : "复制流程",
							listeners : {
								click : function(item, event, options) {
								}
							}
						} ]
					}
				},{
					tooltip : "查看或更改选择的流程属性",
					text : "流程属性",
					listeners : {
						click : function(item, event, options) {}
					}
				}, {
					xtype : "tbseparator"
				}, {
					disabled : true,
					tooltip : "保存当前流程(Ctrl+S)",
					text : "保存当前",
					listeners : {
						click : function(item, event, options) {
						}
					}
				}, {
					disabled : true,
					tooltip : "保存全部流程(Ctrl+Shift+S)",
					text : "保存全部",
					listeners : {
						click : function(item, event, options) {
						}
					}
				}, {
					xtype : "tbseparator"
				}, {
					enableToggle : true,
					toggleGroup : "flow",
					tooltip : "添加流程节点",
					text : "添加节点"
				}, {
					enableToggle : true,
					toggleGroup : "flow",
					tooltip : "把源节点连接到目标节点",
					text : "连接节点"
				}, {
					xtype : "tbseparator"
				}, {
					tooltip : "移除选择的节点或连接",
					text : "移除节点",
					listeners : {
						click : function(item, event, options) {
						}
					}
				}, {
					xtype : "tbseparator"
				}, {
					tooltip : "选择全部节点和连接",
					text : "选择全部",
					listeners : {
						click : function(item, event, options) {
						}
					}
				} ]
			};
		
		this.editorPanel = {
			region : "center",
			xtype : "tabpanel",
			bodyStyle : "background-color:#787878;background-image:none",
			items : [{
				title : '新建流程图',
				xtype:'raphaelpanel'
			}],
			listeners : {
				tabchange : function(tab,newCard, oldCard,options) {
//					setSaveBtn();
//					var n = newCard.propertyNode;
//					if (n) {
//						setProperty(n);
//					} else {
//						disableProperty(true);
//					}
				}
			}
		};
		
		this.infoPanel = {
			region : "east",
			width : 180,
			split : true,
			border : false,
			xtype: 'propertygrid',
            source: {
                '(name)': 'Properties Grid',
                grouping: false,
                autoFitColumns: true,
                productionQuality: false,
                created: Ext.Date.parse('10/15/2006', 'm/d/Y'),
                tested: false,
                version: 0.01,
                borderWidth: 1
            }
		};
		
		
	},
	
	pageLayout : function() {
		
		this.layout = 'border';
		
		this.items = [ this.tree, this.editorPanel , this.infoPanel , this.editorToolbar];
	}
	
});