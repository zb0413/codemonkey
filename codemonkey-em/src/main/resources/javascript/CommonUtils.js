
var toString = Object.prototype.toString;

var CommonUtils = {
		
	buildUrl : function(url , params){
		if(!url){
			return;
		}
		var qs = "";
		if(params){
			for(var p in params){
				qs +=  p + "=" + params[p] + "&";
			}
			
			if(qs.charAt(qs.length-1)=='&'){
				qs = qs.substring(0, qs.length-1);
			}
			
			if(url.indexOf('?') >= 0){
				url += "&" + qs;
			}else{
				url += "?" + qs;
			}
		}
		return url;
	},
	
	encodeURL : function(s){
		
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
	
    trim : function (str){
    	if(str){
        	return str.replace( /^\s+/, "" ).replace( /\s+$/, "" );
    	}
    },
    
//    REGX_HTML_ENCODE : /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g,
//
//    REGX_HTML_DECODE : /&\w+;|&#(\d+);/g,
//
//    HTML_DECODE : {
//        "&lt;" : "<", 
//        "&gt;" : ">", 
//        "&amp;" : "&", 
//        "&nbsp;": " ", 
//        "&quot;": "\"", 
//        "&copy;": ""
//    },
//
//    encodeHtml : function(s){
//        s = (s != undefined) ? s : this.toString();
//        return (typeof s != "string") ? s :
//            s.replace(this.REGX_HTML_ENCODE, 
//                      function($0){
//                          var c = $0.charCodeAt(0), r = ["&#"];
//                          c = (c == 0x20) ? 0xA0 : c;
//                          r.push(c); r.push(";");
//                          return r.join("");
//                      });
//    },
//    
//    decodeHtml : function(s){
//        var HTML_DECODE = this.HTML_DECODE;
//
//        s = (s != undefined) ? s : this.toString();
//        return (typeof s != "string") ? s :
//            s.replace(
//            	  this.REGX_HTML_DECODE,
//	              function($0, $1){
//	                  var c = HTML_DECODE[$0];
//	                  if(c == undefined){
//	                      // Maybe is Entity Number
//	                      if(!isNaN($1)){
//	                          c = String.fromCharCode(($1 == 160) ? 32:$1);
//	                      }else{
//	                          c = $0;
//	                      }
//	                  }
//	                  return c;
//	              }
//            );
//    },
    
    //转换汉字为unicode编码
	toUnicode : function(str){
		return escape(str).replace(/%/g,"\\").toLowerCase();
    },
    //解释unicode编码为汉字
    unUnicode:function(str){
		return unescape(str.replace(/\\/g, "%"));
    },
    
//	sort : function(array , fn) {
//		if(!array) {
//			return;
//		}
//		
//		if(!Ext.isArray(array)){
//			return;
//		}
//		
//		for(var i = 0 ; i < array.length ; i++){
//			for(var j = i ; j > 0 ; j--){
//				var flag = fn(array[j-1] , array[j]) || 0;
//				if(flag < 0){
//					var temp = array[j];
//					array[j] = array[j-1];
//					array[j-1] = temp;
//				}
//			}
//		}
//		
//		return array;
//	},
	
	buildQueryInfo : function(params){
		
		if(params){
			var pageable = params.pageable ? true : false;
			params.pageable = "";
			return {
				queryInfo :	this.encode(params),
				pageable : pageable
			};
		}
		return {
			queryInfo :	{}
		};
		
	},
	
//	concatArray : function(array1 , array2){
//	
//		if(!array1){
//			return null;
//		}
//		
//		if(!array2){
//			return array1;
//		}
//		
//		for(var i = 0 ; i < array2.length ; i++){
//			array1.push(array2[i]);
//		}
//		
//		return array1;
//		
//	},
	
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
	openWindow : function(url , name , featrues){
		window.open(url , name , featrues)
	}
//	,
    
//  beforeSubmit : function(values){
//		if(values){
//			for(var p in values){
//				if(Ext.isArray(values[p]) || Ext.isObject(values[p])){
//					values[p] = Ext.encode(values[p]);
//				}
//			}
//		}
//	},
//	
//	encodeSubmitValues : function(values){
//		if(values){
//			for(var p in values){
//				values[p] = ExtUtils.encodeURL(values[p]);
//			}
//		}
//	}
//	,
//	TODO 移动到jqUils
//	drag : function(obj) {
//	    if (typeof obj === 'string') {
//	        var obj = document.getElementById(obj);
//	    } else {
//	        var obj = obj;
//	    }
//	    function fixEvent(event) {
//	        event.target = event.srcElement;
//	        event.preventDefault = fixEvent.preventDefault;
//	        return event;
//	    }
//	    fixEvent.preventDefault = function () {
//	        this.returnValue = false;
//	    };
//	    obj.onmousedown = mousedown;
//	    function mousedown(e) {
//	        var e = e || fixEvent(window.event);
//	        var disX = e.clientX - obj.offsetLeft;
//	        var disY = e.clientY - obj.offsetTop;
//            document.onmousemove = move;
//            document.onmouseup = up;
//	        function move(e) {
//	            var e = e || fixEvent(window.event);
//	            var left = e.clientX - disX;
//	            var top = e.clientY - disY;
//	            if (obj.setCapture) {
//	                obj.setCapture();
//	            }
//	            if (left < 0) {
//	                left = 0;
//	            } else if (left > document.documentElement.clientWidth - obj.offsetWidth) {
//	                left = document.documentElement.clientWidth - obj.offsetWidth;
//	            }
//	            if (top < 0) {
//	                top = 0;
//	            } else if (top > document.documentElement.clientHeight - obj.offsetHeight) {
//	                top = document.documentElement.clientHeight - obj.offsetHeight;
//	            }
//	            obj.style.left = left + 'px';
//	            obj.style.top = top + 'px';
//	            return false;
//	        };
//	        function up() {
//	            if (obj.releaseCapture) {
//	                obj.releaseCapture();
//	            }
//	            document.onmousemove = null;
//	            document.onmouseup = null;
//	        }
//	    }
//	}
	
};