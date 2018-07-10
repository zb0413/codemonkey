Ext.define('AM.override.ExtFix', {
	
	fix : function(){

//		Ext.define('OverrideConnection', {
//		    override: 'Ext.data.Connection',
//		    onStateChange : function(request) {
//		        if (request && request.xhr && request.xhr.readyState == 4) {
//		            this.clearTimeout(request);
//		            this.onComplete(request);
//		            this.cleanup(request);
//		        }
//		    }
//		});
		
		//textfield 默认长度设置120
		Ext.define('OverrideText' , {
			override: 'Ext.form.field.Text',
			
			maxLength : 120,
			
			enableKeyEvents : true,
			
			trigger1Cls: Ext.baseCSSPrefix + 'form-clear-trigger',
			
			onTrigger1Click : function(){
				this.setValue(null);
			},
			
			listeners : {
				keyup : function(field , e, eOpts ){
					if(field.readOnly){
						Ext.EventManager.stopEvent( e );
					}
				},
				
				keydown: function(field , e, eOpts ){
					if(field.readOnly){
						Ext.EventManager.stopEvent( e );
					}
				}
			}
			
		});
		
		Ext.define('OverrideTextArea' , {
			override: 'Ext.form.field.TextArea',
			maxLength : 5000
		});
		
		//ext6.0 求和显示，默认底部显示
		Ext.define('OverrideGridSummary' , {
			override: 'Ext.grid.feature.Summary',
	        dock : 'bottom'
		});
		
		//ext6.0 去掉panel默认边框
		Ext.define('OverridePanelConfig' , {
			override: 'Ext.panel.Panel',
	    	border : false
		});
		
		//ext6.0 Checkbox选择列宽度
		Ext.define('OverrideCheckboxModel' , {
			override: 'Ext.selection.CheckboxModel',
	    	headerWidth  : 30
		});
		
		//datefield 默认日期格式
		Ext.define('OverrideDate' , {
			override: 'Ext.form.field.Date',
			
			format : 'Y-m-d',
			
			triggers : {
				clear: {
			        cls: Ext.baseCSSPrefix + 'form-clear-trigger',
			        handler: function() {
			            this.setValue(null);
			        }
			    }
			}
			
		});
		
		//numberfield 默认精度4位小数
		Ext.define('OverrideNumberField' , {
			override: 'Ext.form.field.Number',
			
			decimalPrecision : 4,
			maxValue : 999999999999999,
			
			triggers : {
				clear: {
			        cls: Ext.baseCSSPrefix + 'form-clear-trigger',
			        handler: function() {
			            this.setValue(null);
			        }
			    }
			},
			
		    fixPrecision : function(value) {
	    	    var me = this;
		    	
	            var nan = isNaN(value);
	            
	            precision = me.decimalPrecision;
		
		        if (nan || !value) {
		            return nan ? '' : value;
		        }
		        
		        if (!me.allowDecimals || precision <= 0) {
		            precision = 0;
		        }
		        
		        if(!me.allowDecimals){
		    		return parseInt(value);
		    	}

		        return parseFloat(value);
		    },
		    
		    listeners : {
		    	change : function(cmp, newValue, oldValue, eOpts){
		    		
//		    		if(newValue === null || oldValue === null){
//		    			return;
//		    		}
//		    		
//		    		if(this.decimalPrecision){
//		    			var exp = "^([+-]?)[0-9]*(.[0-9]{1," + this.decimalPrecision + "})?$";
//		    			var reg = new RegExp(exp);
//		    			if(!reg.test(newValue)){
//		    				Ext.Msg.alert('错误' , '该字段只允许输入最多' + this.decimalPrecision + '位小数，请检查输入格式');
//		    				this.setValue(oldValue);
//		    			}
//		    		}
//		    		
//		    		if((this.maxValue || this.maxValue === 0 ) && newValue > this.maxValue){
//		    			Ext.Msg.alert('错误' , Ext.String.format(this.maxText , this.maxValue));
//		    			this.setValue(oldValue);
//		    		}
//		    		
//		    		if((this.minValue || this.minValue === 0 ) && newValue < this.minValue){
//		    			Ext.Msg.alert('错误' , Ext.String.format(this.minText , this.minValue));
//		    			this.setValue(oldValue);
//		    		}
		    		
		    	}
		    }
		});
		
		//LabelStyle 设置对齐方式
		Ext.define('OverrideLabelStyle' , {
			override: 'Ext.form.field.Text',
			
			labelStyle : 'text-align:right;'
			
		});
		
		//actioncolumn 默认没有下拉菜单,宽度80
		Ext.define('OverrideActionColumn' , {
			override: 'Ext.grid.column.Action',
			menuDisabled : true,
			width : 80,
			align : 'center'
		});
		
		//datapicker提示信息
		Ext.define('OverrideDataPicker', {
		    override: 'Ext.picker.Date',
		    ariaTitleDateFormat: 'yyyy-MM-dd',
		    todayTip : '{0} (Spacebar)',
		    monthYearFormat: 'Y-m'
		});
		
		Ext.define('OverrideLoadMask', {
		    override: 'Ext.LoadMask',
		    
		    setZIndex: function(index) {
		        var me = this,
		            owner = me.activeOwner;
		            
		        if (owner) {
		        	var zIndex = owner.el.getStyle('zIndex');
		        	//fix IE zIndex problem
		        	if(zIndex == 'auto'){
		        		zIndex = 0;
		        	}else{
		        		zIndex = parseInt(zIndex);
		        	}
		        	
		            index = parseInt(zIndex , 10) + 1;
		        }

		        me.getMaskEl().setStyle('zIndex', index - 1);
		        return me.mixins.floating.setZIndex.apply(me, arguments);
		    }
		});

//		Ext.form.field.Base.prototype.initComponent = function(){
//			var me = this; 
//			Ext.form.field.Base.superclass.initComponent.call(this);
//			me.subTplData = me.subTplData || {};
//			this.addEvents('focus','blur','specialkey');
//			// Init mixins
//			me.initLabelable();
//			me.initField();
//			// Default name to inputId
//			if (!me.name) {
//				me.name = me.getInputId();
//			} 
//			if(this.allowBlank === false && this.fieldLabel){
//				this.fieldLabel += '<font color=red>*</font>';
//			}
//			if(this.readOnly === true && this.fieldLabel){
//				this.fieldLabel += '<font color=blue>*</font>';
//			}
//		};
		
//		Ext.define('OverrideGridColumn', {
//		    override: 'Ext.grid.column.Column',
//		    
//		    align : 'center'
//		});
//		去掉影响列分组
//		Ext.define('OverrideGridColumn', {
//		    override: 'Ext.grid.column.Column',
//		    height : 25
//		});

//		//searchingselect 组件在grid中显示隐藏的text列
//		Ext.define('OverrideGridColumn', {
//		    override: 'Ext.grid.column.Column',
//		   
//		    renderer : function(value  , metaData , record , rowIndex , colIndex , store , view ){
//		    	
//		    	if(this.xtype == 'gridpanel'){
//		    		var column = this.columns[colIndex];
//			    	if(column.textDataIndex){
//			    		return record.get(column.textDataIndex);
//			    	}
//		    	}
//		    	
//		    	return ExtUtils.encodeHtml(value);
//			}
//		    
//		});
		
		this.addExportExcelFunctionToGrid();
		
		this.fixTreeStoreOnNodeAdded();
		
		this.addRedStartToAllowBlankField();
		
		this.fixUpdateIndexProblem();
		
//		this.addGroupingSort();
	},
	
//	addGroupingSort : function(){
//		
//		/**
//		  * 定义降序的groupingStore
//		  */
//		Ext.define('GroupingStore', {
//			override : 'Ext.data.GroupingStore',
//			
//			groupDir : 'ASC',
//			groupBy : function(field, forceRegroup, direction) {
//			    direction = direction ? (String(direction).toUpperCase() == 'DESC' ? 'DESC' : 'ASC') : this.groupDir;
//			    if (this.groupField == field && this.groupDir == direction && !forceRegroup) {
//			          return;
//			    }
//			    this.groupField = field;
//			    this.groupDir = direction;
//			    if (this.remoteGroup) {
//					if (!this.baseParams) {
//						this.baseParams = {};
//					}
//					this.baseParams['groupBy'] = field;
//					this.baseParams['groupDir'] = direction;
//			    }
//			    if (this.groupOnSort) {
//			    	this.sort(field, direction);
//			    	return;
//			    }
//			    if(this.remoteGroup) {
//			    	this.reload();
//			    } else {
//		            var si = this.groupSortInfo || {};
//		            if (si.field != field || si.direction != direction) {
//		                this.applySort();
//		            } else {
//		                this.sortData(field, direction);
//		            }
//		            this.fireEvent('datachanged', this);
//		        }
//		    },
//		    applySort : function() {
//		        Ext.data.GroupingStore.superclass.applySort.call(this);
//		        if (!this.groupOnSort && !this.remoteGroup) {
//		            if (this.groupField != this.groupSortInfo.field
//		                    || this.groupDir != this.groupSortInfo.direction) {
//		                this.sortData(this.groupField, this.groupDir);
//		            }
//		        }
//		    },
//		    applyGrouping : function(alwaysFireChange) {
//		        if (this.groupField !== false) {
//		            this.groupBy(this.groupField, true, this.groupDir);
//		            return true;
//		        } else {
//		            if (alwaysFireChange === true) {
//		                this.fireEvent('datachanged', this);
//		            }
//		            return false;
//		        }
//		    }
//		});
//		
//	},
	
	fixUpdateIndexProblem : function(){
	
		if(Ext.getVersion('core') >= '6.0.0'){
		
			Ext.define('OverrideExtView', {
				override : 'Ext.view.View',
			
				updateIndexes: function(startIndex, endIndex) {
			        var nodes = this.all.elements,
			            node,
			            records = this.getViewRange(),
			            i,
			            myId = this.id;
			        startIndex = startIndex || 0;
			        endIndex = endIndex || ((endIndex === 0) ? 0 : (nodes.length - 1));
			        for (i = startIndex; i <= endIndex; i++) {
			            node = nodes[i];
			            
			            //fix records[i] is null
			            if(records[i]){
				            node.setAttribute('data-recordIndex', i);
				            node.setAttribute('data-recordId', records[i].internalId);
				            node.setAttribute('data-boundView', myId);
			            }
			        }
			    }
			
			});
		}
		
	},
	
	addRedStartToAllowBlankField : function(){
		Ext.define('OverrideField', {
			override : 'Ext.form.field.Base',
			initComponent : function() {
				if (this.allowBlank !== undefined && !this.allowBlank) {
					if (!this.labelSeparator) {
						this.labelSeparator = "";
					}
					this.labelSeparator += '<span style="color:red">*</span>';
				}
				this.callParent(arguments);
			}
		});
	},
	
	fixTreeStoreOnNodeAdded : function(){
		//修复tree store 查询后 setRootNode 产生js错误
		Ext.define('OverrideTreeStore', {
		    override: 'Ext.data.TreeStore',
		   
		    onNodeAdded: function(parent, node) {
		        var me = this,
		            reader = me.proxy.getReader(),
		            data = node.raw || node[node.persistenceProperty],
		            dataRoot,
		            isVisible;

		        if (me.filterFn) {
		            isVisible = me.filterFn(node);
		            node.set('visible', isVisible);

		            
		            if (isVisible) {
		            	//start
		            	if(parent){
		            		parent.set('visible', me.filterFn(parent));
		            	}
		            	//end
		            }
		        }

		        Ext.Array.remove(me.removed, node);
		        node.join(me);

		        if (!node.isLeaf() && !node.isLoaded() && !me.lazyFill) {
		            dataRoot = reader.getRoot(data);
		            if (dataRoot) {
		                me.fillNode(node, reader.extractData(dataRoot));
		            }
		        }

		        if (me.autoSync && !me.autoSyncSuspended && (node.phantom || node.dirty)) {
		            me.sync();
		        }
		    }
		    
		});
	},
	
	addExportExcelFunctionToGrid : function(){
		var Base64 = (function() {
		    // Private property
		    var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		    // Private method for UTF-8 encoding
		    function utf8Encode(string) {
		        string = string.replace("//r/n/g","/n");
		        var utftext = "";
		        for (var n = 0; n < string.length; n++) {
		            var c = string.charCodeAt(n);
		            if (c < 128) {
		                utftext += String.fromCharCode(c);
		            }
		            else if((c > 127) && (c < 2048)) {
		                utftext += String.fromCharCode((c >> 6) | 192);
		                utftext += String.fromCharCode((c & 63) | 128);
		            }
		            else {
		                utftext += String.fromCharCode((c >> 12) | 224);
		                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
		                utftext += String.fromCharCode((c & 63) | 128);
		            }
		        }
		        return utftext;
		    }
		    // Public method for encoding
		    return {
		        encode : (typeof btoa == 'function') ? function(input) {
		            return btoa(utf8Encode(input));
		        } : function (input) {
		            var output = "";
		            var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		            var i = 0;
		            input = utf8Encode(input);
		            while (i < input.length) {
		                chr1 = input.charCodeAt(i++);
		                chr2 = input.charCodeAt(i++);
		                chr3 = input.charCodeAt(i++);
		                enc1 = chr1 >> 2;
		                enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		                enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		                enc4 = chr3 & 63;
		                if (isNaN(chr2)) {
		                    enc3 = enc4 = 64;
		                } else if (isNaN(chr3)) {
		                    enc4 = 64;
		                }
		                output = output +
		                keyStr.charAt(enc1) + keyStr.charAt(enc2) +
		                keyStr.charAt(enc3) + keyStr.charAt(enc4);
		            }
		            return output;
		        }
		    };
		})();
		Ext.override(Ext.grid.GridPanel, {
			
			escapeXml : function(v){
				var htmlEscapes = {
				  '&': '&amp;',
				  '<': '&lt;',
				  '>': '&gt;',
				  '"': '&quot;',
				  "'": '&#x27;',
				  '/': '&#x2F;'
				};

				// Regex containing the keys listed immediately above.
				var htmlEscaper = /[&<>"'\/]/g;
						
				return ('' + v).replace(htmlEscaper, function(match) {
				    return htmlEscapes[match];
				});
			},
		    getExcelXml: function(includeHidden) {
		        var worksheet = this.createWorksheet(includeHidden);
		        var totalWidth = 0;
		        for(var i = 0 ; i < this.columns.length ; i++){
		        	totalWidth += this.columns[i].getWidth();
		        }
		        var excelTitle = "";
		        if(typeof(this.title) != "undefined" && this.title != ""){
		            excelTitle = this.title;
		        }else{
		            excelTitle = "导出数据";
		        }
		        return '<xml version="1.0" encoding="utf-8">' +
		            '<ss:Workbook xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns:o="urn:schemas-microsoft-com:office:office">' +
		            '<o:DocumentProperties><o:Title>' + excelTitle+ '</o:Title></o:DocumentProperties>' +
		            '<ss:ExcelWorkbook>' +
		            '<ss:WindowHeight>' + worksheet.height + '</ss:WindowHeight>' +
		            '<ss:WindowWidth>' + worksheet.width + '</ss:WindowWidth>' +
		            '<ss:ProtectStructure>False</ss:ProtectStructure>' +
		            '<ss:ProtectWindows>False</ss:ProtectWindows>' +
		            '</ss:ExcelWorkbook>' +
		            '<ss:Styles>' +
		            '<ss:Style ss:ID="Default">' +
		            '<ss:Alignment ss:Vertical="Top" ss:WrapText="1" />' +
		            '<ss:Font ss:FontName="arial" ss:Size="10" />' +
		            '<ss:Borders>' +
		            '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Top" />' +
		            '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Bottom" />' +
		            '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Left" />' +
		            '<ss:Border ss:Color="#e4e4e4" ss:Weight="1" ss:LineStyle="Continuous" ss:Position="Right" />' +
		            '</ss:Borders>' +
		            '<ss:Interior />' +
		            '<ss:NumberFormat />' +
		            '<ss:Protection />' +
		            '</ss:Style>' +
		            '<ss:Style ss:ID="title">' +
		            '<ss:Borders />' +
		            '<ss:Font />' +
		            '<ss:Alignment ss:WrapText="1" ss:Vertical="Center" ss:Horizontal="Center" />' +
		            '<ss:NumberFormat ss:Format="@" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:ID="headercell">' +
		            '<ss:Font ss:Bold="1" ss:Size="10" />' +
		            '<ss:Alignment ss:WrapText="1" ss:Horizontal="Center" />' +
		            '<ss:Interior ss:Pattern="Solid" ss:Color="#A3C9F1" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:ID="even">' +
		            '<ss:Interior ss:Pattern="Solid" ss:Color="#CCFFFF" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="even" ss:ID="evendate">' +
		            '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="even" ss:ID="evenint">' +
		            '<ss:NumberFormat ss:Format="0" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="even" ss:ID="evenfloat">' +
		            '<ss:NumberFormat ss:Format="0.00" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:ID="odd">' +
		            '<ss:Interior ss:Pattern="Solid" ss:Color="#CCCCFF" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="odd" ss:ID="odddate">' +
		            '<ss:NumberFormat ss:Format="yyyy-mm-dd" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="odd" ss:ID="oddint">' +
		            '<ss:NumberFormat ss:Format="0" />' +
		            '</ss:Style>' +
		            '<ss:Style ss:Parent="odd" ss:ID="oddfloat">' +
		            '<ss:NumberFormat ss:Format="0.00" />' +
		            '</ss:Style>' +
		            '</ss:Styles>' +
		            worksheet.xml +
		            '</ss:Workbook>';
		    },
		    createWorksheet: function(includeHidden) {
		        // Calculate cell data types and extra class names which affect formatting
		        var cellType = [];
		        var cellTypeClass = [];
		        var cm = this.columns;
		        var totalWidthInPixels = 0;
		        var colXml = '';
		        var headerXml = '';
		        var visibleColumnCountReduction = 0;
		        var colCount = cm.length;
		        for (var i = 0; i < colCount; i++) {
		            if ((cm[i].dataIndex && cm[i].dataIndex != '')  && (includeHidden || !cm[i].hidden)) {
		                var w = cm[i].getWidth();
		                totalWidthInPixels += w;
		                if (cm[i].text === ""){
		                	cellType.push("None");
		                	cellTypeClass.push("");
		                	++visibleColumnCountReduction;
		                }
		                else
		                {
		                    colXml += '<ss:Column ss:AutoFitWidth="1" ss:Width="' + w + '" />';
		                    headerXml += '<ss:Cell ss:StyleID="headercell">' +
		                        '<ss:Data ss:Type="String">' + this.escapeXml(cm[i].text) + '</ss:Data>' +
		                        '<ss:NamedCell ss:Name="Print_Titles" /></ss:Cell>';
//		                    var fld = this.store.recordType.prototype.fields.get(cm[i].dataIndex);
		                    var fld = {};
		                    switch(fld.type) {
		                        case "int":
		                            cellType.push("Number");
		                            cellTypeClass.push("int");
		                            break;
		                        case "float":
		                            cellType.push("Number");
		                            cellTypeClass.push("float");
		                            break;
		                        case "bool":
		                        case "boolean":
		                            cellType.push("String");
		                            cellTypeClass.push("");
		                            break;
		                        case "date":
		                            cellType.push("DateTime");
		                            cellTypeClass.push("date");
		                            break;
		                        default:
		                            cellType.push("String");
		                            cellTypeClass.push("");
		                            break;
		                    }
		                }
		            }
		        }
		        var visibleColumnCount = cellType.length - visibleColumnCountReduction;
		        var result = {
		            height: 9000,
		            width: Math.floor(totalWidthInPixels * 30) + 50
		        };
		        var excelTitle = "";
		        if(typeof(this.title) != "undefined" && this.title != ""){
		            excelTitle = this.title;
		        }else{
		            excelTitle = "导出数据";
		        }
		        // Generate worksheet header details.
		        var expandedRowCount = (this.store.getCount() + 2);
		        if(this.store.getGroups() && this.store.getGroups().length > 1){
		        	expandedRowCount += this.store.getGroups().length * 2;
		        }
		        
		        var t = '<ss:Worksheet ss:Name="' + excelTitle+ '">' +
		            '<ss:Names>' +
		            '<ss:NamedRange ss:Name="Print_Titles" ss:RefersTo="=\'' + excelTitle+ '\'!R1:R2" />' +
		            '</ss:Names>' +
		            '<ss:Table x:FullRows="1" x:FullColumns="1"' +
		            ' ss:ExpandedColumnCount="' + (visibleColumnCount + 2) +
		            '" ss:ExpandedRowCount="' + expandedRowCount + '">' +
		            colXml +
		            '<ss:Row ss:Height="38">' +
		            '<ss:Cell ss:StyleID="title" ss:MergeAcross="' + (visibleColumnCount - 1) + '">' +
		            '<ss:Data xmlns:html="http://www.w3.org/TR/REC-html40" ss:Type="String">' +
		            '<html:B>导出结果</html:B></ss:Data><ss:NamedCell ss:Name="Print_Titles" />' +
		            '</ss:Cell>' +
		            '</ss:Row>' +
		            '<ss:Row ss:AutoFitHeight="1">' +
		            headerXml +
		            '</ss:Row>';
		        // Generate the data rows from the data in the Store
		        if(this.store.getGroups() && this.store.getGroups().length > 1){
		        
		        	t += this.appendByGroup(includeHidden , cellType , cellTypeClass);
		        	
		        }else{
		        	for (var i = 0, it = this.store.data.items, l = it.length; i < l; i++) {
			            t += '<ss:Row>';
			            var cellClass = (i & 1) ? 'odd' : 'even';
			            r = it[i].data;
			            var k = 0;
			            for (var j = 0; j < colCount; j++) {
			                if ((cm[j].dataIndex && cm[j].dataIndex != '') && (includeHidden || !cm[j].hidden)) {
			                    var v = r[cm[j].dataIndex];
			                    if (cellType[k] !== "None") {
			                        t += '<ss:Cell ss:StyleID="' + cellClass + cellTypeClass[k] + '"><ss:Data ss:Type="' + cellType[k] + '">';
			                        if (cellType[k] == 'DateTime') {
			                            t += v.format('Y-m-d');
			                        } else {
			                            t += this.escapeXml(v);
			                        }
			                        t +='</ss:Data></ss:Cell>';
			                    }
			                    k++;
			                }
			            }
			            t += '</ss:Row>';
			        }
		        }
		        
		        result.xml = t + '</ss:Table>' +
		            '<x:WorksheetOptions>' +
		            '<x:PageSetup>' +
		            '<x:Layout x:CenterHorizontal="1" x:Orientation="Landscape" />' +
		            '<x:Footer x:Data="Page &P of &N" x:Margin="0.5" />' +
		            '<x:PageMargins x:Top="0.5" x:Right="0.5" x:Left="0.5" x:Bottom="0.8" />' +
		            '</x:PageSetup>' +
		            '<x:FitToPage />' +
		            '<x:Print>' +
		            '<x:PrintErrors>Blank</x:PrintErrors>' +
		            '<x:FitWidth>1</x:FitWidth>' +
		            '<x:FitHeight>32767</x:FitHeight>' +
		            '<x:ValidPrinterInfo />' +
		            '<x:VerticalResolution>600</x:VerticalResolution>' +
		            '</x:Print>' +
		            '<x:Selected />' +
		            '<x:DoNotDisplayGridlines />' +
		            '<x:ProtectObjects>False</x:ProtectObjects>' +
		            '<x:ProtectScenarios>False</x:ProtectScenarios>' +
		            '</x:WorksheetOptions>' +
		            '</ss:Worksheet>';
		        return result;
		    },
		    
		    appendByGroup : function(includeHidden , cellType , cellTypeClass){
		    	
		    	var cm = this.columns;
		        var colCount = cm.length;
		        
		    	var rows = "";
		    	
		    	var groups = this.store.getGroups().items;
		    	for(var i = 0 ; i < groups.length ; i++){
		    		//添加分组头
		    		var headerRow = '<ss:Row>';
		    		headerRow += '<ss:Cell><ss:Data ss:Type="String">';
			        headerRow +=  this.escapeXml(groups[i]._groupKey);
			        headerRow += '</ss:Data></ss:Cell>';
		    		headerRow += '</ss:Row>';
		    		
		    		rows += headerRow;
		    		
		    		//添加分组明细
		    		var children = groups[i].items;
		    		for(var j = 0 ; j < children.length ; j++){
		    			var detailRow = '<ss:Row>';
			            var cellClass = (i & 1) ? 'odd' : 'even';
			            var r = children[j].data;
			            for (var k = 0; k < colCount; k++) {
			                if ((cm[k].dataIndex && cm[k].dataIndex != '') && (includeHidden || !cm[k].hidden)) {
			                    var v = r[cm[k].dataIndex];
			                    if (cellType[k] !== "None") {
			                        detailRow += '<ss:Cell ss:StyleID="' + cellClass + (cellTypeClass[k] || '' )+ '"><ss:Data ss:Type="String">';
			                        if (cellType[k] == 'DateTime') {
			                            detailRow += v.format('Y-m-d');
			                        } else {
			                            detailRow += this.escapeXml(v);
			                        }
			                        detailRow +='</ss:Data></ss:Cell>';
			                    }
			                }
			            }
			            detailRow += '</ss:Row>';
			            
			            rows += detailRow;
		    		}
		    		//添加分组小计
		    		rows += this.buildSummaryRow(cm , groups[i] , includeHidden);
		    	}
		    	
		    	return rows;
		    },
		    
		    buildSummaryRow : function(cm , group , includeHidden){
		    
		    	var summaryRow = '<ss:Row>';
		    	for(var i = 0 ; i < cm.length ; i++){
		    		if ((cm[i].dataIndex != '') && (includeHidden || !cm[i].hidden)) {
			                    
			            if(cm[i].summaryType && cm[i].summaryType == 'sum'){
				            var sum = 0;
				            for(var j = 0 ; j < group.items.length ; j++){
				            	sum += parseFloat(group.items[j].data[cm[i].dataIndex] || "0");
				            }
				            summaryRow += '<ss:Cell><ss:Data ss:Type="String">';
					        summaryRow +=  this.escapeXml(sum);
					        summaryRow += '</ss:Data></ss:Cell>';
				    	}else{
				    		summaryRow += '<ss:Cell><ss:Data ss:Type="String"></ss:Data></ss:Cell>';
				    	}        
                    }
		    	}
		    	summaryRow += '</ss:Row>';
		    	return summaryRow;
		    },
		    
		    exportExcel : function(includeHidden) {
	            var tmpExportContent = '';
	            tmpExportContent = this.getExcelXml(includeHidden);     
                if (Ext.isIE || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3 ) {//在这几种浏览器中才需要
                    var fd = Ext.get('frmDummy');
                    if (!fd) {
                        fd = Ext.DomHelper.append(
                            Ext.getBody(), {
                                tag : 'form',
                                method : 'post',
                                id : 'frmDummy',
                                action : 'exportdata.jsp',
                                target : '_blank',
                                name : 'frmDummy',
                                cls : 'x-hidden',
                                cn : [ {
                                    tag : 'input',
                                    name : 'exportContent',
                                    id : 'exportContent',
                                    type : 'hidden'
                                } ]
                            }, true);
                    }
                    fd.child('#exportContent').set( {
                        value : tmpExportContent
                    });
                    fd.dom.submit();
                } else {
                    document.location = 'data:application/vnd.ms-excel;base64,' + Base64.encode(tmpExportContent);
                }
		    }    
		});

	}
	
});
