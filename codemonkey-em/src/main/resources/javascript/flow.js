var Wd = {};
var Wb = {};

function loadFlow() {
	function defDragSelector() {
		Ext.define("Wb.flow.DragSelector",{
			requires : [ "Ext.dd.DragTracker",
					"Ext.util.Region" ],
			init : function(panel) {
				this.panel = panel;
				panel.mon(panel, {
					beforecontainerclick : this.cancelClick,
					scope : this,
					render : {
						fn : this.onRender,
						scope : this,
						single : true
					}
				});
			},
			onRender : function() {
				this.tracker = new Ext.dd.DragTracker({
					panel : this.panel,
					el : this.panel.body,
					dragSelector : this,
					onBeforeStart : this.onBeforeStart,
					onStart : this.onStart,
					onDrag : this.onDrag,
					onEnd : this.onEnd
				});
				this.dragRegion = new Ext.util.Region();
			},
			onBeforeStart : function(e) {
				return e.target == this.panel.paper.canvas;
			},
			onStart : function(e) {
				var dragSelector = this.dragSelector;
				this.dragging = true;
				this.panel.dragging = true;
				dragSelector.fillRegions();
				dragSelector.getProxy().show();
			},
			cancelClick : function() {
				return !this.tracker.dragging
			},
			onDrag : function(e) {
				var dragSelector = this.dragSelector, 
					dragRegion = dragSelector.dragRegion, 
					bodyRegion = dragSelector.bodyRegion, 
					proxy = dragSelector.getProxy(), 
					startXY = this.startXY, 
					currentXY = this.getXY(), 
					minX = Math.min(startXY[0], currentXY[0]), 
					minY = Math.min(startXY[1], currentXY[1]), 
					width = Math.abs(startXY[0] - currentXY[0]), 
					height = Math.abs(startXY[1] - currentXY[1]), 
					region, selected;
				Ext.apply(dragRegion, {
					top : minY,
					left : minX,
					right : minX + width,
					bottom : minY + height
				});
				dragRegion.constrainTo(bodyRegion);
				proxy.setRegion(dragRegion);
				var i, j = 0, so, b, s, r, 
				    nodeList = this.panel.nodeList, 
				    ox = this.panel.body.getLeft(), 
				    oy = this.panel.body.getTop();
				for ( var i in nodeList) {
					if (nodeList[i].isLine) {
						r = nodeList[i].posInfo;
						s = lineIntersect(
							r.x1 + ox, 
							r.y1 + oy,
							r.x2 + ox, 
							r.y2 + oy,
							dragRegion.x, 
							dragRegion.y,
							dragRegion.right,
							dragRegion.bottom);
					} else {
						r = {};
						b = nodeList[i].getBBox(true);
						r.x = b.x + ox;
						r.y = b.y + oy;
						r.right = r.x + b.width;
						r.bottom = r.y + b.height;
						s = dragRegion.intersect(r);
					}
					nodeList[i].selected = s;
					if (s && !nodeList[i].isLine) {
						so = nodeList[i];
						j++;
					}
					if (nodeList[i].isLine) {
						nodeList[i].attr({opacity : s ? 0.5 : 0.2});
					} else {
						nodeList[i].attr({"fill-opacity" : s ? 0.5 : 0.2});
					}
					disableProperty(j != 1);
					if (j == 1) {
						setProperty(so);
					}
				}
			},
			onEnd : Ext.Function.createDelayed(function(e) {
				var dragSelector = this.dragSelector;
				this.dragging = false;
				this.panel.dragging = false;
				dragSelector.getProxy().hide();
			}, 1),
			getProxy : function() {
				if (!this.proxy) {
					this.proxy = this.panel.body.createChild({
						tag : "div",
						cls : "x-view-selector"
					});
				}
				return this.proxy;
			},
			fillRegions : function() {
				var panel = this.panel;
				this.bodyRegion = panel.body.getRegion();
			}
		});
	}
	function disableProperty(b) {
		var tab1 = Ext.getCmp('tab1');
		var t = tab1.getActiveTab();
		if (t) {
			t.recordModify = !b;
		}
		if (b) {
			if (t) {
				t.propertyNode = null;
//				scriptEditor.selNode = null;
			}
//			edTitle.setValue("(请选择1个节点)");
//			scriptEditor.setValue("");
		}
	}
	function setProperty(node) {
		var tab1 = Ext.getCmp('tab1');
//		if (scriptEditor.selNode == node) {
//			return;
//		}
		var at = tab1.getActiveTab(), pos, saveLeft, saveTop, saveCursor;
		saveLeft = node.scrollLeft;
		saveTop = node.scrollTop;
		saveCursor = node.cursor;
//		if (scriptEditor.selNode) {
//			pos = scriptEditor.getScrollInfo();
//			scriptEditor.selNode.scrollLeft = pos.x;
//			scriptEditor.selNode.scrollTop = pos.y;
//			scriptEditor.selNode.cursor = scriptEditor.getCursor();
//		}
//		at.recordModify = false;
//		edTitle.clearInvalid();
//		edTitle.setValue(node.edTitle);
//		if (Ext.isIE) {
//			scriptEditor.focus();
//		}
//		scriptEditor.setValue(node.edScript);
//		at.propertyNode = node;
//		at.recordModify = true;
//		scriptEditor.scrollTo(saveLeft || 0, saveTop || 0);
//		if (saveCursor) {
//			scriptEditor.setCursor(saveCursor);
//		}
//		scriptEditor.selNode = node;
	}
	Raphael.fn.link = function(obj) {
		var tab1 = Ext.getCmp('tab1');
		var point, path, adjx, adjy, posInfo;
		point = getStartEnd(obj.obj1, obj.obj2);
		adjx = Math.abs(point.end.x - point.start.x);
		adjy = Math.abs(point.end.y - point.start.y);
		if (adjx + adjy < 10) {
			point.end.x += (10 - adjx) * (point.end.x > point.start.x ? 1 : -1);
		}
		if (adjx + adjy < 10) {
			point.end.y += (10 - adjy) * (point.end.y > point.start.y ? 1 : -1);
		}
		path = "M" + point.start.x + " " + point.start.y + "L" + point.end.x + " " + point.end.y;
		posInfo = {
			x1 : point.start.x,
			y1 : point.start.y,
			x2 : point.end.x,
			y2 : point.end.y
		};
		if (obj.linkPath) {
			obj.linkPath.attr({
				path : path
			});
			obj.linkPath.posInfo = posInfo;
		} else {
			obj.linkPath = this.path(path);
			obj.linkPath.attr({
				"arrow-end" : "classic-wide-midium",
				cursor : "hand",
				stroke : "#000",
				opacity : 0.2,
				"stroke-width" : 4
			});
			obj.linkPath.posInfo = posInfo;
			obj.linkPath.isLine = true;
			obj.linkPath.src = obj.obj1;
			obj.linkPath.dst = obj.obj2;
			tab1.getActiveTab().nodeList.push(obj.linkPath);
			obj.linkPath.mousedown(function(e) {
				disableProperty(true);
				if (e.shiftKey) {
					this.selected = !this.selected;
					this.attr({
						opacity : this.selected ? 0.5 : 0.2
					});
				} else {
					if (!this.selected) {
						clearSelection(this);
					}
				}
				e.preventDefault();
			})
		}
	};
	function lineIntersect(linePointX1, linePointY1, linePointX2, linePointY2,
			rectangleLeftTopX, rectangleLeftTopY, rectangleRightBottomX, rectangleRightBottomY) {
		
		var lineHeight = linePointY1 - linePointY2, 
		    lineWidth = linePointX2 - linePointX1, 
		    c = linePointX1 * linePointY2 - linePointX2 * linePointY1,
			temp;
		if ((lineHeight * rectangleLeftTopX + lineWidth * rectangleLeftTopY + c >= 0 && lineHeight
				* rectangleRightBottomX + lineWidth * rectangleRightBottomY + c <= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth
						* rectangleLeftTopY + c <= 0 && lineHeight
						* rectangleRightBottomX + lineWidth
						* rectangleRightBottomY + c >= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth
						* rectangleRightBottomY + c >= 0 && lineHeight
						* rectangleRightBottomX + lineWidth * rectangleLeftTopY
						+ c <= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth
						* rectangleRightBottomY + c <= 0 && lineHeight
						* rectangleRightBottomX + lineWidth * rectangleLeftTopY
						+ c >= 0)) {
			if (rectangleLeftTopX > rectangleRightBottomX) {
				temp = rectangleLeftTopX;
				rectangleLeftTopX = rectangleRightBottomX;
				rectangleRightBottomX = temp;
			}
			if (rectangleLeftTopY < rectangleRightBottomY) {
				temp = rectangleLeftTopY;
				rectangleLeftTopY = rectangleRightBottomY;
				rectangleRightBottomY = temp;
			}
			if ((linePointX1 < rectangleLeftTopX && linePointX2 < rectangleLeftTopX)
					|| (linePointX1 > rectangleRightBottomX && linePointX2 > rectangleRightBottomX)
					|| (linePointY1 > rectangleLeftTopY && linePointY2 > rectangleLeftTopY)
					|| (linePointY1 < rectangleRightBottomY && linePointY2 < rectangleRightBottomY)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	function getStartEnd(obj1, obj2) {
		var bb1 = obj1.getBBox(),
		    bb2 = obj2.getBBox();
		var p = [ {
			x : bb1.x + bb1.width / 2,
			y : bb1.y - 1
		}, {
			x : bb1.x + bb1.width / 2,
			y : bb1.y + bb1.height + 1
		}, {
			x : bb1.x - 1,
			y : bb1.y + bb1.height / 2
		}, {
			x : bb1.x + bb1.width + 1,
			y : bb1.y + bb1.height / 2
		}, {
			x : bb2.x + bb2.width / 2,
			y : bb2.y - 1
		}, {
			x : bb2.x + bb2.width / 2,
			y : bb2.y + bb2.height + 1
		}, {
			x : bb2.x - 1,
			y : bb2.y + bb2.height / 2
		}, {
			x : bb2.x + bb2.width + 1,
			y : bb2.y + bb2.height / 2
		} ];
		var d = {}, 
		    dis = [];
		for (var i = 0; i < 4; i++) {
			for (var j = 4; j < 8; j++) {
				var dx = Math.abs(p[i].x - p[j].x), 
				    dy = Math.abs(p[i].y - p[j].y);
				if ((i == j - 4)
						|| (((i != 3 && j != 6) || p[i].x < p[j].x)
								&& ((i != 2 && j != 7) || p[i].x > p[j].x)
								&& ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
					dis.push(dx + dy);
					d[dis[dis.length - 1]] = [ i, j ];
				}
			}
		}
		if (dis.length == 0) {
			var res = [ 0, 4 ];
		} else {
			res = d[Math.min.apply(Math, dis)];
		}
		var result = {};
		result.start = {};
		result.end = {};
		result.start.x = p[res[0]].x;
		result.start.y = p[res[0]].y;
		result.end.x = p[res[1]].x;
		result.end.y = p[res[1]].y;
		return result;
	}
	function dragger(x, y, e) {
		var tab1 = Ext.getCmp('tab1');
		if (Ext.isIE) {
			if (this.stopClick) {
				return;
			} else {
				this.stopClick = true;
			}
		}
		var i, j = 0, k,o,
		    firstNode,
		    firstObj,
		    at = tab1.getActiveTab(),
		    nodeList = at.nodeList,
		    connections = at.connections;
		for (i in nodeList) {
			o = nodeList[i];
			if (o.selected && o != this && !o.isLine) {
				j++;
				firstObj = o;
			}
		}
		if (e.shiftKey) {
			this.selected = !this.selected;
			this.attr({
				"fill-opacity" : this.selected ? 0.5 : 0.2
			});
		} else {
			if (!this.selected) {
				clearSelection(this);
			}
		}
		k = 0;
		for (i in nodeList) {
			o = nodeList[i];
			if (o.selected && !o.isLine) {
				k++;
				firstNode = o;
				o.ox = o.attr("x");
				o.oy = o.attr("y");
			}
		}
		disableProperty(k != 1);
		if (k == 1) {
			setProperty(firstNode);
		}
		
		var linkBtn = Ext.getCmp('linkBtn');
		if (linkBtn.pressed) {
			linkBtn.toggle(false);
			if (j != 1) {
//				Wb.warning("请选择1个源节点。");
			} else {
				for (i in connections) {
					if (connections[i].obj1 == firstObj
							&& connections[i].obj2 == this) {
						return;
					}
				}
				o = {
					obj1 : firstObj,
					obj2 : this
				};
				connections.push(o);
				this.paper.link(o);
				setModified();
			}
		}
	}
	function getSnap(p) {
		var m = p % 8;
		p = p - m;
		if (m > 4) {
			return p + 8;
		}
		return p;
	}
	function move(dx, dy) {
		var tab1 = Ext.getCmp('tab1');
		var i, o, lbl, x, 
		    mv = 0, y, w,
		    at = tab1.getActiveTab(),
		    nodeList = at.nodeList, 
		    connections = at.connections;
		for (i in nodeList) {
			o = nodeList[i];
			if (o.selected && !o.isLine) {
				x = getSnap(o.ox + dx);
				y = getSnap(o.oy + dy);
				if (o.attr("x") != x || o.attr("y") != y) {
					mv = 1;
				}
				o.attr({
					x : x,
					y : y
				});
				lbl = o.label;
				w = Math.max(o.label.getBBox().width + 6, 150);
				if (lbl) {
					lbl.attr({
						x : Math.round(o.getBBox().x + w / 2),
						y : y + 11
					});
				}
			}
		}
		if (mv) {
			setModified();
		}
		for (i in connections) {
			this.paper.link(connections[i]);
		}
		this.paper.safari();
	}
	function getUnique(ns, at) {
		var i, o, n, b;
		while (true) {
			n = "节点" + at.nodeIndex++;
			b = true;
			for (i in ns) {
				o = ns[i];
				if (!o.isLine && o.edTitle == n) {
					b = false;
					break;
				}
			}
			if (b) {
				return n;
			}
		}
	}
	function draw(p, x, y, w, tt, script) {
		var tab1 = Ext.getCmp('tab1');
		var t, o, r, 
		    at = tab1.getActiveTab(),
			flag = !tt;
		if (tt === undefined) {
			tt = getUnique(at.nodeList, at);
		}
		if (w === undefined) {
			w = 150;
		}
		if (script === undefined) {
			script = "";
		}
		p.setStart();
		t = p.text(x + Math.round(w / 2), y + 11, tt);
		t.attr({
			"font-size" : 12,
			color : "#000"
		});
		o = p.rect(x, y, w, 23, 5);
		o.edTitle = tt;
		o.edScript = script;
		o.attr({
			fill : "#888",
			stroke : "#AAC",
			"stroke-width" : 1,
			"fill-opacity" : flag ? 0.5 : 0.2,
			cursor : "move"
		});
		o.label = t;
		r = p.setFinish();
		o.obj = r;
		o.paper = p;
		if (Ext.isIE) {
			r.mouseup(function() {
				this.stopClick = false;
				scriptEditor.focus();
			})
		}
		r.drag(move, dragger);
		at.nodeList.push(o);
		if (flag) {
			clearSelection(o);
			setModified();
		}
		return o;
	}
	function clearSelection(o) {
		var tab1 = Ext.getCmp('tab1');
		var i, b, nodeList = tab1.getActiveTab().nodeList;
		for (i in nodeList) {
			b = nodeList[i] == o;
			nodeList[i].selected = b;
			if (nodeList[i].isLine) {
				nodeList[i].attr({
					opacity : b ? 0.5 : 0.2
				});
			} else {
				nodeList[i].attr({
					"fill-opacity" : b ? 0.5 : 0.2
				});
			}
		}
	}
	function selectAll() {
		var tab1 = Ext.getCmp('tab1');
		var at = tab1.getActiveTab(),
 		    i, nodeList, l;
		if (!at) {
			return;
		}
		nodeList = at.nodeList;
		l = nodeList.length;
		for (i in nodeList) {
			nodeList[i].selected = true;
			if (nodeList[i].isLine) {
				nodeList[i].attr({
					opacity : 0.5
				});
			} else {
				nodeList[i].attr({
					"fill-opacity" : 0.5
				});
			}
		}
		disableProperty(l != 1);
		if (l == 1) {
			setProperty(nodeList[0]);
		}
	}
	function removeNode() {
		var tab1 = Ext.getCmp('tab1');
		var i, x = 0, j, k, 
		    l, o, c, n,
			at = tab1.getActiveTab();
		if (!at) {
			return;
		}
		c = at.connections, n = at.nodeList;
		k = c.length;
		for (i = k - 1; i >= 0; i--) {
			o = c[i];
			if (o.linkPath.selected || o.obj1.selected || o.obj2.selected) {
				o.linkPath.selected = true;
				c.splice(i, 1);
			}
		}
		l = n.length;
		for (j = l - 1; j >= 0; j--) {
			o = n[j];
			if (!o.selected) {
				continue;
			}
			if (o.label) {
				o.label.remove();
			}
			x = 1;
			o.remove();
			n.splice(j, 1);
		}
		if (x) {
			disableProperty(true);
			setModified();
		}
	}
	function setSaveBtn() {
		var tab1 = Ext.getCmp('tab1');
		var b1 = true, 
		    b2 = true,
			at = tab1.getActiveTab();
		tab1.items.each(function(c) {
			if (c.isModified) {
				b2 = false;
				if (c == at) {
					b1 = false;
					return false;
				}
			}
		});
//		saveBtn.setDisabled(b1);
//		saveAllBtn.setDisabled(b2);
	}
	function setModified() {
		
//		Wb.setModified(tab1.getActiveTab());
//		setSaveBtn();
	}
	function createFlow(obj) {
		var tab1 = Ext.getCmp('tab1');
		var t;
		t = tab1.add({
			title : obj.name,
			flowName : obj.name,
			iconCls : "item_icon",
			closable : true,
			workFlowId : obj.id,
			plugins : [ new Wb.flow.DragSelector() ],
			autoScroll : true,
			listeners : {
				destroy : function(t) {
					disableProperty(!tab1.getActiveTab());
					setSaveBtn();
				},
				beforeclose : function(t) {
					if (t.isModified) {
						tab1.setActiveTab(t);
						Wb.choose('"' + t.flowName + '" 已经被修改，保存所做的更改吗？',
								function(b) {
									if (b == "yes") {
										save(false, function() {
											t.close();
										})
									} else {
										if (b == "no") {
											t.isModified = false;
											t.close();
										}
									}
								});
						return false;
					}
				},
				afterrender : function(p) {
					var b = p.body;
					var nodeBtn = Ext.getCmp('nodeBtn');
					p.paper = Raphael(b.dom.id, 4000, 3500);
					b.on("click", function(e) {
						if (!p.dragging && e.target == p.paper.canvas) {
							if (nodeBtn.pressed) {
								var o = draw(p.paper, getSnap(e.getX()- b.getLeft() + p.body.dom.scrollLeft),
										getSnap(e.getY() - b.getTop()+ p.body.dom.scrollTop));
								nodeBtn.toggle(false);
								disableProperty(false);
								setProperty(o);
							} else {
								clearSelection();
								disableProperty(true);
							}
						}
					})
				}
			}
		});
		tab1.setActiveTab(t);
		t.nodeIndex = 1;
		t.nodeList = [];
		t.connections = [];
		if (obj.list) {
			openFlow(obj, t);
		}
		disableProperty(true);
	}
	function openFlow(obj, t) {
		var i, o, b;
		o = obj.list;
		for (i in o) {
			b = o[i].box;
			if (o[i].isObj) {
				draw(t.paper, b.x, b.y, b.w, o[i].label, o[i].script);
			}
		}
		function get(n) {
			var j, nl = t.nodeList;
			for (j in nl) {
				if (nl[j].edTitle == n) {
					return nl[j];
				}
			}
			return null;
		}
		o = obj.conn;
		for (i in o) {
			b = {
				obj1 : get(o[i].src),
				obj2 : get(o[i].dst)
			};
			t.connections.push(b);
			t.paper.link(b);
		}
	}
	function newFlow() {
		createFlow({
			id : -1,
			name : '新流程'
		});
//		Wb.prompt("新建流程", [ {
//			text : "流程名称",
//			allowBlank : false
//		} ], function(v) {
//			Wb.request({
//				url : "main?xwl=23U14D15L0AX",
//				params : {
//					name : v[0]
//				},
//				success : function(r) {
//					var id = r.responseText;
//					tree1.getRootNode().appendChild({
//						text : v[0],
//						workFlowId : id,
//						leaf : true,
//						iconCls : "item_icon"
//					});
//					createFlow({
//						id : id,
//						name : v[0]
//					});
//				}
//			});
//		});
	}
	function getTab(id) {
		var a = null;
		var tab1 = Ext.getCmp('tab1');
		tab1.items.each(function(t) {
			if (t.workFlowId == id) {
				a = t;
				return false;
			}
		});
		return a;
	}
	function save(isAll, fn) {
		var tab1 = Ext.getCmp('tab1');
		var data = {}, o, i, l,
     		at = tab1.getActiveTab(),
			nl, conn, box;
		tab1.items.each(function(t) {
			if (t.isModified && (isAll || t == at)) {
				l = t.nodeList;
				nl = [];
				for (i in l) {
					o = l[i];
					box = o.getBBox();
					if (o.isLine) {
						nl.push({
							isObj : false,
							box : {
								x : box.x,
								y : box.y,
								w : box.width
							}
						});
					} else {
						nl.push({
							label : o.edTitle,
							isObj : true,
							script : o.edScript,
							box : {
								x : box.x,
								y : box.y,
								w : box.width
							}
						});
					}
				}
				l = t.connections;
				conn = [];
				for (i in l) {
					o = l[i];
					conn.push({
						src : o.obj1.edTitle,
						dst : o.obj2.edTitle
					});
				}
				data[t.workFlowId] = {
					name : t.flowName,
					list : nl,
					conn : conn
				}
			}
		});
//		Wb.request({
//			url : "main?xwl=23U2229ZY4OE",
//			message : "正在保存中...",
//			jsonData : data,
//			success : function() {
//				tab1.items.each(function(t) {
//					if (t.isModified && (isAll || t == at)) {
//						Wb.delModified(t);
//					}
//				});
//				setSaveBtn();
//				if (fn) {
//					fn();
//				}
//			}
//		});
	}
	Wd.wb_beforeunload = function() {
		if (!saveAllBtn.disabled) {
			return "还有未保存的流程。";
		}
	};
	window.onbeforeunload = function() {
		if (!Wd.wb_forceCls && !Wb.isLogout()) {
			return wb_beforeunload();
		}
	};
	Wd.treeStore1 = new Ext.data.TreeStore({
//		proxy : {
//			type : "ajax",
//			url : "main?xwl=23TY1WYIS468",
//			listeners : {
//				exception : function(proxy, response, operation, options) {
//					Wb.except(response.responseText);
//				}
//			}
//		},
		fields : [ "text", "workFlowId" ],
		autoLoad : false,
		storeId : "treeStore1",
		params : {},
		listeners : {
			beforeload : function(store, operation, options) {
//				if (!Wb.setStore(store)) {
//					return false;
//				}
			}
		}
	});
	Wd.toolbar1 = new Ext.toolbar.Toolbar(
			{
				id : "toolbar1",
				items : [
						{
							id : "newBtn",
							iconCls : "new_icon",
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
								items : [
										{
											id : "delBtn",
											iconCls : "delete_icon",
											tooltip : "删除选择的流程",
											text : "删除流程",
											listeners : {
												click : function(item, event,options){
//													      var n = Wb.getSelNode(tree1), id, t;
//													      if (n) {
//														          Wb.confirm('确定需要删除"'	+ n.get("text")+ '"吗？',
//														          function() {
//																              id = n.get("workFlowId");
//																              Wb.request({
//																			              url : "main?xwl=23U2229ZY4JO",			
//																			              params : {
//																						  id : id
//																						},			
//																			  success : function() {
//																							       Wb.delSelNode(tree1);
//																							       t = getTab(id);
//																							      if (t) {
//																								           t.isModified = false;
//																								           t.close();
//																							             }
//																						}			
//																			 })						
//																 })				
//													} else {
//														    Wb.warning("请选择一个需要删除的流程。");
//													}
												}
											}
										},
										{
											id : "copyBtn",
											iconCls : "copy_icon",
											tooltip : "复制选择的流程",
											text : "复制流程",
											listeners : {
												click : function(item, event,options){
//													var n = Wb.getSelNode(tree1), o, id;
//													if (n) {
//														id = n.get("workFlowId");
//																Wb.prompt("复制 - " + n.get("text"),
//																		[ {
//																			text : "流程名称",
//																			allowBlank : false,
//																			value : n.get("text")+ "复件"
//																		} ],
//																		function(v){
//																					Wb.request({
//																						url : "main?xwl=23U2229ZY4EY",
//																						params : {
//																							id : n.get("workFlowId"),
//																							name : v[0]
//																						},
//																						success : function(r) {
//																							o = {
//																								workFlowId : r.responseText,
//																								text : v[0],
//																								leaf : true,
//																								iconCls : "item_icon"
//																							};
//																							if (n.nextSibling) {
//																								o = n.parentNode.insertBefore(o,n.nextSibling);
//																							} else {
//																								o = n.parentNode.appendChild(o);
//																							}
//																							tree1.getSelectionModel().select(o);
//																						}
//																					})
//																		})
//													} else {
//														   Wb.warning("请选择要复制的流程。");
//													}
												}
											}
										} ]
							}
						},
						{
							id : "propertyBtn",
							iconCls : "property_icon",
							tooltip : "查看或更改选择的流程属性",
							text : "流程属性",
							listeners : {
								click : function(item, event, options) {
//									var n = Wb.getSelNode(tree1), id, t;
//									if (n) {
//										    id = n.get("workFlowId");
//												Wb.prompt("属性 - " + n.get("text"),
//														[
//																{
//																	text : "流程编号",
//																	readOnly : true,
//																	value : id
//																},
//																{
//																	text : "流程名称",
//																	allowBlank : false,
//																	value : n.get("text")
//																			
//																} 
//													    ],
//														function(v) {
//																	Wb.request({
//																		url : "main?xwl=23U2229ZY3H6",
//																		params : {
//																			id : id,
//																			name : v[1]
//																		},
//																		success : function(r) {
//																			t = getTab(id);
//																			if (t) {
//																				   t.setTitle((t.isModified ? "*": "")+ v[1]);
//																			}
//																			n.set("text",v[1]);
//																			n.commit();
//																		}
//																	})
//														})
//									} else {
//										Wb.warning("请选择一个流程。");
//									}
								}
							}
						}, {
							id : "menuItem5",
							xtype : "tbseparator"
						}, {
							id : "saveBtn",
							disabled : true,
							iconCls : "save_icon",
							tooltip : "保存当前流程(Ctrl+S)",
							text : "保存当前",
							listeners : {
								click : function(item, event, options) {
									save();
								}
							}
						}, {
							id : "saveAllBtn",
							disabled : true,
							iconCls : "saveAll_icon",
							tooltip : "保存全部流程(Ctrl+Shift+S)",
							text : "保存全部",
							listeners : {
								click : function(item, event, options) {
									save(true);
								}
							}
						}, {
							id : "menuItem1",
							xtype : "tbseparator"
						}, {
							id : "nodeBtn",
							enableToggle : true,
							toggleGroup : "flow",
							iconCls : "insert_icon",
							tooltip : "添加流程节点",
							text : "添加节点"
						}, {
							id : "linkBtn",
							enableToggle : true,
							toggleGroup : "flow",
							iconCls : "linkOn_icon",
							tooltip : "把源节点连接到目标节点",
							text : "连接节点"
						}, {
							id : "menuItem2",
							xtype : "tbseparator"
						}, {
							id : "removeBtn",
							iconCls : "remove_icon",
							tooltip : "移除选择的节点或连接",
							text : "移除节点",
							listeners : {
								click : function(item, event, options) {
									removeNode();
								}
							}
						}, {
							id : "menuItem6",
							xtype : "tbseparator"
						}, {
							id : "selAll",
							iconCls : "model_icon",
							tooltip : "选择全部节点和连接",
							text : "选择全部",
							listeners : {
								click : function(item, event, options) {
									selectAll();
								}
							}
						} ]
			});
	Wd.toolbar2 = [ {
		id : "refreshBtn",
		type : "refresh",
		listeners : {
			click : function(item, event, options) {
//				Wb.refresh(tree1, "workFlowId");
			}
		}
	} ];
	Wd.toolbar3 = new Ext.toolbar.Toolbar({
		id : "toolbar3",
		items : [
				{
					id : "lbTitle",
					xtype : "tbtext",
					text : "名称："
				},
				{
					id : "edTitle",
					allowBlank : false,
					validator : function(value) {
						var tab1 = Ext.getCmp('tab1');
						var n = scriptEditor.selNode,  
						    at = tab1.getActiveTab(),
							s , ls, name;
						if (n && at && at.recordModify) {
							ls = at.nodeList;
							for (s in ls) {
								if (ls[s] != n && ls[s].edTitle === value) {
									return "节点名称重复";
								}
							}
						}
						return true;
					},
					value : "(请选择1个节点)",
					xtype : "textfield",
					flex : 1,
					listeners : {
						change : function(text, newValue, oldValue, options) {
							var tab1 = Ext.getCmp('tab1');
							var at = tab1.getActiveTab(),
							    n, w;
							if (at && at.recordModify && text.isValid()) {
								n = scriptEditor.selNode;
								n.edTitle = newValue;
								n.label.attr({
									text : newValue
								});
								w = Math.max(n.label.getBBox().width + 6, 150);
								n.attr({
									width : w
								});
								n.label.attr({
									x : Math.round(n.getBBox().x + w / 2)
								});
								setModified();
							}
						}
					}
				}, {
					id : "menuItem3",
					xtype : "tbseparator"
				}, {
					id : "lbFunc",
					xtype : "tbtext",
					text : "function (route)"
				}, {
					id : "menuItem4",
					xtype : "tbseparator"
				}, {
					id : "lbCursor",
					width : 80,
					xtype : "tbtext",
					text : "1:1"
				} ]
	});
	Wd.defPaper = new Ext.panel.Panel({
		id : "defPaper",
		height : 0,
		width : 0,
//		renderTo : Ext.getBody(),
		listeners : {
			afterrender : function(panel, options) {
				var p = Raphael(panel.body.id, 0, 0);
				p.path("M1 1L2 2").attr({
					"arrow-end" : "classic-wide-midium"
				});
			}
		}
	});
	Wd.viewport1 = Ext.create('Ext.panel.Panel',
			{
				id : "viewport1",
				layout : "fit",
				height : 800,
				renderTo : Ext.getBody(),
				items : [ {
					id : "mainPanel",
					tbar : Wd.toolbar1,
					layout : "border",
					xtype : "panel",
					items : [
							{
								region : "west",
								animate : false,
								store : Wd.treeStore1,
								collapsible : true,
								width : 180,
								iconCls : "list_icon",
								id : "tree1",
								rootVisible : false,
								tools : Wd.toolbar2,
								title : "流程列表",
								split : true,
								xtype : "treepanel",
								iconCls : "explorer_icon",
								listeners : {
									itemdblclick : function(view, record, item,index, event, options) {
										var tab1 = Ext.getCmp('tab1');
										var id = record.get("workFlowId"), 
										t = getTab(id);
										if (t) {
											tab1.setActiveTab(t);
										} else {
													Wb.request({
														        url : "main?xwl=23U2229ZY4P5",
														        params : {
															              id : id
														        },
														        success : function(r) {
																var o = Wb.decode(r.responseText);
																o.id = id;
																createFlow(o);
												                }
													})
										 }
									}
								}
							},
							{
								id : "leftPanel",
								region : "center",
								layout : "border",
								split : true,
								xtype : "panel",
								items : [
										{
											id : "tab1",
											region : "center",
											xtype : "tabpanel",
											bodyStyle : "background-color:#787878;background-image:none",
											
											listeners : {
												tabchange : function(tab,newCard, oldCard,options) {
													setSaveBtn();
													var n = newCard.propertyNode;
													if (n) {
														setProperty(n);
													} else {
														disableProperty(true);
													}
												}
											}
										},
										{
											id : "panel1",
											tbar : Wd.toolbar3,
											region : "south",
											height : 180,
											html : "<div id='mrEditor'></div>",
											split : true,
											xtype : "panel",
											border : false,
											listeners : {
												         afterrender : function(panel,options) {
//															  Wd.scriptEditor = CodeMirror.fromTextArea( 
//																 Wb.dom("mrEditor"),
//																{
//																	lineNumbers : true,
//																	mode : "text/javascript",
//																	onChange : function() {
//																		var at = tab1.getActiveTab();
//																		if (at && at.recordModify) {
//																			scriptEditor.selNode.edScript = scriptEditor.getValue();
//																			setModified();
//																		}
//																	},
//																	onCursorActivity : function() {
//																		var o = scriptEditor.getCursor();
//																		lbCursor.setText((o.line + 1)+ ":"+ (o.ch + 1));
//																	}
//																}
//																);
														},
														resize : function(panel,adjWidth, adjHeight,options) {
//															Ext.fly(scriptEditor.getScrollerElement()).setHeight(adjHeight - 26);
//															scriptEditor.refresh();
														}
											}
										} ]
							} ]
				} ]
			});
	defDragSelector();
	Ext.getDoc().on("keydown", function(e, o) {
		if (e.ctrlKey && String.fromCharCode(e.getKey()) == "S") {
			e.stopEvent();
			if (e.shiftKey && !saveAllBtn.disabled) {
				save(true);
			} else {
				if (!saveBtn.disabled) {
					save();
				}
			}
		}
	});
}