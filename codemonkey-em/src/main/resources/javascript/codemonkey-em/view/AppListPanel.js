Ext.define('AM.view.AppListPanel' , {
    
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.applistpanel',
	
	layout : {
		type : 'vbox',
		align : 'stretch',
		pack  : 'start'
	},
	
	containerType : 'window',

	searchFormHeight : C.SEARCH_FORM_HEIGHT_1,
	
	searchFormMaxHeight :C. SEARCH_FORM_HEIGHT_1,
	
	formClass : null,
	
	//是否显示行号
	showRowNumber : true,
	
	//是否分页
	pageable : true,
	
	startParam : 'start',
	limitParam : 'limit',
	
	listProxyActionMethods : {
		 create : 'POST',
		 read   : 'GET',
		 update : 'POST',
		 destroy: 'POST'
	},
	
	//后台排序 
	remoteSort : true,
	
	//是否多选
	mode : null, 
	
	//查询url
	readUrl : null,
	
	//删除列表行Url
	deleteUrl : null,
	
	//默认查询参数
	searchParams : null,
	
	//打开画面时是否查询，默认查询，如果不查询需要覆盖。
	autoSearch : true,
	
	//默认页面参数
	pageParams : null,
	
	//查询保持当前页
	keepCurrentPage : false,
	
	doSearch_callback : function(data){
		
	},
	
	doDelete_callback : function(data){
		
	},
	
	//子类需覆盖的方法
	formItems : Ext.emptyFn,
	
	listGrid : Ext.emptyFn,
	
	listStore : Ext.emptyFn,
	
	manageFields : function(form){
		
	},
	
	constructor : function(config) {

		var me = this;
		
		me.readUrl = me.readUrl || NS.url(me.modelName , C.READ);
		
		me.deleteUrl = me.deleteUrl || NS.url(me.modelName , C.DESTROY);

		Ext.apply(me, config);

		var grid = me.buildListGrid(me.listGrid());
		
		if(me.winTitle){
			Ext.apply(grid , {title : me.winTitle + "列表"});
		}
		
		var listBar = {
			xtype : 'panel',
			buttonAlign: 'left',
			border : 0 ,
			buttons : me.listActionBar()
		};
		
		var searchForm = me.buildSearchForm(me.formItems() );
		
		me.pageLayout(searchForm , listBar , grid);

		me.callParent();
		
		me.afterCreate();
	},
	
	buildSearchForm : function(items){
		var me = this;
		return ExtUtils.hboxLayout(items, {
			xtype : 'form',
			padding : 0,
    		bodyPadding : 0,
    		border : false,
			resizable : true,
			resizeHandles : 's',
			height : this.searchFormHeight,
			buttons : me.searchFormBar()
		});
	},
	
	pageLayout : function(searchForm , listBar , grid){
		if(listBar.buttons && listBar.buttons.length > 0){
			Ext.apply(grid , {tbar : listBar});
			this.items = [searchForm , grid];
		}else{
			this.items = [searchForm , grid];
		}
	},
	
	buildListGrid : function(config){
		var me = this;
		
		var store = me.buildListGridStore(config);
		
		me.listStore(store);
		
		if(me.showRowNumber){
			config.columns = [ExtUtils.ROW_NUM].concat(config.columns);
		}
		
		me.buildColumns(config.columns);
		
		var grid =  {
			store : store ,
			listeners: {
				'itemdblclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
					me.itemdblclick(record);
			    }
//				,
//				'itemclick' : function( /*Ext.view.View*/ view, /* Ext.data.Model*/ record, /*HTMLElement*/ item,/* Number*/ index, /*Ext.EventObject*/ e, /*Object*/ eOpts){
//					me.setSelectedRecord(this , record);
//				}
			},
			mode : me.mode,
			viewConfig: {
	             stripeRows: true,
	             enableTextSelection:true,
	             listeners: {
	                 itemcontextmenu: function(view, rec, node, index, e) {
	                	 e.stopEvent();
	                	 var contextMenu = me.getLineContextMenu(rec);
	                     contextMenu.showAt(e.getXY());
	                     return false;
	                 }
	             }
	         }
		};
		
		if(me.pageable){
			Ext.apply(grid , {dockedItems : [ExtUtils.pagingBar(store)]});
		}
		
		Ext.apply(grid , config);
		
		return ExtUtils.grid(grid);
	},
	
	setSelectedRecord : function(grid , record){
		var selModel = grid.getSelectionModel();
		if(selModel.isSelected(record)){
			selModel.deselect(record , false);
		}else{
			selModel.select(record , true);
		}
	},
	
	listActionBar : function(){
		var me = this;
		return [{
			action : 'create', 
			text : '新建',
			iconCls : 'btn-new',
			handler : function(){
				me.doCreate();
			}
		}];
	},
	
	searchFormBar : function(){
		var me = this;
		
		var actions = [ {
			action : 'search', 
			text : '查询',
			iconCls : 'btn-select',
			handler : function(){
				me.doSearch(null , null, false);
			}
		}, {
			action : 'reset', 
			text : '重置',
			iconCls : 'btn-reset',
			handler : function(){
				me.doReset();
			}
		} ];
		
		if(this.searchFormMaxHeight > this.searchFormHeight){
			actions = [{
				action : 'expand', 
				text : '展开',
				iconCls : 'btn-super',
				handler : function(){
					
					me.doExpendSearchForm(this);
				}
			}].concat(actions);
		}
		
		return actions;
	},
	
	buildToolBar : function(array , override){
		var bar = Ext.apply({
				xtype : 'toolbar',
				flex : 1
			} , {
				items : array
			});
		if(override){
			Ext.apply(bar , override);
		}
			
		return bar;
	},
	
	buildListGridStore : function(config){
		var me = this;
		if(!config.columns){
			alert("请提供columns");
			return;
		}
		
		var fields = ExtUtils.fieldsFromCols(config.columns);
		
		ExtUtils.model({
			modelFields : fields,
			modelName : me.modelName
		});
		
		var proxy = me.buildListGridProxy();
		
		return ExtUtils.store({
			proxy : proxy,
			modelName : me.modelName,
			groupField: config.groupField
		},{remoteSort : me.remoteSort});
	},
	
	buildListGridProxy : function(){
		var me = this;
		return ExtUtils.proxy(me.readUrl,{
			limitParam : me.limitParam,
			startParam : me.startParam,
			actionMethods : me.listProxyActionMethods
		});
	},
	
	buildColumns : function(columns){
		if(columns){
			for(var i = 0 ; i < columns.length ; i++){
				if(!columns[i].flex && !columns[i].width){
					columns[i] = Ext.apply({flex : 1} , columns[i]);
				}
			}
		}
	},
	
	afterCreate : function(){
		
		var formPanel = this.down('form');
		
		if(formPanel){
			var form = formPanel.getForm();
			
			if(this.pageParams){
				ExtUtils.data2form(ExtUtils.prepareConfig(this.pageParams) , formPanel);
			}
		
			var values = this.getSearchParams();
			
			if(values){
	    		form.setValues(values);
	    	}
			
			this.manageFields(form);
		}
		
		if(this.autoSearch === true ){
			this.doSearch();
		}
	},
	
	getSearchParams : function(){
		var values = null;
		
		if(Ext.isObject(this.searchParams)){
			values = this.searchParams;
		}else if(Ext.isFunction(this.searchParams)){
			values = this.searchParams();
		}
		
		return values;
	},
	
	getLineContextMenu : function(record){
		return Ext.create('Ext.menu.Menu', {
	        items: this.listActionBar()
	    });
	},
	/*------------------------------------
	 * ACTIONS
	 --------------------------------------*/
	
	itemdblclick : function(record){
		this.doShow(record.data);
	},
	
	doSearch : function(p , callback , keepCurrentPageFlg){
		var me = this;
		var params = me.buildSearchParams(p);
		var grid = this.down('grid');
		
		var keepCurrentPage = me.keepCurrentPage;
		if(keepCurrentPageFlg === false){
			keepCurrentPage = false;
		}else if(keepCurrentPageFlg === true){
			keepCurrentPage = true;
		}
		
		if(grid){
			ExtUtils.reloadStore(grid.getStore() , params , function(records, operation){
				var store = grid.getStore();
				if(store.groupField){
					store.group(store.groupField , "ASC");
				}
				me.doSearch_callback(records, operation);
				if(callback){
					callback(records, operation);
				}
			}, keepCurrentPage);
		}
	},
	
	buildSearchParams : function(p){
		var me = this;
		var params = {};
		
		var formPanel = this.down('form');
		
		if(formPanel){
			var form = formPanel.getForm();
			
			var errorMsg = ExtUtils.validateForm(form);
			
			if(errorMsg){
				ExtUtils.tipMsg(me , '校验失败' , errorMsg);
				return;
			}
			
			params = form.getValues();
		}
		
		var values = this.getSearchParams();
		
		if(values){
			Ext.apply(params , values); 
    	}
		
		if(p){
			params = Ext.apply(params , p , {pageable : this.pageable});
		}else{
			Ext.apply(params , {pageable : this.pageable}); 
		}
		
		return params;
	},
	
	doReset : function(){
		var grid = this.down('grid');
		var form = this.down('form');
		form.getForm().reset();
		
		var params = {};
		var values = this.getSearchParams();
		
		if(values){
			Ext.apply(params , values); 
    	}
		
		Ext.apply(params , {pageable : this.pageable});  
		
		if(grid){
			ExtUtils.reloadStore(grid.getStore() , params);
		}
	},
	
	doShow : function(params){
		
		if(this.formClass){
			var view = Ext.create(this.formClass , {
				readParams : params,
				parentPanel : this
			});
			ExtUtils.win(view , {
				width : view.winWidth ,
	            height : view.winHeight,
	            title : view.winTitle,
	            modal : view.winModal,
	            maximizable : true
	        });
		}
	},
	
	doCreate : function(){
		this.doShow({id : C.NEW_ENTITY_ID});
	},
	
	doDelete : function(data){
		
		var me = this;
		var alertStr = '是否确认删除？';
		Ext.Msg.confirm('系统提示',alertStr,
				function(btn){
					 if(btn == 'yes'){
					 	ExtUtils.mask(me);
					 	ExtUtils.doUrlAction(me.deleteUrl , data , 
								function(result) {
					 				me.doDelete_callback(result.data);
					 				me.doSearch();
									ExtUtils.unmask(me);
								} , function(result){
									ExtUtils.unmask(me);
								}
						);
					 }
					 
				}
		);
	},
	
	doExpendSearchForm : function(btn){
		var formPanel = this.down('form');
		
		if(formPanel.getHeight() < this.searchFormMaxHeight){
			btn.setText("折叠");
			formPanel.setSize(formPanel.getWidth() , this.searchFormMaxHeight);
		}else if(formPanel.getHeight() > this.searchFormHeight ){
			btn.setText("展开");
			formPanel.setSize(formPanel.getWidth() , this.searchFormHeight);
		}
		
	}
});
