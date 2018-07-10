var KendoUtils = {
	 
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
    		angular.extend(rootNode , {items : childNodes , leaf : false , text : rootNode.name , expanded : true});
    	}else{
    		angular.extend(rootNode , {leaf : true , text : rootNode.name});
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
    			root.push(nodes[i]);
    		}
    	}
    	return root;
    },
    
    buildParams : function(params){
    	if(params){
			for(var p in params){
				if(_.isArray(params[p]) || _.isObject(params[p])){
					params[p] = JSON.stringify(params[p]);
				}
			}
		}
    	return params;
    },
    
    createDataSource : function(override){
    	
    	var c = {
    		allowEmptyOption : false,
			dataType: "json",
			pageSize : 25,
			serverPaging: true,
            serverSorting: true,
            sortable: true,
            pageable: true,
            schema: {
                data: "data",
                total : "total"
            }
    	};
    	
    	$.extend(true , c , override);
    	
    	return new kendo.data.HierarchicalDataSource({
            transport: {
            	read: function(options) {
            		var sort = c.sort || [] ;
            		options.data.queryInfo =  options.data.queryInfo || (c.scope ? c.scope.queryInfo : {});
//            		options.data.start  =  options.data.start || 0;
//            		options.data.skip  =  options.data.skip || 0;
//            		options.data.page  =  options.data.page || 1;
//            		options.data.take  =  options.data.take || c.pageSize;
//            		options.data.pageSize  =  options.data.pageSize || c.pageSize;
            		if(options.data.sort){
            			for(var i = 0 ; i < options.data.sort.length ; i++){
            				sort.push({
            					property : options.data.sort[i].field,
            					direction : options.data.sort[i].dir
            				});
            			}
            		}
            		var data = options.data;
            		
            		var queryInfo = $.extend({} , c.queryInfo, options.data.queryInfo);
            		
            		$.extend(data , {
            			start : options.data.skip,
            			limit : options.data.take,
            			sort : sort,
            			queryInfo : queryInfo
            		});
            		
            		var params = KendoUtils.buildParams(data);
            		
    				$.ajax({
    					url: c.url,
    					dataType: c.dataType, // "jsonp" , "json"
    					data : params,
    					success: function(result) {
    						if(c.allowEmptyOption){
    							result.data = _.union([{id:'',name:'(空)'}] , result.data);
    						}
    						options.success(result);
    					},
    					error: function(result) {
    						options.error(result);
    					}
    				});
            	}
            },
            schema: c.schema,
            pageSize: c.pageSize,
            serverPaging: c.serverPaging,
            serverSorting: c.serverSorting
		});
    },
    
    listApp : function($scope , override){
    	
    	var c = {
    		scope : $scope,	
    		//selectable : "multiple, row",
			dataType: "json",
			pageSize : 25,
			serverPaging: true,
            serverSorting: true,
            sortable: true,
            pageable: true,
            schema: {
                data: "data",
                total : "total"
            },
            doSearch : function(){
            	event.preventDefault();
            	this.dataSource.page(1);
            },
            
            doDelete : function(e , grid){
            	
            	if(!grid){
            		return;
            	}
            	
            	var me = this;
            	event.preventDefault();
            	var dataItem = grid.dataItem($(e.currentTarget).closest("tr"));
            	
            	if(me.deleteUrl){
            		$.post(me.deleteUrl , {id : dataItem.id} , function(){
            			me.doSearch();
            		} , 'json' );
            	}
            },
            
            doCreate : function(readParams , defaultProps){
            	var me = this;
            	var positionY = (event ? event.clientY : 0) - (me.detailConfig.height || 400)/2;
            	positionY = positionY < 0 ? 10 : positionY;
            	
            	if(me.detailConfig.url){
            		KendoUtils.iframeWin({
    					url : me.detailConfig.url,
    					readParams : readParams || {id : -1},
    					defaultProps : defaultProps || me.detailConfig.defaultProps
    				},{
    					title : me.detailConfig.title || '编辑',
    					height : me.detailConfig.height || 400,
    					width : me.detailConfig.width || 450,
    					y :  positionY,
    					close : function(){
    						$scope.doSearch();
    					}
    				});
            	}
            },
            
            showDetails : function (e , grid){
            	var me = this;
            	var readParams;
            	
            	if(!me.detailConfig){
            		return;
            	}
            	
            	if(grid){
            		e.preventDefault();
                   	var dataItem = grid.dataItem($(e.currentTarget).closest("tr"));
                   	readParams = {id : dataItem.id};
            	}else {
            		readParams = {id : null};
            	}
            	
            	var positionY = (e ? e.clientY : 0) - (me.detailConfig.height || 400)/2;
            	positionY = positionY < 0 ? 10 : positionY;
            	
            	if(me.detailConfig.url){
            		KendoUtils.iframeWin({
    					url : me.detailConfig.url,
    					readParams : readParams
    				},{
    					title : me.detailConfig.title || '编辑',
    					height : me.detailConfig.height || 400,
    					width : me.detailConfig.width || 450,
    					y :  positionY,
    					close : function(){
    						$scope.doSearch();
    					}
    				});
            	}
            },
            
            doAction : function(url , p , successFn , failFn ){
            	var params = KendoUtils.buildParams(p);
            	KendoUtils.mask();
            	$.post( url , params , function(result) {
            		KendoUtils.unmask();
            		if(result.success){
            			if(successFn){
            				successFn(result.data);
            			}
            		}else{
            			
            			if(failFn){
            				failFn(result.data);
            			}
            		}
        		} , c.dataType);
            },
    	};
    	
    	$.extend(true , c , override);
    	
    	$.extend(true , $scope , c);
    	
    	if(!c.url){
    		alert("please provide a read url!!");
    		return;
    	}
    	
    	if(!c.gridOptions){
    		alert("please provide property 'gridOptions' !!");
    		return;
    	}
    	
    	if(!c.columns){
    		alert("please provide an array 'columns' !!");
    		return;
    	}
    	
		var dataSource = this.createDataSource(c);
		
		$scope.dataSource = dataSource;

        $scope[c.gridOptions] = {
            dataSource: dataSource,
            sortable: c.sortable,
            pageable: c.pageable,
            columns: c.columns,
            selectable : c.selectable
        };
        
        
    },
    
    formApp : function($scope, override){
    	var $scope = $scope;
    	var c = {
    		model : {},	
			dataType: "json",
			readUrl : '',
			submitUrl : '',
			
			beforeSave : function(){},
			
            doSave : function(model , form){
            	this.errorFields = [];
            	this.doAction(c.submitUrl , model , form);
            },
            
            doAction : function(url , model , form){
            	if(form && form.$invalid){
            		return;
            	}
            	this.beforeSave();
            	
            	var params = KendoUtils.buildParams(model);
            	KendoUtils.mask();
            	$.post( url , params , function(result) {
            		KendoUtils.unmask();
            		
            		if(result.success){
            			$.extend($scope.model , result.data);
        				$scope.$apply();
        				$scope.afterModelLoad();
            		}else{
            			$.extend($scope , {errorFields : result.errorFields});
        				$scope.$apply();
        				if(result.errorMsg){
        					alert(result.errorMsg);
        				}
            		}
            		
        		} , c.dataType);
            },
            
            doRead : function(){
            	
            	var data = $.extend(true, {} , c.readParams , {defaultProps : JSON.stringify(c.defaultProps)});
            	
            	$.ajax({
        			url: c.readUrl,
        			dataType: c.dataType, // "jsonp", "json"
        			data : data,
        			success: function(result) {
        				$.extend($scope.model , result.data);
        				$scope.$apply();
        				$scope.afterModelLoad();
        			},
        			error: function(result) {
        			}
        		});
            },
            afterModelLoad : function(){
            	
            }
    	};
    	
    	$.extend(true , c , override);
    	
    	$.extend(true , $scope , c);
    	
    	if(!c.readUrl){
    		alert("please provide a read url!!");
    		return;
    	}
    	
    	$scope.doRead();
    	
    },
    
    win: function(item , override){
    	var c = {
    		height : $(document).height() - 100,
    		width : 600,
    		title : 'title',
    		draggable: true,
    		modal: true,
    		resizable: true,
    		actions: [
				"Minimize",
				"Maximize",
				"Close"
			],
			x : 50,
    		y : 50
    	}
    	
    	$.extend(true ,c ,override);
    	
    	if(item){
    		$('body').prepend(item);
    		var win = item.kendoWindow(c).parents('.k-window');
    		win.css('left' , c.x);
    		win.css('top' , c.y);
    	}
		return win;
    },
    
    iframeWin : function(config , override){
    	
    	var url = config.url;
    	if(config.readParams){
    		var readParams = JSON.stringify(this.buildParams(config.readParams));
    		if(url.indexOf('?') > 0){
    			url = url + '&readParams=' + encodeURI(readParams);
    		}else{
    			url = url + '?readParams=' + encodeURI(readParams);
    		}
    	}
    	
    	if(config.defaultProps){
    		var defaultProps = JSON.stringify(this.buildParams(config.defaultProps));
    		if(url.indexOf('?') > 0){
    			url = url + '&defaultProps=' + encodeURI(defaultProps);
    		}else{
    			url = url + '?defaultProps=' + encodeURI(defaultProps);
    		}
    	}
    	
    	var item = $(
    		'<div class="content">'+
				'<iframe src="'+ url +'"/>'+
			'</div>'
		);
		var win = this.win(item , override);
		$('.content > iframe' , win).height(win.height());
		return win;
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
	}
    
};