 function ListPage ($obj , config){
	this.$obj = $obj;
	this.modelName = '';
	this.readAction = C.READ;
	this.pageable = true;
	this.pageSize = C.PAGE_SIZE;
	this.autoSearch = true;
	this.columns = null;
	this.quickSearch = true;
	this.advancedSearch = true;
	this.showAdvancedSearch = false;
	// SINGLE , MULTI , NONE
	this.selMode = 'NONE';

	this.doSearch_callback = function(data){
		
	};
	
	this.doBatchedUpdate_callback = function(data){
	
	};
	
	this.beforeSave = function(data){
		return data;
	};
	
	this.afterCreate = function(){};
	
	this.doCreate = function(p){
		if(!p){
			p = $.extend(true , {id : C.NEW_ENTITY_ID} , p);
		}
		JQUtils.openFormPage(this.formUrl , p);
	},
	
	this.doReset = function(p){
		var formPanel = $('.search_form' , this.$obj);
		if(formPanel.length > 0 ){
			formPanel[0].reset();
		}
		this.doSearch(p);
	};
	
	this.doBatchedUpdate = function(){
		var me = this;
	
		var table = $('.search_result' , this.$obj);
		var recordsToDelete = me.deletedData || [];
		var recordsToModify = me.recordsToModify || JQUtils.getModifedData(table);
		
		
		var params = {
			recordsToDelete : JSON.stringify(recordsToDelete),
			recordsToModify : JSON.stringify(recordsToModify)
		};
		
		params = this.beforeSave(params);
		
		JQUtils.post(this.batchedUpdateUrl , params , 
			function(result){
				me.doBatchedUpdate_callback(result);
				me.doSearch();
			} , 
			function(result){
				alert('错误');
			}
		);
	};
	
	this.loadPage = function(page , limit){
		limit = parseInt(limit);
		start = (parseInt(page)-1) * limit;
		this.doSearch(null , null , {
			start : start ,
			limit : limit,
			pageable : this.pageable
		});
	};
	
	this.doQuickSearch = function(){
		var query = $('input[name=query]' , this.$obj).val();
		this.doSearch({} , null , {start : 0 , limit : this.pageSize , query : query});
	};
	
	this.doShowAdvancedSearch = function(){
		 $('.quick_search_panel' , this.$obj).hide();
		 $('.search_form_panel' , this.$obj).show();
	};
	
	this.doShowQuickSearch = function(){
		 $('.quick_search_panel' , this.$obj).show();
		 $('.search_form_panel' , this.$obj).hide();
	};
	
	this.doSearch = function(p , callback , pagenation){
		var me = this;
		
		var table = $('.search_result' , this.$obj);
		
		if(table.length == 0){
			return;
		}
		
		var params = {};
		
		var formPanel = $('.form_items' , this.$obj);
		
		if(formPanel.length > 0 ){
			params = JQUtils.getFormValues(formPanel);
		}
		
		var values = null;
		
		if(_.isFunction(this.searchParams)){
			values = this.searchParams();
		}else if(_.isObject(this.searchParams)){
			values = this.searchParams;
		}
		
		if(values){
			params = $.extend(true , params , values);
    	}
		
		if(p){
			params = $.extend( true , params , p);
		}
		
		params = $.extend( true , params , {queryInfo : JSON.stringify(params)});
		
		if(pagenation){
			params = $.extend( true ,  params , pagenation);
		}else{
			params = $.extend( true ,  params , {start : 0 , limit : this.pageSize});
		}

		JQUtils.mask(me.$obj);
		JQUtils.post(this.readUrl , params , function(result, textStatus, jqXHR){
			
			JQUtils.reloadGrid(table , result.data , me);
			
			if(me.pageable){
				me.pageSize = result.limit;
				JQUtils.renderPager(table , result , me);
			}
			
			JQUtils.unmask(me.$obj);
			me.doSearch_callback(result.data);
			if(callback){
				callback(result.data);
			}
		});
		
	};
	
	this.doShow = function(p){
		
		var readParams = JSON.stringify(this.buildReadParams(p) || {});
		
		JQUtils.openFormPage(this.formUrl , {readParams : readParams});
		
	};
	
	this.buildReadParams = function(p){
		return {id : p.id};
	};
	
	this.doDelete = function(p){
		
		var me = this;
		
		JQUtils.mask(me.$obj);
		
		JQUtils.post(this.deleteUrl , p , 
			function(result){
				JQUtils.unmask(me.$obj);
				me.doSearch();
			} , function(result){
				JQUtils.unmask(me.$obj);
			}
		);

	};
	
	this.buildAdvancedSearchPanel = function(){
		var form = $('.search_form' , this.$obj);
		
		var panel = $(
			'<div class="row search_form_panel">' + 
				'<div class="col-sm-9">' + 
					'<div class="dataTable_filter">' + 
					'</div>' + 
				'</div>' + 
				'<div class="col-sm-3">' + 
					'<div class="input-group" role="group">' +
						'<button type="button" class="btn btn-success " pageAction="doSearch">查询</button>' +
						'<button type="button" class="btn btn-success " pageAction="doReset">重置</button>' + 
						'<button type="button" class="btn btn-success " pageAction="doShowQuickSearch">收起</button>' + 
					'</div>'+
				'</div>'+
			'</div>'
		);

		if(!this.showAdvancedSearch){
			panel.hide();
		}
		
		$('.dataTable_filter' , panel).append(form);
		
		var container = $('.list_filter' , this.$obj);
		container.append(panel);
		JQUtils.regActions(container , this);
	};
	
	this.buildQuickSearchBar = function(){
		
		var me = this;
		
		var tpl = 
			'<div class="row quick_search_panel">' + 
			'	<div class="col-sm-6">' + 
			'		<div class="dataTables_filter">' + 
			'			<div class="input-group">' + 
        	'           	<input name="query" type="text" class="form-control" placeholder="快速查询">' + 
        	'               <div class="input-group-addon">' + 
        	'                	<i class="glyphicon glyphicon-search" pageAction="doQuickSearch"></i>' + 
        	'               </div>' + 
        	'       	</div>' + 
			'		</div>' + 
			'	</div>' + 
			'	<% if(advancedSearch){ %>' +
			'	<div class="col-sm-2">' + 
			'		<button class="btn btn-success btn_create" pageAction="doShowAdvancedSearch" >高级查询</button>' +
			'	</div>' + 
			'	<% } %>' +
			'</div>';
		var compiled = _.template(tpl);
		var html = compiled({advancedSearch : this.advancedSearch});
		var container = $('.list_filter' , this.$obj);
		container.append(html);
		
		JQUtils.regActions(container , me);
	};
	
	this.doInit = function(){
		var me = this;
		if(!this.$obj){
			alert('jquery Object is null');
			return;
		}
		if(!this.modelName){
			alert('modelName is null');
			return;
		}
		
		me.formModelName = me.formModelName || me.modelName.substring(0 , me.modelName.indexOf('List'));
		
		me.readUrl = me.readUrl || NS.url(me.modelName , C.READ);
		
		me.formUrl = me.formUrl || NS.url(me.formModelName , C.SHOW);
		
		me.deleteUrl = me.deleteUrl || NS.url(me.modelName , C.DESTROY);
		
		me.batchedUpdateUrl = me.batchedUpdateUrl || NS.url(me.modelName , C.BATCHED_UPDATE);
		
		//action_bar
		var actionBar = $('.action_bar' , this.$obj);
		JQUtils.regActions(actionBar , this);

		if(me.quickSearch){
			me.buildQuickSearchBar();
		}
		
		me.buildAdvancedSearchPanel();
		
		var table = $('.search_result' , this.$obj);
		
		JQUtils.arrayGrid(table , {
			columns : me.columns,
			selMode : me.selMode,
			idField : me.idField
		});
		
		if(me.autoSearch){
			me.doSearch();
		}
		
	}
	
	$.extend(true , this , config);
	
	this.doInit();
	
	this.afterCreate();
	
	return this;
}