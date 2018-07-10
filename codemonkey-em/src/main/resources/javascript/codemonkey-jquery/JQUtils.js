/**
 * 依赖于jQuery , underscorejs , bootstrap
 * 
 */
var JQUtils = {
	post : function(url , params , success , fail){
		var me = this;
		
		if(params){
			for(var p in params){
				if(_.isArray(params[p]) || _.isObject(params[p])){
					params[p] = JSON.stringify(params[p]);
				}
			}
		}
		
		$.post(url , params , function(data, textStatus, jqXHR){
			var result = JSON.parse(data);
			if(result.success){
				if(success){
					success(result, textStatus, jqXHR);
				}
			}else{
				if(fail){
					var errorMsg = $('<p>' + result.errorMsg + '</p>');
					me.popup({title :'错误' , closable : true , popupBody : errorMsg , height : 180});
					fail(result, textStatus, jqXHR);
				}
			}
		});
	},
	
	setFormValues : function($form , values){
		if(!values){
			return;
		}
		
		if(values.selectOptions){
			for(var p in values.selectOptions){
				var el = $('select[name= ' + p.toLowerCase() + ']' , $form);
				if(el.length > 0){
					JQUtils.buildSelectOptions(el , {data : values.selectOptions[p]});
				}
			}
		}
		
		for(var p in values){
			$('input[name= ' + p + ']' , $form).val(values[p]);
			$('textarea[name= ' + p + ']' , $form).val(values[p]);
			$('select[name= ' + p + ']' , $form).val(values[p]);
		}
	},
	
	getFormValues : function($form){
		
		if(!$form || !$form.is('form')){
			return null;
		}
		var data = {};
		var array = $form.serializeArray();
		$.each(array , function(){
			if(data[this.name]){
				data[this.name] += ',' +  this.value;	
			}else{
				data[this.name] = this.value;
			}
			
		});
		return data;
	},
	
	getUrlParam : function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		if (r != null) {
			return unescape(r[2]); 
		}
		return null;
	}
	
	//TODO 提取到CommonUtils
	buildUrl : function(url , params){
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
		return url;
	},
	
	tipMsg : function($el , title, content){
		var tpl = 
			'<div class="alert alert-success alert-dismissable">' + 
			'	<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>' +
			'	<h4><i class="icon fa fa-check"></i><%=title%></h4> '+
			'	<%=content%>' +
		    '</div>';
		
		var compiled = _.template(tpl);
		var alertMsg = $(compiled({
			title : title,
			content : content
		})).width(500);
		alertMsg.css('z-index' , 1000);
		alertMsg.css('top' , 0);
		alertMsg.css('position' , 'fixed');
		alertMsg.fadeIn(1000 , function(){
			$(this).fadeOut(1000);
		});
		$el.append(alertMsg);
		
		
	},
	/**
	 	featrues : 
		channelmode=yes|no|1|0	是否使用剧院模式显示窗口。默认为 no。
		directories=yes|no|1|0	是否添加目录按钮。默认为 yes。
		fullscreen=yes|no|1|0	是否使用全屏模式显示浏览器。默认是 no。处于全屏模式的窗口必须同时处于剧院模式。
		height=pixels	窗口文档显示区的高度。以像素计。
		left=pixels	窗口的 x 坐标。以像素计。
		location=yes|no|1|0	是否显示地址字段。默认是 yes。
		menubar=yes|no|1|0	是否显示菜单栏。默认是 yes。
		resizable=yes|no|1|0	窗口是否可调节尺寸。默认是 yes。
		scrollbars=yes|no|1|0	是否显示滚动条。默认是 yes。
		status=yes|no|1|0	是否添加状态栏。默认是 yes。
		titlebar=yes|no|1|0	是否显示标题栏。默认是 yes。
		toolbar=yes|no|1|0	是否显示浏览器的工具栏。默认是 yes。
		top=pixels	窗口的 y 坐标。
		width=pixels	窗口的文档显示区的宽度。以像素计。
		
		例 ： window.open('','','width=200,height=100')
	*/
	//TODO 提取到CommonUtils
	openWindow : function(url , name , featrues){
		window.open(url , name , featrues)
	},
	
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
	
	mask : function($el){
		var $target = $el || $('body');
		var position = $target.position();
		var zIndex = $target.css('z-index') == 'auto' ? 0 : $target.css('z-index');
		var width = $target.width();
		var height = $target.height();
		
		var css = {
			position: 'absolute', 
			top: position.top + 'px',
			left: position.left + 'px',
			filter: 'alpha(opacity=60)', 
			'background-color': '#777',
			'z-index': zIndex + 1000,
			width : width,
			height : height,
			opacity:0.5, 
    		'-moz-opacity':0.5
		};
		
		var $mask = $('<div class="mask"></div>');
		$mask.css(css);
		$target.append($mask);
		$mask.show();
	},
	
	unmask : function($el){
		var $target = $el || $('body');
		var $mask = $('.mask' , $target);
		$mask.remove();
	},
	//TODO 提取到CommonUtils
	concatArray : function(array1 , array2){
	
		if(!array1){
			return null;
		}
		
		if(!array2){
			return array1;
		}
		
		for(var i = 0 ; i < array2.length ; i++){
			array1.push(array2[i]);
		}
		
		return array1;
		
	}, 
	
	createEntitySelect : function($obj , config){
		
		var c = {
			modelName : $obj.attr('input_modelName'),
			readUrl : $obj.attr('input_readUrl'),
			valueField : $obj.attr('input_valueField') || 'id',
			displayField : $obj.attr('input_displayField') || 'name',
			hiddenFied : $obj.attr('input_hiddenField'),
			pageable : $obj.attr('input_pageable') || false,
			emptyOption : $obj.attr('input_emptyOption') || true,
			searchParams : $obj.attr('input_searchParams') || null,
			autoLoad : $obj.attr('input_autoLoad') || 'true'
		};
		
		$.extend(true , c , config);
		
		var url = c.readUrl || NS.url(c.modelName , C.READ);
		
		$obj.change(function(){
			var val = $(this).val();
			$('input[name=' + c.hiddenFied + ']').val(val);
		});
		var params = {pageable : c.pageable};
		
		$.extend(true , params , {queryInfo : JSON.stringify(c.searchParams || {})});
		
		if(c.autoLoad === 'true'){
			JQUtils.post(url , params , function(result){
				JQUtils.buildSelectOptions($obj , result , c.emptyOption , c.valueField , c.displayField);
			});
		}
	},
	
	createEnumSelect : function($obj){
	
		var className = $obj.attr('input_className');
		var packageName = $obj.attr('input_packageName') || null;
		var method = $obj.attr('input_method')|| null;
		var emptyOption = $obj.attr('input_emptyOption') || true;
		
		var url = NS.url('enum' , C.READ);
		
		var p = {
			queryInfo : JSON.stringify({
				className : className,
				packageName : packageName || "",
				method : method || ""
			})
		};
		
		JQUtils.post(url , p , function(result){
			JQUtils.buildSelectOptions($obj , result , emptyOption , 'name' , 'text');
		});
		
	},
	
	createPopupSelect : function($obj){
		var callMethod = $obj.attr('input_callMethod');
		if(callMethod){
			
			var btn = $('.btn_popupselect' , $obj.parents('.input-group'));
			btn.click(function(){
				if(!$obj.list){
					$obj.list = eval(callMethod);
				}
				JQUtils.popup({title :'选择' , closable : true , popupBody : $obj.list});
			});
		}
	},
	
	buildSelectOptions : function($select , result , emptyOption , valueField , displayField){
		
		emptyOption = emptyOption || true;
		valueField = valueField || 'id';
		displayField = displayField || 'name';
		
		$('option' , $select).remove();
		
		if(emptyOption){
			$select.append('<option value="">(空)</option>');
		}
		if(result.data && result.data.length > 0){
			for(var i = 0 ; i < result.data.length ; i++){
				var opt = $('<option value="' + result.data[i][valueField] + '">' + result.data[i][displayField] + '</option>');
				$select.append(opt);
			}
		}
	},
	
	popup : function(config , handler){
		
		var tpl = 
			'<div class="modal fade popup_win" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' + 
			'	<div class="modal-dialog">' +
			'		<div class="modal-content"> '+
			'			<div class="modal-header">' +
			'			<% if(closable){%>	' +
	        '				<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
	        '					<span aria-hidden="true">&times;</span>'+
	        '				</button>'+
	        '			<% } %>	' +
	        '			<% if(title){%>	' +
	        '				<h4 class="modal-title"><%=title%></h4>'+
	        '			<% } %>	' +
	        '			</div>' +
	        '			<div class="modal-body"></div>'+
		    '		</div>' +
		    '	</div>' +
		    '</div>';
		
		var compiled = _.template(tpl);
		var popup = $(compiled({
			closable : config.closable || true,
			title : config.title
		}));
		
		if(config.title){
			$('.modal-header' , popup).append($(config.title));
		}
		
		var content = $('.modal-body' , popup);
		
		if(config.popupBody){
			$(config.popupBody).removeClass('hidden');
			content.prepend($(config.popupBody));
		}else if(config.url){
			content.load(config.url);
		}
		
		var height = config.height || $(document).height() - 100
		popup.css('width' , config.width || 600);
		popup.css('height' , height);
		
		popup.css('left' , config.x|| 50);
		popup.css('top' , config.y || 50);
		
		popup.modal();
		
		$('.modal-dialog' , popup).css('margin' , '0px auto');
//		$('.modal-content' , popup).height(height);
		
		$('body').prepend(popup);
		
		if(handler){
			handler.call(popup);
		}
		
		return popup;
	},
	
	panel : function(config , owner){
		var tpl = 
			'<div class="box box-default <%= hidden == true ?  "hidden" : "" %> ">' +
			'	<div class="box-header with-border" data-dismiss="alert">' +
			'		<% if(title){%>	' +
			'		<h3 class="box-title"><%= title %></h3>'+
			'		<% } %>	' +
			'		<div class="box-tools pull-right">' +
			'			<% if(closable){%>	' +
			'			<button class="btn btn-box-tool" data-widget="remove" >'+
			'				<i class="glyphicon glyphicon-remove" ></i>'+
			'			</button>'+
			'			<% } %>	' +
			'		</div>'+
			'	</div>'+
			'	<div class="box-body"></div>'+
			'</div>';
		
		var compiled = _.template(tpl);
		var panel = $(compiled(config));
		
		var content = $('.box-body' , panel);
		
		if(config.bodyItems){
			content.append($(config.bodyItems));
		}
		
//		panel.css('left' , config.x|| 50);
//		panel.css('top' , config.y || 50);

		this.regActions(panel , owner);
		
		return panel;
		
	},
	
	removeLine : function($table , $tr , data ){
		var table = $table.dataTable();
		var row = table.api().row($tr);
	    table.fnDeleteRow(row);
	},
	
	addLine : function($table , json , owner , uniqueCols){
		var table = $table.dataTable();
	    var oSettings = table.fnSettings();
	    
	    if(json){
	    	
	    	if(this.isExistedRecord(table , json , uniqueCols)){
				return;
			}
	    	
	    	table.oApi._fnAddData(oSettings, json);
	    }else{
	    	var data = {};
	    	var columns = oSettings.aoColumns;
	    	for(var i = 0 ; i < columns.length ; i++){
	    		data[columns[i].data] = columns[i].defaultContent;
	    	}
	    	table.oApi._fnAddData(oSettings, data);
	    }
	    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
	    table.fnDraw();
	    
	    this.regGridActions(table , owner);
	
	},
	
	isExistedRecord : function($table , json , uniqueCols){
		
		var rows = $table.api().data();
		
		for(var i = 0 ; i < rows.length ; i++){
			var record = rows[i];
			if(uniqueCols){
				
				var flag = true;
				
				for(var j = 0 ; j < uniqueCols.length ; j++){
					
					if(record[uniqueCols[j]] && json[uniqueCols[j]]){
						flag = flag && (record[uniqueCols[j]] == json[uniqueCols[j]]);
					}
					
				}
				if(flag){
					return true;
				}
			}
		}
		return false;
	},
	
	getSelected : function($table){
		
		var trs = [];
		
		$('input[name=dataTable_select]:checked' , $table).each(function(){
			var tr = $(this).parents('tr');
			trs.push(tr);
		});
		
		return $(trs);
	},
	
	getSelectedData : function($table){
		var me = this;
		var tr = this.getSelected($table);
		var rows = [];
		tr.each(function(){
			var data = me.getRowData($table , $(this));
			rows.push(data);
		});
		return rows;
	},
	
	getDeletedData : function($table){
		
		if($table){
			return $table.deletedData || [];
		}
		
		return null;
		
	},
	
	getModifedData : function($table){
		var me = this;
		var rows = [];
		var table = $table;
		var tr = $('tbody tr' , $table);
		tr.each(function(){
			if(me.isModified($(this) , table)){
				var data = me.getRowData(table , $(this));
				rows.push(data);
			}
		});
		return rows;
	},
	
	isModified : function($tr , $table){
		var b = false;
		
		$('td' , $tr).each(function(){
			var td = $(this);
			var inputEl = $('input , select' , td)
			var userVal = inputEl.val();
			var originalVal = inputEl.attr('originalValue');
			if(userVal != originalVal){
				b = true;
				return false;
			}
		});
		
		return b;
	},
	
	getRowData : function($table , $tr){
		if(!$table || !$tr){
			return;
		}
		var table = $table.dataTable();
		var row = table.api().row($tr);
		
		var data = row.data();
		
		$('input , select' , $tr).each(function(){
		
			var name = $(this).attr('name');
			var value = $(this).val();
			if(name){
				var obj = {};
				obj[name] = value;
				$.extend(true , data , obj);
			}
		});
		
		return data;
	},
	
	arrayGrid : function($table , config , owner , callback){
		
		var tableDefaults = {
			info : false,
			paging : false,
			searching : false
		};
		
		if(!config.columns){
			alert('columns不存在');
			return;
		}
		
		for(var i = 0 ; i < config.columns.length ; i++){
			var col = {
				defaultContent : ''	
			};
			
			var editor = config.columns[i].editor;
			if(editor && editor.inputType && editor.inputType == 'text'){
				$.extend(true , col , {
					render: function ( data, type, full, meta ) {
						return '<input name="' + meta.settings.aoColumns[meta.col].data + '" type="text" originalValue="'+ data + '" style="width:100%" value="' + data + '"/>';
					}
				});
			}else if(editor && editor.inputType && editor.inputType == 'select'){
				$.extend(true , col , {
					render: function ( data, type, full, meta ) {
						return '<select  name="' + meta.settings.aoColumns[meta.col].data + '" originalValue="'+ data + '" style="width:100%"></select>';
					}
				});
			}else if(editor && editor.inputType && editor.inputType == 'actionCols'){
				$.extend(true , col , {
					render: function ( data, type, full, meta ) {
						var col = meta.settings.aoColumns[meta.col];
						var editor = col.editor;
						var actions = editor.actions;
						
						var t = '';
						if(actions && actions.length > 0){
							for(var i = 0 ; i < actions.length ; i++){
								t += '<button pageAction="'+actions[i].pageAction+'">' + actions[i].text + '</button>'
							}
						}
						
						return t;
					}
				});
			}
			config.columns[i] = $.extend(true , col , config.columns[i]);
		}
		
		if(config.selMode && config.selMode == 'SINGLE'){
		
			config.columns = this.concatArray([{
				data: config.idField , 
				title : '选择' ,
				render: function ( data, type, full, meta ) {
					return '<input name="dataTable_select" type="radio" value="'+ data + '"/>';
				}
			}] , config.columns);
		}else if(config.selMode && config.selMode == 'MULTI'){
		
			config.columns = this.concatArray([{
				data: config.idField , 
				title : '选择' ,
				render: function ( data, type, full, meta ) {
					return '<input name="dataTable_select" type="checkbox" value="'+ data + '"/>';
				}
			}] , config.columns);
		}
		
		var c = $.extend(true , tableDefaults , config);
		
		$table.dataTable(c);
		
		if(owner && owner[callback]){
			owner[callback].apply(data , $table);
		}
	},
	
	renderPager : function($table , result , owner){
		
		var table = $table.dataTable();
		
		var currentPage = parseInt(result.start / result.limit) + 1;
		var totalPage = Math.ceil(result.total / result.limit);
		
		var max = 3;
		
		var forwardSteps = (totalPage - currentPage) > max ? max : (totalPage - currentPage);
		var backwardSteps = (totalPage - 1) > max ? max : (totalPage - 1);
		
		var disabledBackward = currentPage > 1 && currentPage <= totalPage ? '' : 'disabled';
		
		var disabledForward = currentPage >= totalPage ? 'disabled' : '';
		
		var tpl = 
			'<div class="row">' + 
			'	<div class="col-sm-5">'+ 
			'		<div class="dataTables_info" role="status" aria-live="polite">'+
			'			第<%= currentPage %>页，共<%= totalPage%>页  ，每页'+
			'			<select name="limit" class="form-control input-sm" pageAction="loadPage">' + 
			'				<option value="10"  <%= limit == 10 ? "selected" : ""  %> >10</option>' + 
			'				<option value="25"  <%= limit == 25 ? "selected" : ""  %> >25</option>' + 
			'				<option value="50"  <%= limit == 50 ? "selected" : ""  %> >50</option>' + 
			'				<option value="100" <%= limit == 100 ? "selected" : ""  %> >100</option>' + 
			'			</select>' +
			'			共 <%= total%>条' +
			'		</div>' + 
			'	</div>' + 
			'	<div class="col-sm-7">' + 
			'		<div class="dataTables_paginate paging_simple_numbers">' + 
			'			<ul class="pagination">' + 
				'			<li class="<%= disabledBackward%> paginate_button" pageAction="loadPage" params="1,<%= limit %>">' + 
				'				<a href="#" aria-label="Previous"> ' +
				'					<span aria-hidden="true">&laquo;</span>' +
				'				</a>'+
				'			</li>' +
				'			<li  class="<%= disabledBackward%> paginate_button" pageAction="loadPage" params="<%= (currentPage - 1) %>,<%= limit %>">' +
				'				<a href="#" aria-label="Previous">' +
				'					<span aria-hidden="true">&lt;</span>' +
				'				</a>' +
				'			</li>' +
				'   		<% for(var i = backwardSteps ; i >= 0 ; i--){ %>' +
				'   			<% if(currentPage - (i+1) > 0){ %>' +
				'					<li class="paginate_button" pageAction="loadPage" params="<%= (currentPage - (i+1))%>,<%= limit %>" ><a href="#"><%= currentPage - (i+1) %></a></li>' +
				'				<% } %>' +
				'   		<% } %>' +
				'			<li class="active" ><a href="#"><%= currentPage %></a></li>' +
				'   		<% for(var i = 0 ; i < forwardSteps ; i++){ %>' +
				'   			<% if(currentPage + (i+1) <= totalPage){ %>' +
				'					<li class="paginate_button" pageAction="loadPage" params="<%= (currentPage + (i+1))%>,<%= limit %>"><a href="#"><%= currentPage + (i+1) %></a></li>' +
				'   			<% } %>'+
				'   		<% } %>'+
				'			<li class="<%= disabledForward%> paginate_button" pageAction="loadPage" params="<%= (currentPage + 1)%>,<%= limit %>">'+
				'				<a href="#" aria-label="Next">' + 
				'					<span aria-hidden="true">&gt;</span>' +
				'				</a>' +
				'			</li>'+
				'			<li class="<%= disabledForward%> paginate_button" pageAction="loadPage" params="<%= totalPage%>,<%= limit %>"> ' +
				'				<a href="#" aria-label="Next"> '+
				'					<span aria-hidden="true">&raquo;</span>'+
				'				</a>'+
				'			</li>'+
			'			</ul>' + 
			'		</div>' + 
			'	</div>' + 
			'</div>';
		
		var compiled = _.template(tpl);
		
		var tfoot = $('.row:last' , $table.parent());
		tfoot.remove();
		
		tfoot = $(compiled({
			currentPage: currentPage,
			totalPage : totalPage,
			limit : result.limit,
			disabledBackward : disabledBackward,
			disabledForward : disabledForward,
			forwardSteps : forwardSteps,
			backwardSteps : backwardSteps,
			total : result.total
		}));
		table.after(tfoot);
		
		this.regPagerActions(tfoot , owner);
	},
	
	getActionEvent : function($obj){
		var actionEvent = $obj.attr('actionEvent');
		if($obj.is('select') || $obj.is('input')){
			return 'change';
		}
		return 'click';
	},
	
	regPagerActions : function($root , owner){
		var me = this;
		
		var limitSelect = $('select[name=limit]' , $root);
		
		var	actionEvent = this.getActionEvent(limitSelect);
		
		limitSelect[actionEvent](function(){
			var limit = $('option:selected' , $(this)).val();
			var pageAction = $(this).attr('pageAction');
			if(owner && pageAction && owner[pageAction]){
				owner[pageAction].call(owner , '1' , limit);
			}
		});
		
		var li = $('li[pageAction]' , $root);
		li.each(function(){
			
			var	actionEvent = me.getActionEvent($(this));
			
			$(this)[actionEvent](function(){
				if(!$(this).hasClass('disabled') || $(this).attr('disabled') !== true){
					var pageAction = $(this).attr('pageAction');
					var params = $(this).attr('params');
					if(params){
						try{
							params = JSON.parse(params);
						}catch(e){
							params = params.split(',');
						}
					}
					if(owner && pageAction && owner[pageAction]){
						owner[pageAction].apply(owner , params);
					}
				}
			});
		});
	},
	
	regActions : function($root , owner){
		var me = this;
		var el = $('*[pageAction]' , $root);
		el.each(function(){
			var	actionEvent = me.getActionEvent($(this));
			$(this)[actionEvent](function(){
				if(!$(this).hasClass('disabled') || $(this).attr('disabled') !== true){
					var pageAction = $(this).attr('pageAction');
					if(owner && pageAction && owner[pageAction]){
						owner[pageAction].apply(owner);
					}
				}
			});
		});
	},
	
	reloadGrid : function($table , json , owner){
		var table = $table.dataTable();
	    var oSettings = table.fnSettings();
	    table.fnClearTable(oSettings);
	    for (var i = 0 ; i < json.length; i++){
	      table.oApi._fnAddData(oSettings, json[i]);
	    }
	    oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
	    table.fnDraw();
	    
	    this.regGridActions(table , owner);
	},
	
	regGridActions : function($root , owner){
		var me = this;
		var table = $root.dataTable();
		var tr = $('tbody tr' , $root);
		tr.each(function(){
			var line = $(this);
			var li = $('*[pageAction]' , line);
			li.each(function(){
				$(this).click(function(){
					if(!$(this).hasClass('disabled') || $(this).attr('disabled') !== true){
						var pageAction = $(this).attr('pageAction');
						var tr = $(this).parents('tr');
						var data = me.getRowData(table , tr);
						if(owner && pageAction && owner[pageAction]){
							owner[pageAction].call(owner , data , tr , table);
						}
					}
				});
			});
		});
	},
	
	openFormPage : function(url , readParams){
		if(url){
			var newUrl = JQUtils.buildUrl(url , readParams);
			window.location = newUrl;
		}
	},
	
	openListPage : function(url , searchParams){
		if(url){
			var newUrl = JQUtils.buildUrl(url , searchParams);
			window.location = newUrl;
		}
	},
	
	buildTimeline : function($el , data){
		
		var timeline = $( '.timeline', $el);
		if(timeline.length == 0){
			timeline = $( '<ul class="timeline"></ul>');
			$el.append(timeline);
		}
		//找出所有日期
		var dates = this._findDates(data);
		//循环日期构造
		for(var i = 0 ; i < dates.length ; i++){
			this._buildTimelineByDate(timeline , data , dates[i]);
		}
	},
	
	_findDates : function(data){
		var array = [];
		for(var i = 0 ; i < data.length ; i++){
			array.push(data[i].date_date);
		}
		return _.uniq(array);
	},
	
	_buildTimelineByDate : function($el , data , date){
	
		var date = date;
		var records = _.filter(data , function(item){ 
			return item.date_date  == date;
		});
		
		var tpl = 
			'<li class="time-label">'+
				'<span class="bg-red">'+
					'<%=date%>'+
				'</span>'+
			'</li>';
		
		var compiled = _.template(tpl);
		var $timeline = $(compiled({
			date : date
		}));
		$el.append($timeline);
		
		for(var i = 0 ; i < records.length ; i++){
			this._buildTimelineItem($el , records[i]);
		}
	},
	
	_buildTimelineItem : function($el , item){
		var tpl = 
		'<li>'+
			'<i class="fa <%= icon %> bg-blue"></i>'+
			'<div class="timeline-item">'+
				'<span class="time"><i class="fa fa-clock-o"></i><%= to_now %></span>'+
				'<h3 class="timeline-header"><a href="#"><%= title %></a> </h3>'+
				'<div class="timeline-body">'+
					'<%= content %>'+
				'</div>'+
	        '</div>'+
	    '</li>';
		var compiled = _.template(tpl);
		var $timelineItem = $(compiled(item));
		$el.append($timelineItem);
	},
	
	renderText : function(config){
		var tpl = 
			'<div class="col-sm-6 col-md-6 col-xs-12">' +
				'<div class="form-group">' +
		        	'<label class="col-sm-3 control-label"><%= label %></label>' +
		        	'<div class="col-sm-9">' +
		        		'<div class="input-group">' +
							'<input name="<%= name %>" type="text" class="form-control" placeholder="<%= placeholder %>" aria-describedby="basic-addon1">'+
						'</div>' +
		        	'</div>' +
		    	'</div>' +
		    '</div>';
		var compiled = _.template(tpl);
		return $(compiled(config));
	},
	
	renderSelect : function(config){
		var tpl = 
			'<div class="col-sm-6 col-md-6 col-xs-12">' +
				'<div class="form-group">' +
		    		'<label class="col-sm-3 control-label"><%= label %></label>' +
		    		'<div class="col-sm-9">' +
						'<select name="<%= name %>" class="form-control" placeholder="<%= placeholder %>">' +
							'<option value="">(空)</option>' +
							'<% for(var i = 0 ; i < options.length ; i++) {%>' +
								'<option value="<%= options[i].value %>"> <%= options[i].text %> </option>' +
							'<% } %>' +
						'</select>' +
		    		'</div>' +
				'</div>' +
			'</div>';
		var compiled = _.template(tpl);
		return $(compiled(config));
	},
	
	renderCheckbox : function(config){
		var tpl = 
			'<div class="col-sm-6 col-md-6 col-xs-12">' +
				'<div class="form-group">' +
		        	'<label class="col-sm-3 control-label"><%= label %></label>' +
		        	'<div class="col-sm-9">' +
		        		'<div class="checkbox">' +
		        			'<% for(var i = 0 ; i < options.length ; i++ ){ %>' +
			        			'<label>' +
			        				'<input type="checkbox" name="<%= name %>" value="<%= options[i].id %>"> <%= options[i].text %>' +
	   							'</label>' +
   							'<% } %>' +
						'</div>' +
		        	'</div>' +
		    	'</div>' +
		    '</div>';
		var compiled = _.template(tpl);
		return $(compiled(config));
	},
	
	renderTextArea : function(config){ 
		var tpl = 
			'<div class="col-sm-6 col-md-6 col-xs-12">' +
				'<div class="form-group">' +
		        	'<label class="col-sm-3 control-label"><%= label %></label>' +
		        	'<div class="col-sm-9">' +
		        		'<div class="input-group">' +
							'<textarea name="<%= name %>" type="text" class="form-control" placeholder="<%= placeholder %>" aria-describedby="basic-addon1">'+
						'</div>' +
		        	'</div>' +
		    	'</div>' +
		    '</div>';
		var compiled = _.template(tpl);
		return $(compiled(config));
	},
	/**
	 * @param string title
	 * @param sring || jquery object html
	 * @param string url
	 * @param string type
	 * @param boolean autohide
	 * @param [] buttons
	 * @param boolean dragable
	 * @param boolean resizable
	 * @param int x
	 * @param int y
	 * @param int width
	 * @param int height
	 * @param boolean modal
	 * @param boolean animation
	 * 
	 * @return $dialog
	 */
	dialog : function(config){
		
		var c = {
			type : 'success' , 
			message : '',
			width : 400,
			height : 300,
			animation : true,
			title : null,
			buttons : [],
			url : null,
			dragable : true,
			modal : true
		};
		
		$.extend(true , c , config);

		var pageWidth = $(document).width();
		var pageHeight = $(document).height();
		
		var top = (pageHeight / 3) - (c.height / 2);
		var left = (pageWidth / 2) - (c.width / 2);
		
		if(c.modal){
			var mask = $('<div class="mask"></div>');
			$(document.body).append(mask);
		}
		
		var tpl = 
			'<div class="dialog">' +
				'<div class="dialog_header <%= type %>header">' +
					'<div class="dialog_title"><%= title %></div>' +
					'<div class="dialog_close"></div>' +
				'</div>' +
				'<div class="dialog_content <%= type %>"> <%= message %> </div>' +
				'<div class="dialog_bbar <%= type %>header">' + 
					'<% for(var i = 0 ; i < buttons.length ; i++ ) { %>' +
						'<button class="button <%= type %>header" action="<%= buttons[i].text %>" ><%= buttons[i].text %></button>' +
					'<% } %>' +
				'</div>'+
			'</div>';
		var compiled = _.template(tpl);
		
		var dialog = $(compiled(c));
		
		dialog.css('top' , top);
		dialog.css('left' , left);
		
		if(c.width){
			dialog.width(c.width);
		}
		
		if(c.height){
			dialog.height(c.height);
		}
		
		if(c.animation){
			dialog.fadeIn(500);
		}
		$(document.body).append(dialog);
		
		$('.dialog_close' , dialog).click(function(){
			
			if(c.modal){
				$('.mask').remove();
			}
			
			if(c.animation){
				dialog.fadeOut(500);
				dialog.remove();
			}else{
				dialog.remove();
			}
		});
		
		if(c.dragable){
			$('.dialog_header' , dialog).mousedown(function(event){
				$(this).css('cursor' , 'move');
				dialog.clientY = event.clientY;
				dialog.clientX = event.clientX;
				
				dialog.origalTop = dialog.offset().top;
				dialog.origalLeft = dialog.offset().left;
				
			});
			
			$('body').mousemove(function(event){
				
				if($('.dialog_header' , dialog).css('cursor') == 'move'){
					
					var dy = event.clientY - dialog.clientY;
					var dx = event.clientX - dialog.clientX;
					
					dialog.css('top' , dialog.origalTop + dy);
					dialog.css('left' , dialog.origalLeft + dx);
				}
				
			});
			
			$('body').mouseup(function(event){
				$('.dialog_header' , dialog).css('cursor' , 'default');
			});
		}
		
		var content = $('.dialog_content'  , dialog);
		
		if(c.url){
			var iframe = $('<iframe src="' + c.url + '"/>');
			iframe.css('border' , 0);
			iframe.width(content.width());
			content.append(iframe);
		}else if(c.message){
			content.append(c.message);
		}
		
		$('button' , dialog).click(function(){
			var action = $(this).attr('action');
			var btn = _.find(c.buttons , function(item){ 
				return item.text == action; 
			});
			
			if(btn){
				btn.handler($(this));
			}
		});
		
		return dialog;
	}
	
};