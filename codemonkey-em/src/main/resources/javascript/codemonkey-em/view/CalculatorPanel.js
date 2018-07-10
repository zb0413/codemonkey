Ext.define('AM.view.CalculatorPanel', {
	
	extend: 'Ext.panel.Panel',
	
	alias: 'widget.calculatorpanel',
	
	title: '计算器',
	
	constructor : function(config) {
		
		var me = this;
		
		me.bodyPadding = 5;
		me.layout = {
			type: 'table',
			columns: 5  //设置表格布局默认列数
		},
		me.frame = true;
		me.tbar = [{
				itemId: 'expression',
				xtype : 'textfield',
				readOnly: true,
				style: 'text-align:right'
			},
			'=',
			{
				xtype : 'textfield',
				itemId: 'result',
				width: 60
			}
		];
		me.defaultType = 'button',
		me.defaults = {
			minWidth: 50,
			handler: function(){
				me.btnClick(this);
			}
		};
		me.items = [{
			text: '1',symbol: '1'
		}, {
			text: '2',symbol: '2'
		}, {
			text: '3',symbol: '3'
		}, {
			text: '(',symbol: '('
		},{
			text: ')',symbol: ')'
		}, {
			text: '4',symbol: '4'
		}, {
			text: '5',symbol: '5'
		}, {
			text: '6',symbol: '6'
		}, {
			text: '+',symbol: '+'
		},{
			text: '-',symbol: '-'
		}, {
			text: '7',symbol: '7'
		}, {
			text: '8',symbol: '8'
		}, {
			text: '9',symbol: '9'
		}, {
			text: '×',symbol: '*'
		}, {
			text: '÷',symbol: '/'
		}, {
			text: '0',symbol: '0'
		}, {
			text: '.',symbol: '.'
		}, {
			text: '=',symbol: '='
		}, {
			text: '删',symbol: 'clear'
		}, {
			text: '退',symbol: 'back'
		}];
		
		this.callParent(arguments);
	
	},
	
	btnClick : function(btn) {
		var me = this;
		var expression = me.down('#expression');
		var result = me.down('#result');
		var oldValue = expression.getValue();
		if(btn.symbol == 'back') {
			//取消最后一次输入的字符
			oldValue = oldValue.substring(0, oldValue.length - 1);
		} else if(btn.symbol == 'clear') {
			//清空表达式和结果
			oldValue = '';
			result.setValue('');
		} else if(btn.symbol == '=') {
			var r = me.calculate(oldValue);
		} else if(btn.symbol == '.') {
			if(oldValue.indexOf('.') != -1) {
				return;
			}
			oldValue += btn.symbol;
		} else {
			//累计表达式字符串
			oldValue += btn.symbol;
		}
		expression.setValue(oldValue);
	},
	
	calculate : function(expr){
	}
	
});