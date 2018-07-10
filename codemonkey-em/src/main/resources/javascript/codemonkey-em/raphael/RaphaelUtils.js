

//add Function to Raphael 
(function(){
	if(Raphael){
		
		Raphael.fn.getConnection = function (obj1, obj2) {
			if(!this.connections){
				return null;
			}
			
			for(var i = 0 ; i < this.connections.length; i++){
				if(this.connections[i].from === obj1 && this.connections[i].to === obj2){
					return this.connections[i];
				}
			}
			
			return null;
		}
		
		Raphael.fn.connection = function (obj1, obj2 , attrs) {
			
			if(!this.connections){
				this.connections = [];
			}
			
			var line = this.getConnection(obj1, obj2);
			
			var bb1 = obj1.getBBox(),
				bb2 = obj2.getBBox(),
				p = [{x: bb1.x + bb1.width / 2, y: bb1.y - 1},
				{x: bb1.x + bb1.width / 2, y: bb1.y + bb1.height + 1},
				{x: bb1.x - 1, y: bb1.y + bb1.height / 2},
				{x: bb1.x + bb1.width + 1, y: bb1.y + bb1.height / 2},
				{x: bb2.x + bb2.width / 2, y: bb2.y - 1},
				{x: bb2.x + bb2.width / 2, y: bb2.y + bb2.height + 1},
				{x: bb2.x - 1, y: bb2.y + bb2.height / 2},
				{x: bb2.x + bb2.width + 1, y: bb2.y + bb2.height / 2}],
				d = {}, dis = [];
			for (var i = 0; i < 4; i++) {
				for (var j = 4; j < 8; j++) {
					var dx = Math.abs(p[i].x - p[j].x),
						dy = Math.abs(p[i].y - p[j].y);
					if ((i == j - 4) || (((i != 3 && j != 6) || p[i].x < p[j].x) && ((i != 2 && j != 7) || p[i].x > p[j].x) && ((i != 0 && j != 5) || p[i].y > p[j].y) && ((i != 1 && j != 4) || p[i].y < p[j].y))) {
						dis.push(dx + dy);
						d[dis[dis.length - 1]] = [i, j];
					}
				}
			}
			if (dis.length == 0) {
				var res = [0, 4];
			} else {
				res = d[Math.min.apply(Math, dis)];
			}
			var x1 = p[res[0]].x,
				y1 = p[res[0]].y,
				x4 = p[res[1]].x,
				y4 = p[res[1]].y;
			dx = Math.max(Math.abs(x1 - x4) / 2, 10);
			dy = Math.max(Math.abs(y1 - y4) / 2, 10);
			var x2 = [x1, x1, x1 - dx, x1 + dx][res[0]].toFixed(3),
				y2 = [y1 - dy, y1 + dy, y1, y1][res[0]].toFixed(3),
				x3 = [0, 0, 0, 0, x4, x4, x4 - dx, x4 + dx][res[1]].toFixed(3),
				y3 = [0, 0, 0, 0, y1 + dy, y1 - dy, y4, y4][res[1]].toFixed(3);
			var path = ["M", x1.toFixed(3), y1.toFixed(3), "C", x2, y2, x3, y3, x4.toFixed(3), y4.toFixed(3)].join(",");
			
			if (line) {
				line.attr({path: path});
			} else {
				var line = this.path(path);
				var c = {
					"arrow-end" : "classic-narrow-short",
					cursor : "hand",
					stroke : "#000",
					opacity : 0.2,
					"stroke-width" : 4
				};
				Ext.apply(c , attrs);
				line.attr(c);
				
				line.from = obj1;
				line.to = obj2;
				
				this.connections.push(line);
			}
			
			return line;
		};
	}
})();

Ext.define('AM.raphael.RaphaelUtils' , {

	//copy from ExtJs
	apply : function(object, config, defaults) {
        if (defaults) {
            this.apply(object, defaults);
        }

        if (object && config && typeof config === 'object') {
            var i, j, k;
            for (i in config) {
                object[i] = config[i];
            }          
        }

        return object;
    },
	
	/*
		RaphaelUtils.circle(paper , {
			x : 10,
			y : 10,
			text : 'text',
			r : 100
		});
	*/
	circle : function(paper , config){
		var c = {
			x : 10,
			y : 10,
			text : 'text',
			r : 100
		};
		
		this.apply(c , config);
	
		var block = paper.circle(c.x , c.y , c.r);
		
		paper.setStart();
		var t = paper.text(c.x , c.y , c.text);
		
		t.attr({
			"font-size" : 12,
			color : "#000"
		});
		paper.setFinish();
		
		block.textLabel = t;
		
		block.drag(function(dx , dy ){
			//move
			RaphaelUtils.onMove(dx , dy , this);
		
		} , function(x , y , e){
			//startDrag
			RaphaelUtils.onStartDrag(x , y , this);
		});
		
		return block;
		
	},
	/*
		RaphaelUtils.rect(paper , {
			x : 10,
			y : 10,
			text : 'text',
			width : 10,
			height : 10,
			r : 100
		});
	*/
	rect : function( paper , config){
		var c = {
			x : 10,
			y : 10,
			text : 'text',
			width : 100,
			height : 100,
			r : 5
		};
		
		this.apply(c , config);
	
		var block = paper.rect(c.x , c.y , c.width , c.height , c.r);
		
		paper.setStart();
		var t = paper.text(c.x + Math.round(c.width / 2), c.y + Math.round(c.height / 2), c.text);
		
		t.attr({
			"font-size" : 12,
			color : "#000"
		});
		paper.setFinish();
		
		block.textLabel = t;
		
		block.drag(function(dx , dy ){
			//move
			RaphaelUtils.onMove(dx , dy , this);
		
		} , function(x , y , e){
			//startDrag
			RaphaelUtils.onStartDrag(x , y , this);
		});
		
		return block;
	},
	
	/*
		RaphaelUtils.attrs(circle , {
			"fill", "#f00",
			"stroke", "#fff"
		});
	*/
	attrs : function(block , attrs){
		if(!block || !attrs){
			return;
		}
	
		for(var p in attrs){
			block.attr(p , 	attrs[p]);
		}
		
	},
	
	onStartDrag : function(x , y , block){
		
		block.ox = block.type == "rect" ? block.attr("x") : block.attr("cx");
        block.oy = block.type == "rect" ? block.attr("y") : block.attr("cy");
		
	},
	
	onMove : function(dx , dy , block){
		var x = block.ox + dx;
		var y = block.oy + dy;
	
		var att = block.type == "rect" ? {x: x , y: y} : {cx: x, cy: y};
        block.attr(att);
		
		if(block.textLabel){
			block.textLabel.attr({
				x : block.getBBox().cx,
				y : block.getBBox().cy
			});
		}
		
		if(block.paper.connections){
			for (var i = 0 ; i < block.paper.connections.length; i++) {
                block.paper.connection(block.paper.connections[i].from , block.paper.connections[i].to);
            }
		}
		
		block.paper.safari();
	}

});

var RaphaelUtils = {};
(function(){
	RaphaelUtils = Ext.create('AM.raphael.RaphaelUtils');
})();


