Ext.define('codemonkey-activiti.view.ProcessListView', {
    
	extend: 'AM.view.AppListPanel',
    modelName : 'ProcessList',
    
    modelFields : function(){
    	return [ "id" ,"url" ,"key" , "version" , "name" , "tenantId","deploymentId","deploymentUrl" , "resource" , "diagramResource" , "category" , "graphicalNotationDefined" , "suspended" , "startFormDefined"];
    },
    
    winTitle : '流程管理',
    
	limitParam : 'size',
	
    readUrl : '/repository/process-definitions',
    
    searchParams : {latest : true},
    
    buildSearchParams : function(p){
    	var params = {};
    	Ext.apply(params , this.searchParams);
    	if(p){
    		Ext.apply(params , p);
    	}
    	return params;
    },
    
    listGrid : function(){
    	var me = this;
    	return {
    		tbar : [{
				text:'流程图',
				handler: function(){
					me.showDiagramWin();
				}
    		},{
				text:'查看实例',
				handler: function(){
					
			    	var grid = me.down('grid');
			    	var roles = ExtUtils.getSelectedData(grid);
			    	
					var records = ExtUtils.getSelected(grid);
					if(records && records.length > 0){
						var view = Ext.create('codemonkey-activiti.view.ProcessInstanceListView' , {
							searchParams : {processDefinitionId : records[0].get('id')}
						});
						
						ExtUtils.win(view , {title : '流程实例'});
					}
				}
    		},'-',{
    			text : '流程建模',
    			xtype : 'splitbutton',
    			menu : [{
    				  text:'在线设计',
    				  tooltip: '在线设计流程模型',
    				  handler: function(){
    					  var view = Ext.create('codemonkey-activiti.view.ProcessDesignerView',{});
    					  
    					  var iframe = Ext.create('AM.view.MyIFrame' , {src : '/ext/processDesigner/show'});
    					  
						  ExtUtils.win(iframe , {title : '在线设计'});
    				  }
    			},{
    				text:'导入离线文件',
    				tooltip: '导入离线设计的BPMN文件',
    				handler: function(){
    					me.openUploadBpmnWin();
    				}
    			}]
    		},{
				xtype : 'triggerfield',
				emptyText:'模型名称',
				trigger1Cls:'x-form-search-trigger',
				enableKeyEvents : true,
				onTrigger1Click : function(){
					var value = this.getValue();
					if(value){
						me.doSearch({'keyLike' : value});
					}else{
						me.doSearch();
					}
				},
				listeners:{
					'specialkey' : function(obj,e){
						if (e.getKey() == Ext.EventObject.ENTER) {
							var value = this.getValue();
							if(value){
								me.doSearch({'keyLike' : value});
							}else{
								me.doSearch();
							}
						}
					}
				}
			},'-',{
				 text:'启动流程',
				 handler : function(){
					 me.startInstance();
				 }
			}],
    		columns : [      
		        {header: 'id', dataIndex: 'id' , hidden : true},
		        {dataIndex:"url",header : 'url'},
		        {dataIndex:"key",header : 'key'},
		        {dataIndex:"version",header : 'version'},
		        {dataIndex:"name",header : 'name'},
		        {dataIndex:"tenantId",header : 'tenantId'},
		        {dataIndex:"deploymentId",header : 'deploymentId'},
		        
		        {dataIndex:"deploymentUrl",header : 'deploymentUrl'},
		        {dataIndex:"resource",header : 'resource'},
		        {dataIndex:"diagramResource",header : 'diagramResource'},
		        {dataIndex:"category",header : 'category'},
		        {dataIndex:"graphicalNotationDefined",header : 'graphicalNotationDefined'},
		        
		        {dataIndex:"suspended",header : 'suspended'},
		        {dataIndex:"startFormDefined",header : 'startFormDefined'},
		        
		        {	
					header : '操作',
					xtype : 'actioncolumn',
					align : 'center',
					items : [{
						text:'修改',
						iconCls : '',
						handler: function(){_w_props_show();}
					},{
						text:'删除',
						iconCls : '',
						handler: function(){_g_model_del();}
					}]
				}
		    ]
    	};
    },
    
    pageLayout : function(searchForm , listBar , grid){
    	this.items = [grid];
	},
	
	startInstance: function(){
		
		var me = this;
    	var grid = me.down('grid');
    	var roles = ExtUtils.getSelectedData(grid);
    	
		var records = ExtUtils.getSelected(grid);
		if(records && records.length > 0){
			var url = NS.url(me.modelName , 'startProcessInstance')
			ExtUtils.doUrlAction(url , records[0].data , function(obj){
	        	
	        });
		}
	},
	
	openUploadBpmnWin : function(){
		var me = this;
		
		var view = Ext.create('codemonkey-activiti.view.UploadProcessView',{
			parentPanel : me
		});
		
		ExtUtils.win(view , {
			title : '导入离线文件',
			width : 500,
			height : 350
		});
	},
	showDiagramWin : function(){
		var items = {};
		ExtUtils.win(items , {
			title : '流程图',
			autoScroll: true,
			constrain: true,
			maximizable: true,
			modal: true,
			buttons : [{
				text : '关闭',
				handler : function(){
					this.up('window').close();
				}
			}]
		});
	}
	
});