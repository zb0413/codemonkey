
Ext.define('AM.utils.ExtUtils', {
	
	/*----------------------------------
	 *          defaults
	 -------------------------------------*/
	
	
	spacer : {xtype : 'tbspacer' , flex : 1},
	
	ROW_NUM : {xtype: 'rownumberer', text : '序号' , width : 50 , align : 'center' , 
		renderer : function(column, record, recordIndex, rowIndex, columnIndex, out){
			return rowIndex + 1;
		}
	},
	
	defaultModelFields : ['id','code','name','description','version','creationDate','createdBy','modificationDate','modifiedBy'],
	
	defaultGridCols1 : function(){
		return [	
	       	{header: 'id', dataIndex: 'id' , hidden : true},
	    	{header: 'version', dataIndex: 'version' , hidden : true},
	       	{dataIndex:"code",flex:1,header : i18n.code},
			{dataIndex:"name",flex:1,header : i18n.name1}
       	];
	},
	
	defaultGridCols2 : function(){
		return [	
			{dataIndex:"createdBy",flex:1,header:i18n.createdBy},
			{dataIndex:"creationDate",flex:1,header: i18n.creationDate},
			{dataIndex:"modifiedBy",flex:1,header:i18n.modifiedBy},
			{dataIndex:"modificationDate",flex:1,header:i18n.modificationDate}
		];
	},
	
	defaultGridCols3 : function(){
		return [
		   	{header: 'id', dataIndex: 'id' , hidden : true},
		   	{header: 'version', dataIndex: 'version' , hidden : true},
			{dataIndex:"name",flex:1,header : i18n.name1}
		];
	},
	
	defaultGridCols4 : function(){
		return [	
		    {header: 'id', dataIndex: 'id' , hidden : true},
		    {header: 'version', dataIndex: 'version' , hidden : true},
		    {dataIndex:"code",flex:1,header : i18n.code}
		];
	},
	defaultGridCols5 : function(){
		return [	
		    {header: 'id', dataIndex: 'id' , hidden : true},
		    {header: 'version', dataIndex: 'version' , hidden : true}
		];
	},
	
	doSubmit : function(form , override){
		var c = {
			method : 'POST',
			waitMsg : '正在提交...',
			success : function(form, action) {
			},
			failure : function(form, action) {
				var response = action.response;
				
				if(response.status != 200){
					ExtUtils.handleSysError({
						errorCode : response.status,
						errorMsg : response.statusText
					});
				}else{
					ExtUtils.handleSysError({
						errorCode : action.result.errorCode,
						errorMsg : action.result.errorMsg
					});
				}
			}
		};
			
		Ext.apply(c , override);
		
		form.submit(c);
	},
	
	/*
	 * url : string
	 * parmas : {}
	 * success : fn
	 * */
	submitForm : function(p){
		var form = Ext.create('Ext.form.Panel');
		
		var c = {
			method : 'POST',
			waitMsg : '正在提交...',
			success : function(form, action) {
			},
			failure : function(form, action) {
				var response = action.response;
				
				if(response.status != 200){
					ExtUtils.handleSysError({
						errorCode : response.status,
						errorMsg : response.statusText
					});
				}else{
					ExtUtils.handleSysError({
						errorCode : action.result.errorCode,
						errorMsg : action.result.errorMsg
					});
				}
				
//				switch (action.failureType) {
//					case Ext.form.action.Action.CLIENT_INVALID:
//						me.handleSysError({
//							errorCode : action.result.errorCode,
//							errorMsg : action.result.errorMsg
//						});
//						break;
//					case Ext.form.action.Action.CONNECT_FAILURE:
//						me.handleSysError({
//							errorCode : action.result.errorCode,
//							errorMsg : action.result.errorMsg
//						});
//						break;
//					case Ext.form.action.Action.SERVER_INVALID:
//						me.handleSysError({
//							errorCode : action.result.errorCode,
//							errorMsg : action.result.errorMsg
//						});
//				}
			}
		};
		
		Ext.apply(c , p);
		
		form.submit(c);
	},
	
	/*----------------------------------
                 BASE
	------------------------------------*/
	//TODO 废弃 , 用buildUrl 和 openWindow方法取代
	openUrl : function(url , params){
		if(!url){
			return;
		}
		var qs = "";
		if(params){
			for(var p in params){
				qs +=  p + "=" + params[p] + "&";
			}
		}
		
		if(url.indexOf('?') >= 0){
			url += "&" + qs;
		}else{
			url += "?" + qs;
		}
		window.open(url);
	},
	//TODO 提取到CommonUtils
	sort : function(array , fn) {
		if(!array) {
			return;
		}
		
		if(!Ext.isArray(array)){
			return;
		}
		
		for(var i = 0 ; i < array.length ; i++){
			for(var j = i ; j > 0 ; j--){
				var flag = fn(array[j-1] , array[j]) || 0;
				if(flag < 0){
					var temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
				}
			}
		}
		
		return array;
	},
	
	queryCmp : function(xpath , root){
		return Ext.ComponentQuery.query(xpath , root);
	},
	
	applyAll : function(to , from){
		if(!from || !to){
			return;
		}
		
		for(var p in from){
			if(Ext.isObject(from[p])){
				this.applyAll(to[p] , from[p]);
			}else{
				Ext.apply(to[p] , from[p]);
			}
		}
	},
	
	fieldsFromCols : function(cols , fields){
		fields = fields || [];
		for(var i = 0 ; i < cols.length ; i++){
			if(cols[i].dataIndex){
				fields.push({name : cols[i].dataIndex , type : cols[i].type || 'string'});
			}else if(cols[i].columns){
				this.fieldsFromCols(cols[i].columns , fields);
			}
		}
		return fields;
	},
	
	model : function(config){
		
		var modelConfig = {}
		
		var fields = config.modelFields || [];
		
		var model = config.modelName;
		
		if(this.hasEntity(model)){
			var entity = Ext.data.schema.Schema.lookupEntity(model);
			entity.replaceFields(fields , entity.getFields());
		}else{
			var baseClass = config.baseClass || 'Ext.data.Model';
			Ext.define(model , {
			     extend: baseClass,
			     fields: fields,
			     idProperty: 'id'
			});
		}
	},
	
	hasEntity : function(modelName , schema){
		var s = schema || 'default';
		return Ext.data.schema.Schema.instances[s].hasEntity(modelName);
	},
	
	proxy : function(url , override){
		var proxy = {
			type: 'ajax',
			url: url,
			reader: {
				type: 'json',
				rootProperty: 'data'
			},
			listeners : {
				exception : function( proxy1 , response, operation, eOpts ){
					if(operation.error && Ext.isObject(operation.error)){
						ExtUtils.handleSysError({
							errorCode : operation.error.status,
							errorMsg : operation.error.statusText
						});
					}else if(operation.error && Ext.isString(operation.error)){
						ExtUtils.handleSysError({
							errorCode : '000',
							errorMsg : operation.error
						});
					}else{
						var result = Ext.decode(response.responseText);
						ExtUtils.handleSysError({
							errorCode : result.errorCode,
							errorMsg : result.errorMsg
						});
					}
				}
			}
		};
		Ext.apply(proxy , override);
		return proxy;
	},
	
	/**
	 * config : {
	 *     modelName
	 *     proxy
	 * }
	 */
	store : function(config , override ){
		
		var store = {
			proxy : config.proxy,
			model : config.modelName,
			groupField: config.groupField
		};
		
		if(config.groupField){
			Ext.apply(store , {
				sorters: {property : config.groupField , direction : 'DESC'}
			});
		}
		
		Ext.apply(store , override);
		return Ext.create('Ext.data.Store' , store);
	},
	/*
	 * config : {
	 * 		modelName : modelName,
	 * 		modelFields : modelFields,
	 * 		jsonUrl : jsonUrl
	 * }
	 * 
	 */
	ajaxStore : function(config , override){
		this.model({
			modelName : config.modelName,
			modelFields : config.modelFields
		});
		
		if(!config.jsonUrl){
			config.jsonUrl = NS.url(config.modelName);
		}
		
		var proxy = this.proxy(config.jsonUrl);
		
		return this.store({
			modelName : config.modelName,
			proxy : proxy
		} , override);
	},
	
	treeStore : function(config , override){
		Ext.apply(config , override);
		return Ext.create('Ext.data.TreeStore' , config);
	},
	
	buildStoreByColumns : function(modelName , columns , override){
		if(!columns){
			alert("请提供columns");
			return;
		}
		
		if(!modelName){
			alert("请提供modelName");
			return;
		}
		
		var fields = ExtUtils.fieldsFromCols(columns , []);
		
		ExtUtils.model({
			modelFields : fields,
			modelName : modelName
		});
		
		return ExtUtils.store({modelName : modelName} , override);
	},
	
	/*
	 * config : {
	 * 		modelName : modelName,
	 * 		modelFields : modelFields,
	 * 		data : data
	 * }
	 */
	localStore : function(config , override){
		this.model({
			modelName : config.modelName,
			modelFields : config.modelFields
		});
		
		return this.store({
			modelName : config.modelName,
			data : config.data
		} , override);
	},
	
	reloadStore : function(store , params , fn , keepCurrentPage){
		var me = this;
		var proxy = store.getProxy();
		if(params){
			proxy.extraParams = this.buildQueryInfo(params);
			
			var loadCallback = function(records, operation, success){
				if(success && fn){
					fn(records, operation);
				}
				
//				if(!success || operation.exception){
//					if(operation.exception){
//						if(operation.error){
//							ExtUtils.handleSysError({
//								errorCode : operation.error.status,
//								errorMsg : operation.error.statusText
//							});
//						}else{
//							var errorCode = operation.request.scope.reader.jsonData["errorCode"];
//							var errorMsg = operation.request.scope.reader.jsonData["errorMsg"];
//							ExtUtils.handleSysError({
//								errorCode : errorCode,
//								errorMsg : errorMsg
//							});
//						}
//					}
//				}
			};
			
			if(keepCurrentPage === true){
				store.load({callback : loadCallback});
			}else {
				if(store.loadPage){
					store.loadPage(1 , {callback : loadCallback});
				}else{
					store.load({callback : loadCallback});
				}
			}
		}
	},
	
	insertStore : function(store , data , uniqueCols , index){
		if(this.isExistedRecord(store , data , uniqueCols)){
			return;
		}
		var r = Ext.create(store.model.entityName , data);
		
		var count = store.getCount();
		if(typeof(index) == 'number' && index <= count) {
			count = index;
		}
		store.insert(count, r);
	},
	//TODO 提取到CommonUtils
	buildQueryInfo : function(params){
		
		if(params){
			var pageable = params.pageable ? true : false;
			params.pageable = "";
			return {
				queryInfo :	Ext.encode(params),
				pageable : pageable
			};
		}
		return {
			queryInfo :	{}
		};
		
	},
	
	doUrlAction : function(url , params , successFn , failFn){
		var me = this;
		
		if(params){
			for(var p in params){
				if(Ext.isObject(params[p]) || Ext.isArray(params[p])){
					params[p] = Ext.encode(params[p]);
				}
			}
		}
		
		Ext.Ajax.request({
		    url: url,
		    method: 'post',
		    params: params,
		    timeout : 5 * 60 * 1000,
		    success: function(response){
		    	var result = Ext.decode(response.responseText);
		    	
		    	if(result.success){
		    		if(successFn){
		    			successFn(result);
		    		}
		    	}else{
		    		ExtUtils.handleSysError({
						errorCode : result.errorCode,
						errorMsg : result.errorMsg
					});
		    		
		    		if(failFn){
		    			failFn(result);
		    		}
		    	}
		    },
		    failure: function(response, opts) {
		    	var result = Ext.decode(response.responseText);
		    	ExtUtils.handleSysError({
					errorCode : result.errorCode || result.status,
					errorMsg : result.errorMsg || result.message
				});
		    	if(failFn){
	    			failFn();
	    		}
		    }
		});
	},
	
	/*----------------------------------
	    GRID
	------------------------------------*/

	gridSumaryType : function(dataIndex){
		return function(records){
            var total = 0 ; 
    		for (var i = 0 ; i < records.length ; i++) {
                 var record = records[i];
                 total += parseFloat(record.get(dataIndex));
             }
             return total;
        };
	},
	
	addLine: function(grid, data, uniqueCols , index){
		data = data || {};
		if(grid){
			var data2 = {};
			var cols = grid.columns;
			for(var i = 0 ; i < cols.length ; i++ ) {
				var fn = cols[i].dataIndex;
				if(fn){
					switch(cols[i].fieldType){
						case "text" :
							data2[fn] = '';
							break;
						case "integer" :
	//						data2[fn] = 0;
							break;
	//					case "number" : 
	//						data2[fn] = 0;
	//						break;
						case "date" : 
							data2[fn] = '';
							break;
						case "checkbox" : 
							data2[fn] = false;
							break;
						default :
							data2[fn] = '';
							break;
					}
				}
			}
			Ext.apply(data2 , data);
			var store = grid.getStore();
			
			var plugin = grid.findPlugin('cellediting') || grid.findPlugin('rowediting');
		    if(plugin){
			   plugin.cancelEdit();
		    }
			
			var count = store.getCount();
			if(typeof(index) == 'number' && index <= count) {
				count = index;
			}
			
			this.insertStore(grid.getStore() , data2 , uniqueCols , count);
			
			//避免添加行时引起死循环
            grid.getView().getSelectionModel().select(count,false,true);
            var record = grid.getView().getSelectionModel().getSelection()[0];
            return record;
		}
	},
	
	removeLine: function(grid , recordToDel) {
		var selection;
		if(recordToDel) {
			selection = recordToDel;
		} else {
			selection = this.getSelected(grid);
		}
	   if(selection) {
		   var plugin = grid.findPlugin('cellediting') || grid.findPlugin('rowediting');
		   if(plugin){
			   plugin.cancelEdit();
		   }
	   		if(Ext.isObject(selection)){
	   			this.removeRow(grid, selection);
	   		}else if(Ext.isArray(selection)){
	   			for(var i = 0 ; selection.length ; i++){
	   				this.removeRow(grid, selection[i]);
	   			}
	   		}
        }
	},
	
	removeRow : function(grid , record){
		var selectionIndex = grid.getStore().indexOf(record);
    	grid.getStore().remove(record);
    	if(grid.getStore().getCount() > 0) {
    		if(selectionIndex <= 0) {
    			selectionIndex = 1;
    		}
    		grid.getView().getSelectionModel().select(selectionIndex - 1 , false, false);
    	}
	} ,
	isExistedRecord : function(store , r , uniqueCols){
		for(var i = 0 ; i < store.getCount() ; i++){
			var record = store.getAt(i);
			if(uniqueCols){
				
				var flag = true;
				
				for(var j = 0 ; j < uniqueCols.length ; j++){
					
					if(record.get(uniqueCols[j]) && r[uniqueCols[j]]){
						flag = flag && (record.get(uniqueCols[j]) == r[uniqueCols[j]]);
					}
					
				}
				if(flag){
					return true;
				}
			}
		}
		return false;
	},
	
	getSelected : function(grid , fn){
		if(!grid){
			return null;
		}
		var records = grid.getSelectionModel().getSelection();
		if(fn){
			fn(records);
		}
		return records;
	},
	
	getSelectedData : function(grid){
		var data = [];
		grid.getStore().clearFilter();
		var records = grid.getSelectionModel().getSelection();
		for(var i = 0 ; i < records.length ; i++){
			data.push(records[i].data);
		}
		return data;
	},
	
	getDeletedData : function(grid){
		var data = [];
		
		grid.getStore().clearFilter();
		
		var records = grid.getStore().getRemovedRecords() || [];
		
		for(var i = 0 ; i < records.length ; i++){
			data.push(records[i].data);
		}
		
		return data;
	},
	
	getModifedData : function(grid){
		var data = [];
		
		if(!grid){
			return data;
		}
		this.completeGridEdit(grid);
		
		grid.getStore().clearFilter();
		
		var records = grid.getStore().getModifiedRecords();
		
		for(var i = 0 ; i < records.length ; i++){
			var row = this.getRowData(records[i] , grid); 
			data.push(row);
		}
		
		return data;
	},
	
	getRowData : function(r , grid){
		
		if(!r || !grid){
			return;
		}
		
		var row = {rowId : r.id};
		
		var data = r.data;
		
		if(data){
			for(var p in data){
				var column = this.queryCmp('gridcolumn[dataIndex=' + p + ']' , grid);
				if(column && column[0]){
					if(column[0].xtype == "datecolumn"){
						if(column[0].format){
							row[p] = Ext.util.Format.date(data[p], column[0].format);
						}else{
							row[p] = Ext.util.Format.date(data[p], Ext.Date.defaultFormat);
						}
					}else{
						row[p] = data[p];
					}
				}else{
					row[p] = data[p];
				}
			}
		}
		
		//修正6.0后新建record自动生成的id传到后台引起转换错误
		if(r.phantom){
			row[r.idProperty] = '';
		}
		
		return row;
	},
	
	completeGridEdit : function(grid){
		if(grid.plugins){
			var plugins = grid.plugins;
			if(plugins){
				for(var j = 0 ; j < plugins.length ; j++){
					if(plugins[j].completeEdit){
						plugins[j].completeEdit();
					}
				}
			}
		}
	},
	
	getAllData : function(grid){
		var data = [];
		
		if(!grid){
			return data;
		}
		this.completeGridEdit(grid);
		
		grid.getStore().clearFilter();
		
		var records = grid.getStore().getRange();
		
		for(var i = 0 ; i < records.length ; i++){
			var row = this.getRowData(records[i] , grid); 
			data.push(row);
		}
		
		return data;
	},
	
	setSelections : function(grid , records){
		
	},
	
	/*----------------------------------
    			TREE
	------------------------------------*/
	
	tree : function(config){
		var tree = {
			xtype : 'treepanel',	
//		    store: store,
	        hideHeaders: true,
	        rootVisible: false,
	        viewConfig: {
	            plugins: [{
	                ptype: 'treeviewdragdrop'
	            }]
	        },
	        collapsible: true
		};
		
		Ext.apply(tree , config);
	    return tree;
	},
	
	checkedTreeJson : function(treePanel){
		var values = [];
		var treeNodes = treePanel.getView().getChecked();
		
		if(!treeNodes){
			return values;
		}
		
		for(var i = 0 ; i < treeNodes.length ; i++){
			if(treeNodes[i].get('id') && treeNodes[i].get('id') != 'root'){
				values.push({id : treeNodes[i].get('id') , parentId : treeNodes[i].get('parentId') , root : treeNodes[i].get('root') , text : treeNodes[i].get('text')});
			}
		}
		
		return values;
	},
	
	checkChildNodes : function(node , checked){
    	var do_flg = true;
    	node.set('checked', checked);
    	if(node.parentNode){
    		if(checked === true){
    			this.setParentNode(node.parentNode , checked);
    		}else{
    			if(node.parentNode.childNodes.length == 1){
    				this.setParentNode(node.parentNode , checked);
    			}else{
    				for(var i = 0 ;  i < node.parentNode.childNodes.length ; i++){
    	    			if(node.parentNode.childNodes[i].data.checked === true && node.id != node.parentNode.childNodes[i].id){
    	    				do_flg = false;
    	    				break;
    	    			}
    	    		}
    				if(do_flg){
    					this.setParentNode(node.parentNode , checked);
    				}
    			}
    		}
    	}
    	if(node.childNodes){
    		for(var i = 0 ;  i < node.childNodes.length ; i++){
    			this.checkChildNodes(node.childNodes[i] , checked);
    		}
    	}
    },
    
    setParentNode : function(node ,checked) {
    	node.set('checked', checked);
    	if(node.parentNode){
    		if(checked === true){
    			node.parentNode.set('checked', checked);
    		}else{
    			if(node.parentNode.childNodes.length == 1){
    				this.setParentNode(node.parentNode , checked);
    			}
    		}
    	}
    },
		
	/*----------------------------------
	             UI
	 ------------------------------------*/
	
	/**
     * @cfg [] items : 窗口内容
     *
     * @cfg {} override : 覆盖默认设置
     */
	win : function(items , override){
		var d = Ext.dom.Element;
		var width = d.getViewportWidth() - 300;
		var height = d.getViewportHeight() - 200;
		
		var c = {
			width : width,
			height : height,
			autoShow : false,
			closable : true,
			layout : 'fit',
			items : items,
			constrainHeader : true,
			constrain : true,
			modal : true
		};
		
		Ext.apply(c , override);
		
		if(c.width > d.getViewportWidth()){
			c.width = d.getViewportWidth();
		}
		
		var win = Ext.create('Ext.window.Window' , c);
		win.show();
		return win;
	},
	
	/**
     * @cfg {} config : form配置
     */
	form : function(config){
		var formConfig = {
			xtype : 'form',
			border : 0,
//			height : 100 ,
			defaultType: 'textfield',
			bodyPadding: 10,
			collapsible : true,
			collapsed : false,
			collapseFirst : true,
//			title : i18n.search,
//			resizable : true,
//			resizeHandles : 's',
			fieldDefaults: {
				labelAlign: 'right',
		        labelWidth: 60
			}
		};
		Ext.apply(formConfig , config);
		return formConfig;
	},
	
	hboxPanel : function(items , override){
	
		var c = {
			layout : {
				type : 'hbox',
        		align: 'stretch'
			}
		};
		
		Ext.apply(c , override);
		
		return this.panel(items , c);
	},
	
	vboxPanel : function(items , override){
		var c = {
			layout : {
				type : 'vbox',
        		align: 'stretch'
			}
		};
		
		Ext.apply(c , override);
		
		return this.panel(items , c);
	},
	
	fitPanel : function(items , override){
		var c = {
			layout : 'fit'
		};
		
		Ext.apply(c , override);
		
		return this.panel(items , c);
	},
	
	panel : function(items , override){
		var p = {
			xtype : 'panel',
			padding : 1,
    		bodyPadding : 1,
    		border : false,
    		items : items
    	};
		Ext.apply(p , override);
		return p;
	},
	
	formContainer : function(items , override){
		
		var hidden = items.hidden;
		var c = {
			xtype : 'container',
			flex : 1,
			border : 0,
			padding : 0,
//			maxWidth : 300,
			fieldDefaults:{
				labelAlign : "right",
				
				labelWidth : 80,
				maxWidth : 300
			},
			layout: {
                type: 'vbox',
                align: 'stretch'
			},
			hidden : hidden,
			items : items
		};
		Ext.apply(c , override);
		return c;
	},
	
	creationInfoPanel : function(){
		var col1 = [
			{xtype:"textfield",name:"id","fieldLabel": i18n.id , readOnly : true},
			{xtype:"textfield",name:"createdBy","fieldLabel":  i18n.createdBy , readOnly : true},
			{xtype:"textfield",name:"modifiedBy","fieldLabel": i18n.modifiedBy , readOnly : true}
		];
    	
    	var col2 =  [
			{xtype:"textfield",name:"version","fieldLabel": i18n.originVersion , readOnly : true},
			{xtype:"textfield",name:"creationDate","format":"Y-m-d","fieldLabel": i18n.creationDate , readOnly : true},
			{xtype:"textfield",name:"modificationDate","format":"Y-m-d","fieldLabel":i18n.modificationDate , readOnly : true}
		];
    	
    	var p2 = this.panel(
    			ExtUtils.columnLayout([this.formContainer(col1) , this.formContainer(col2)]), {
		    		hidden : true,
		    		collapsed : true,
		    		collapsible : true,
		    		title : i18n.creationInfo
		    	});
    	
    	return p2;
	},
	
	mask : function(cmp , msg){
		msg = msg || 'processing';
		
		if(cmp && cmp.getEl()){
			cmp.getEl().mask(msg);
		}
		
	},
	
	unmask : function(cmp){
		if(cmp && cmp.getEl()){
			cmp.getEl().unmask();
		}
	},
	
	clearInvalidFields : function(form){
		if(form && form.monitor){
			var fields = form.getFields();
			for(var i = 0 ; i < fields.length ; i++){
				fields.getAt(i).clearInvalid();
			}
		}
	},
	
	setFormValues : function(form , values){
		if(form && form.monitor){
			form.setValues(values);
		}
	},
	
	getFormValues : function(form){
		if(form){
			var values = form.getValues();
			
			//修复checkbox值取不到的问题
			var fields = form.getFields().items;
			for(var i = 0 ; i < fields.length ; i++){
				if(fields[i].xtype == "checkbox" && fields[i].getName()){
					Ext.apply(values , fields[i].getModelData());
				}
			}
			return values;
		}
	},
	
	validateForm : function(form){
		var errorMsg = '';
		
		if(!form.isValid()){
			var fields = form.getFields();
			if(fields && fields.items.length > 0){
				for(var i = 0 ; i < fields.items.length ; i++){
					var field = fields.items[i];
					var errors = field.getErrors();
					if(errors && errors.length > 0){
						for(var j = 0 ; j < errors.length ; j++){
							errorMsg += field.fieldLabel + '-' + errors[j] + '<br>';
						}
					}
				}
			}
		}
		return errorMsg;
	},
	
	form2record : function (formPanel , record){
		
		if(!formPanel || !record){
			return;
		}
		
		var form = formPanel.getForm();
		var	values = form.getValues();
		if(values){
			this.data2Record(data, record);
		}
		return record;
	},
	
	data2form : function(data , formPanel){
		
		if(!data || !formPanel) return;
		
		var form = formPanel.getForm();
		for(var p in data){
			var field = form.findField(p);
			if(field){
				field.setValue(data[p]);
			}
		}
		
	},
	
	data2Record : function(data ,record){
		if(!data){
			return;
		}
		for(var p in data){
			record.set(p , data[p]);
		}
	},
	
	/*
	 * 拖拽排序
	 * 
	 * plugins : [{
			ptype : 'gridviewdragdrop',
			dragText : '拖拽行完成排序'
		}],
		listeners : {
			'drop' : function(node, data, overModel, dropPosition, eOpts ){
				var store = this.getStore();
				for(var i = 0 ; i < store.getCount() ; i++){
					store.getAt(i).set('sortIndex' , i + 1);
					store.commitChanges();
				}
			}
		} 
	 * */
	
	grid : function(config){
		
		if(!config.store){
			alert('no store found !');
			return;
		}
		
		if(!config.columns){
			alert('no columns found !');
			return;
		}
		
		//排序将hidden列放于最后，防止renderer函数对应列错误
		config.columns = this.sort(config.columns , function(a , b){
			var aHiden = a.hidden === true ? 0 : 1;
			var bHiden = b.hidden === true ? 0 : 1;
			return aHiden - bHiden;
		});
		
		
		for(var i = 0 ; i < config.columns.length ; i++){
			//column header 鼠标悬停提示   
			if(!config.columns[i].tooltip){
				Ext.apply(config.columns[i] ,{
					tooltipType : 'qtip',
					tooltip : config.columns[i].text || config.columns[i].header
				});
			}
			//searchingselect 等复杂组件显示 
			if(config.columns[i].textDataIndex){
				Ext.apply(config.columns[i] ,{
					renderer : function(value  , metaData , record , rowIndex , colIndex , store , view ){
				    	if(metaData.column.textDataIndex){
				    		return record.get(metaData.column.textDataIndex);
				    	}
				    	return value;
					}
				});
			}
		}
		
		var grid =  {
			xtype : 'gridpanel',
			flex : 4,
			selModel: {
				selType: 'checkboxmodel',
				mode : "SINGLE"
			},
			viewConfig: {
	             stripeRows: true,
	             enableTextSelection:true
	        },
	        listeners : {
//	        	deselect : function(){
//	        		ExtUtils.completeGridEdit(this);
//	        	}
	        }
		};
		
		if(config.mode && config.mode == 'MULTI'){
			Ext.apply(grid , {
				selModel: {
					selType: 'checkboxmodel',
					mode : "MULTI",
					checkOnly : true,
					toggleOnClick : false
			    }
			});
		}
		
		Ext.apply(grid , config);
		
		return grid;
		
	},
	
	data2RowEditor : function(grid , data){
		
		if(!grid || !data){
			return;
		}
		for(var p in data){
			var col = this.findColByDataIndex(grid , p);
			if(col.field){
				col.field.setValue(data[p]);
			}
		}
	},
	
	findColByDataIndex : function(grid , dataIndex){
		if(!grid || !dataIndex){
			return;
		}
		
		var columns = grid.columns;
		
		for(var i = 0 ; i < columns.length ; i++){
			if(columns[i].dataIndex == dataIndex){
				return columns[i];
			}
		}
		
		return null;
		
	},
	
	getColumnByDataIndex : function(grid , dataIndex){
		if(!grid || !dataIndex){
			return null;
		}
		
		var cols = grid.columns;
		
		if(cols || cols.length > 0){
			for(var i = 0 ; i < cols.length ; i++){
				if(cols[i].dataIndex && cols[i].dataIndex == dataIndex){
					return cols[i];
				}
			}
		}
		
		return null;
	},
	
	popup : function(config , overrideWin){
		var v = Ext.create('AM.view.Popup' , config);
		var c = {
			width : 800,
			height : 600,
			modal : true
		};
		
		Ext.apply(c , overrideWin);
		
		return this.win(v , c);
	},
	
	pagingBar : function(store , override){
		var c = {
			xtype : 'pagingtoolbar',
			dock : 'bottom',
			displayInfo : true,
			store : store,
            emptyMsg : '暂时没有数据',
            beforePageText : '当前页',
            afterPageText : '总页数{0}',
            displayMsg : '从 {0}到 {1}条   总计{2}条'
		};
		
		Ext.apply(c , override);
		
		return c;
	},
	/*----------------------------------
	    LAYOUT
	------------------------------------*/
	
	hboxLayout : function(items , override){
		var c = {
			xtype : 'panel',
			border : false,
			layout : {
				type : 'hbox',
				align : 'top'
			},
			items : this.buildFormBlock(items)		
		};
		Ext.apply(c , override);
		return c;
	},
	
	vboxLayout : function(items , override){
		var c = {
			xtype : 'panel',
			border : false,
			layout : {
				type : 'vbox',
				align : 'stretch',
				pack  : 'start'
			},
			items : this.buildFormBlock(items)		
		};
		Ext.apply(c , override);
		return c;
	},
	
	columnLayout : function(items , override){
		
		var layout = {
			layout:'hbox',
			xtype:'container',
			items: this.buildFormBlock(items)
		};
		Ext.apply(layout , override);
		return layout;
	},
	
	accordionLayout : function(items , override){
		var layout = {
			layout:'accordion',
			xtype:'container',
			items: this.buildFormBlock(items)
		};
		Ext.apply(layout , override);
		return layout;
	},
	
	fitLayout : function(items , override){
		var layout = {
			layout : 'fit',
			xtype : 'container',
			items : this.buildFormBlock(items)
		};
		Ext.apply(layout , override);
		return layout;
	},
	
	tableLayout : function(items , override){
		
		var items1 = [];
		
		for(var i = 0 ; i< items.length; i++){
			var t = Ext.apply({ margin: '5 0 0 0' ,
				labelWidth : 80 },
				items[i]);
			items1.push(t);
		}
		
		var layout = {
			xtype : 'panel',
			border : false,
			layout : {
			   type : 'table',
			   columns : 100,
			   tableAttrs: {
		            style: {
		                width: '100%',
		                height: '100%'
		            }
		        }
			},
			items : items1,
			fieldDefaults:{
				labelAlign : "right",
				labelWidth : 80,
				maxWidth : 300 
			}
		};
		
		Ext.apply(layout , override);
		 
		return layout;
	},
	
	buildFormBlock : function(array){
		var items = [];
		if(array){
			for(var i = 0 ; i < array.length ; i++){
				items.push(this.formContainer(array[i]));
			}
		}
		return items;
	},
	
	handleSysError : function(p){
		
		if(p.errorCode == 601){
			Ext.Msg.alert('系统错误', '错误代码：' + p.errorCode + '<br>错误信息 ：' + p.errorMsg , function(){
				window.location = PAGE_DATA.ctx + '/auth/login';
			});
		}else{
			Ext.Msg.alert('系统错误', '错误代码：' + p.errorCode + '<br>错误信息 ：' + p.errorMsg);
		}
		
	},
	
	
	/**
	 * 设置form field属性，支持disable 和readOnly ， editable
	 * */
	setFormFieldState : function(form , state , exceptFields){
		var fields = form.getFields();
		for(var i = 0 ; i < fields.items.length ; i++){
			if(this.indexOf(exceptFields , fields.items[i].name) < 0 ){
				if(state == 'readOnly'){
					fields.items[i].setReadOnly(true);
				}else if(state == 'editable'){
					fields.items[i].setReadOnly(false);
				}
			}
		}
	},
	
	indexOf : function (array , obj){
		
		var array1 = array || [];
		
		for(var i = 0 ; i < array1.length ; i++){
			if(array1[i] == obj){
				return i;
			}
		}
		
		return -1;
	},
	
	setFormButtonState : function(panel , state , exceptButtons){
		var buttons = panel.query('button');
		for(var i = 0 ; i < buttons.length ; i++){
			if(this.indexOf(exceptButtons , buttons[i].action) < 0 ){
				if(state == 'enable'){
					buttons[i].enable();
				}else if(state == 'disable'){
					buttons[i].disable();
				}
			}
		}
	},
	
	createBox : function(t, s){
        return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    },
    
    tipMsg : function(cmp , title, content){
         if(cmp && cmp.el){
            var msgCt = Ext.DomHelper.insertFirst(cmp.el , {
            	id:'msg-div-' + Ext.id(),
            	cls: 'msg-div'
            }, true);
            var m = Ext.DomHelper.append(msgCt, this.createBox(title || '系统提示' , content), true);
            m.hide();
            m.slideIn('t').ghost("t", { delay: 1000, remove: true});
         }
    },
    
	/**
	 *  1.+ URL 中+号表示空格 %2B 
		2.空格 URL中的空格可以用 %20 
		-- 3./ 分隔目录和子目录 %2F 
		4.? 分隔实际的 URL 和参数 %3F 
		5.% 指定特殊字符 %25 
		6.# 表示书签 %23 
		7.& URL 中指定的参数间的分隔符 %26 
		8.= URL 中指定参数的值 %3D
		9." 双引号 %22
		10.' 单引号 %27
		11.\ 反斜杠 %5C
		
	 */
    //TODO 提取到CommonUtils
	encodeURL : function(s){
//		var t = encodeURI(s);
		
		if (typeof s == 'string' && s.length > 0) {
			var t = s;
			return t.replace(/\+/g, '%2B')
			.replace(/ /g , '%20')
			.replace(/\//g, '%2F')
			.replace(/\?/g , '%3F')
			.replace(/\#/g , '%23')
			.replace(/\&/g , '%26')
			.replace(/\=/g, '%3D')
			.replace(/\"/g, '%22')
			.replace(/\'/g, '%27')
			.replace(/\\/g, '%5C');
		}
		
		return s;
		
	},
	
	themes : function(){
		var themes = [{
			text : '经典',
			handler : function(){
				window.location = window.location + '?theme=classic'
			}
		},{
			text : '灰色',
			handler : function(){
				window.location = window.location + '?theme=gray'
			}
		},{
			text : 'neptune',
			handler : function(){
				window.location = window.location + '?theme=neptune'
			}
		},{
			text : 'access',
			handler : function(){
				window.location = window.location + '?theme=access'
			}
		},{
			text : 'classic-sandbox',
			handler : function(){
				window.location = window.location + '?theme=classic-sandbox'
			}
		}];
		
		return themes;
	},
	
	prepareConfig : function(obj){
		var values = {};
		
		if(Ext.isObject(obj) || Ext.isArray(obj)){
			values = obj;
		}else if(Ext.isFunction(obj)){
			values = obj();
		}
		
		return values;
	},
	
	/**
    * 去掉前后空格
    * @param str
    * @returns str
    */
	//TODO 提取到CommonUtils
    trim : function (str){
    	if(str){
        	return str.replace( /^\s+/, "" ).replace( /\s+$/, "" );    		
    	}
    },
    //TODO 提取到CommonUtils
    REGX_HTML_ENCODE : /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g,
    //TODO 提取到CommonUtils
    REGX_HTML_DECODE : /&\w+;|&#(\d+);/g,
    //TODO 提取到CommonUtils
    HTML_DECODE : {
        "&lt;" : "<", 
        "&gt;" : ">", 
        "&amp;" : "&", 
        "&nbsp;": " ", 
        "&quot;": "\"", 
        "&copy;": ""
    },
    //TODO 提取到CommonUtils
    encodeHtml : function(s){
        s = (s != undefined) ? s : this.toString();
        return (typeof s != "string") ? s :
            s.replace(this.REGX_HTML_ENCODE, 
                      function($0){
                          var c = $0.charCodeAt(0), r = ["&#"];
                          c = (c == 0x20) ? 0xA0 : c;
                          r.push(c); r.push(";");
                          return r.join("");
                      });
    },
    //TODO 提取到CommonUtils
    decodeHtml : function(s){
        var HTML_DECODE = this.HTML_DECODE;

        s = (s != undefined) ? s : this.toString();
        return (typeof s != "string") ? s :
            s.replace(
            	  this.REGX_HTML_DECODE,
	              function($0, $1){
	                  var c = HTML_DECODE[$0];
	                  if(c == undefined){
	                      // Maybe is Entity Number
	                      if(!isNaN($1)){
	                          c = String.fromCharCode(($1 == 160) ? 32:$1);
	                      }else{
	                          c = $0;
	                      }
	                  }
	                  return c;
	              }
            );
    },
	
	//打印处理
	reportOpen : function(reportName , reportParamsNam, reportParamsVal){
		var url = this.reportUrl(reportName,reportParamsNam,reportParamsVal);
		window.open(url);
		//window.open("/report/show?url=" + this.encodeURL(url));
	},
	
	reportUrl : function(reportName , reportParams){
		var url = CONSTANTS.REPORT_PROTOCOL + PAGE_DATA.IPADDRESS + CONSTANTS.REPORT_ROOT + CONSTANTS.REPORT_URL + reportName;
		if (reportParams){
//	        var paramsInfo = [];
//			for (var p in reportParams){
//				 paramsInfo.push(reportParams[p]);
//			} 

			var params = '&paramsInfo=' + Ext.encode(reportParams);
			
			url += params;
		}
		return url ;
	},
	//转换汉字为unicode编码
	//TODO 提取到CommonUtils
	toUnicode : function(str){
     return escape(str).replace(/%/g,"\\").toLowerCase();
    },
    //解释unicode编码为汉字
  //TODO 提取到CommonUtils
    unUnicode:function(str){
     return unescape(str.replace(/\\/g, "%"));
    },
	
    equals : function(obj1 , obj2){
    	
    	if(!obj1 && !obj2){
    		return true;
    	}else if(obj1 && !obj2){
    		return false;
    	}else if(!obj1 && obj2){
    		return false;
    	}
    	
    	if(obj1 && obj2){
    		if(Ext.isPrimitive(obj1) && Ext.isPrimitive(obj2)){
        		return obj1 === obj2;
        	}else if(Ext.isObject(obj1) && Ext.isObject(obj2)){
        		for(var p in obj1){
        			if(!this.equals(obj1[p] , obj2[p])){
        				return false;
        			}
        		}
        	}else if(Ext.isArray(obj1) && Ext.isArray(obj2)){
        		
        		if(obj1.length != obj2.length){
        			return false;
        		}
        		
        		for(var i = 0 ; i < obj1.length ; i++){
        			if(!this.equals(obj1[i] , obj2[i])){
        				return false;
        			}
        		}
        	}
    	}
    	
    	return true;
    	
    },
    
    buildTrees : function(array , parentProp , idProp , rootNode){
    	
    	var roots = [{expanded : true}];
    	
    	if(!array){
    		return null;
    	}
    	
    	parentProp = parentProp || 'parent';
    	
    	idProp = idProp || 'id';
    	
    	if(rootNode){
    		roots = [rootNode];
    	}else{
    		roots = this._findRootNodes(array , parentProp , idProp);
    	}
    	
    	if(roots && roots.length > 0){
    		for(var i = 0 ; i < roots.length ; i++){
    			this.buildTree(array  , roots[i], parentProp , idProp);
    		}
    	}
    	return roots;
    },
    
    buildTree : function(array  , rootNode, parentProp , idProp){
    	
    	if(!array){
    		return null;
    	}
    	
    	parentProp = parentProp || 'parent';
    	
    	idProp = idProp || 'id';
    	
    	if(!rootNode){
    		return null;
    	}
    	
    	var children = this._findChildrenNodes(array , rootNode , parentProp , idProp);
    	var childNodes = [];
    	if(children && children.length > 0){
    		for(var i = 0 ; i < children.length ; i++){
    			childNodes.push(children[i]);
    			this.buildTree(array , childNodes[i] , parentProp , idProp);
    		}
    		Ext.apply(rootNode , {children : childNodes , leaf : false , text : rootNode.name , expanded : true});
    	}else{
    		Ext.apply(rootNode , {leaf : true , text : rootNode.name});
    	}
    	return rootNode;
    },
    
    _findChildrenNodes : function(array , root , parentProp , idProp){
    	var children = [];
    	var nodes = array;
    	for(var i = 0 ; i < nodes.length ; i++){
    		if(nodes[i][parentProp] == root[idProp] && nodes[i].available){
    			children.push(nodes[i]);
    		}
    	}
    	return children;
    },
    
    _findRootNodes : function(array , parentProp , idProp){
    	
    	var nodes = array;
    	var root = [];
    	for(var i = 0 ; i < nodes.length ; i++){
    		if(!nodes[i][parentProp]){
    			var children = this._findChildrenNodes(array , nodes[i] , parentProp , idProp);
    			if(children && children.length > 0){
    				root.push(nodes[i]);
    			}
    		}
    	}
    	return root;
    }
    
});
