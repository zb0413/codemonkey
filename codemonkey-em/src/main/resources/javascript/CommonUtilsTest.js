//describe("test-extend", function() {
//
//	it("null返回null", function() {
//		var dst = CommonUtils.extend(null);
//		expect(dst).toBe(null);
//	});
//	
//	it("{}返回{}", function() {
//		var dst = CommonUtils.extend({});
//		expect(CommonUtils.isObject(dst)).toBe(true);
//	});
//	
//	it("相同属性覆盖", function() {
//		var dst = CommonUtils.extend({key1 : "key1"} , {key1 : "key2"});
//		expect(dst.key1).toBe("key2");
//	});
//	
//	it("不相同属性覆盖", function() {
//		var dst = CommonUtils.extend({key1 : "key1"} , {key2 : "key2"});
//		expect(dst.key1).toBe("key1");
//		expect(dst.key2).toBe("key2");
//	});
//	
//	it("[]返回[]", function() {
//		var dst = CommonUtils.extend([]);
//		expect(CommonUtils.isArray(dst)).toBe(true);
//	});
//	
//	it("同位置数组", function() {
//		var array1 = [];
//		array1[1] = {name : 'obj-1'};
//		
//		var array2 = [];
//		array2[1] = {name : 'obj-2'};
//		
//		var dst = CommonUtils.extend(array1 , array2);
//		expect(dst[1].name).toBe('obj-2');
//	});
//	
//	it("不同位置数组", function() {
//		
//		var array1 = [];
//		array1[1] = {name : 'obj-1'};
//		
//		var array2 = [];
//		array2[3] = {name : 'obj-3'};
//		
//		var dst = CommonUtils.extend(array1 , array2);
//		expect(dst.length).toBe(4);
//		expect(dst[1].name).toBe('obj-1');
//		expect(dst[3].name).toBe('obj-3');
//		
//	});
//	
//});
//
//describe("test-deepExtend", function() {
//
//	it("null返回null", function() {
//		var dst = CommonUtils.deepExtend(null);
//		expect(dst).toBe(null);
//	});
//	
//	it("{}返回{}", function() {
//		var dst = CommonUtils.deepExtend({});
//		expect(CommonUtils.isObject(dst)).toBe(true);
//	});
//	
//	it("相同属性覆盖", function() {
//		var dst = CommonUtils.deepExtend({key1 : {name : "key1"}} , {key1 : {name : "key2"}});
//		expect(dst.key1.name).toBe("key2");
//	});
//	
//	it("不相同属性覆盖", function() {
//		var dst = CommonUtils.deepExtend({key1 : {name : "key1"}} , {key2 : {name : "key2"}});
//		expect(dst.key1.name).toBe("key1");
//		expect(dst.key2.name).toBe("key2");
//	});
//	
//	it("[]返回[]", function() {
//		var dst = CommonUtils.deepExtend([]);
//		expect(CommonUtils.isArray(dst)).toBe(true);
//	});
//	
//	it("同位置数组", function() {
//		var array1 = [];
//		array1[1] = {name : {key : 'obj-1'}};
//		
//		var array2 = [];
//		array2[1] = {name : {key : 'obj-2'}};
//		
//		var dst = CommonUtils.deepExtend(array1 , array2);
//		expect(dst[1].name.key).toBe('obj-2');
//	});
//	
//	it("不同位置数组", function() {
//		
//		var array1 = [];
//		array1[1] = {name : {key : 'obj-1'}};
//		
//		var array2 = [];
//		array2[3] = {name : {key : 'obj-3'}};
//		
//		var dst = CommonUtils.deepExtend(array1 , array2);
//		expect(dst.length).toBe(4);
//		expect(dst[1].name.key).toBe('obj-1');
//		expect(dst[3].name.key).toBe('obj-3');
//		
//	});
//	
//});
//
//describe("test-isEmpty", function() {
//
//	it("null返回false", function() {
//		expect(CommonUtils.isEmpty(null)).toBe(true);
//	});
//	
//	it("空字符串返回true", function() {
//		expect(CommonUtils.isEmpty("")).toBe(true);
//		expect(CommonUtils.isEmpty("" , false)).toBe(true);
//		expect(CommonUtils.isEmpty("" , true)).toBe(false);
//	});
//	
//	it("[]返回true", function() {
//		expect(CommonUtils.isEmpty([])).toBe(true);
//		expect(CommonUtils.isEmpty([1])).toBe(false);
//	});
//	
//});
// 
//describe("test-isArray", function() {
//
//	it("[]返回true", function() {
//		expect(CommonUtils.isArray([])).toBe(true);
//	});
//	
//	it("[1,2]返回true", function() {
//		expect(CommonUtils.isArray([1,2])).toBe(true);
//	});
//	
//	it("null返回false", function() {
//		expect(CommonUtils.isArray(false)).toBe(false);
//	});
//	
//	it("{}返回false", function() {
//		expect(CommonUtils.isArray({})).toBe(false);
//	});
//	
//});
//
//describe("test-isDate", function() {
//
//	it("Date 返回true", function() {
//		expect(CommonUtils.isDate(new Date())).toBe(true);
//	});
//	
//	it("null 返回false", function() {
//		expect(CommonUtils.isDate(null)).toBe(false);
//	});
//	
//});
//
//describe("test-isObject", function() {
//
//	it("null 返回false", function() {
//		expect(CommonUtils.isObject(null)).toBe(false);
//	});
//	
//	it("{} 返回true", function() {
//		expect(CommonUtils.isObject({})).toBe(true);
//	});
//	
//	it("[] 返回false", function() {
//		expect(CommonUtils.isObject([])).toBe(false);
//	});
//	
//});
//
//
//describe("test-isPrimitive", function() {
//
//	it("int 返回true", function() {
//		expect(CommonUtils.isPrimitive(3)).toBe(true);
//	});
//	
//	it("float 返回true", function() {
//		expect(CommonUtils.isPrimitive(1.23)).toBe(true);
//	});
//	
//	it("string 返回true", function() {
//		expect(CommonUtils.isPrimitive("123")).toBe(true);
//	});
//	
//	it("null 返回false", function() {
//		expect(CommonUtils.isPrimitive(null)).toBe(false);
//	});
//	
//});
//
//describe("test-isFunction", function() {
//
//	it("function 返回true", function() {
//		var fn = function(){};
//		expect(CommonUtils.isFunction(fn)).toBe(true);
//	});
//	
//	it("null 返回false", function() {
//		var fn = function(){};
//		expect(CommonUtils.isFunction(null)).toBe(false);
//	});
//	
//});
//
//describe("test-isNumber", function() {
//
//	it("null 返回false", function() {
//		var fn = function(){};
//		expect(CommonUtils.isNumber(null)).toBe(false);
//	});
//	
//	it("1 返回true", function() {
//		var fn = function(){};
//		expect(CommonUtils.isNumber(1)).toBe(true);
//	});
//	
//	it("1.2  返回true", function() {
//		var fn = function(){};
//		expect(CommonUtils.isNumber(1.2)).toBe(true);
//	});
//	
//});
//
//describe("test-isString", function() {
//
//	it("null 返回false", function() {
//		expect(CommonUtils.isString(null)).toBe(false);
//	});
//	
//	it(" '1' 返回true", function() {
//		expect(CommonUtils.isString('1')).toBe(true);
//	});
//	
//	it("1.2  返回false", function() {
//		var fn = function(){};
//		expect(CommonUtils.isString(1.2)).toBe(false);
//	});
//	
//	it("2  返回false", function() {
//		expect(CommonUtils.isString(2)).toBe(false);
//	});
//	
//});
//
//describe("test-isBoolean", function() {
//
//	it("null 返回false", function() {
//		var fn = function(){};
//		expect(CommonUtils.isBoolean(null)).toBe(false);
//	});
//	
//	it(" true 返回true", function() {
//		expect(CommonUtils.isBoolean(true)).toBe(true);
//	});
//	
//	it("false 返回false", function() {
//		expect(CommonUtils.isBoolean(false)).toBe(true);
//	});
//	
//});
 
 
describe("test-buildUrl", function() {
	it("url为空时返回null", function() {
		var url = CommonUtils.buildUrl(null);
		expect(url).toBe(undefined);
	});
	
	it("url无参数", function() {
		var url = CommonUtils.buildUrl('www.123.com');
		expect(url).toBe('www.123.com');
	});
	
	it("1个参数追加", function() {
		var params = {q : 'like'};
		var url = CommonUtils.buildUrl('www.123.com' , params);
		expect(url).toBe('www.123.com?q=like');
	});
	
	it("原来有参数，1个参数追加", function() {
		var params = {q : 'like'};
		var url = CommonUtils.buildUrl('www.123.com?a=b' , params);
		expect(url).toBe('www.123.com?a=b&q=like');
	});
});

//describe("test-encodeURL", function() {
//	it("+", function() {
//		var r = CommonUtils.encodeURL('+$+');
//		expect(r).toBe('%2B$%2B');
//	});
//	it("空格", function() {
//		var r = CommonUtils.encodeURL(' $ ');
//		expect(r).toBe('%20$%20');
//	});
//	it("/", function() {
//		var r = CommonUtils.encodeURL('/$/');
//		expect(r).toBe('%2F$%2F');
//	});
//	it("?", function() {
//		var r = CommonUtils.encodeURL('?$?');
//		expect(r).toBe('%3F$%3F');
//	});
//	it("#", function() {
//		var r = CommonUtils.encodeURL('#$#');
//		expect(r).toBe('%23$%23');
//	});
//	it("&", function() {
//		var r = CommonUtils.encodeURL('&$&');
//		expect(r).toBe('%26$%26');
//	});
//	it("=", function() {
//		var r = CommonUtils.encodeURL('=$=');
//		expect(r).toBe('%3D$%3D');
//	});
//	it("\"", function() {
//		var r = CommonUtils.encodeURL('"$"');
//		expect(r).toBe('%22$%22');
//	});
//	it("\'", function() {
//		var r = CommonUtils.encodeURL('\'$\'');
//		expect(r).toBe('%27$%27');
//	});
//	it("\\", function() {
//		var r = CommonUtils.encodeURL('\\$\\');
//		expect(r).toBe('%5C$%5C');
//	});
//});
//
//describe("test-encode", function() {
//	
//	it("null返回null", function() {
//		var r = CommonUtils.encode(null);
//		expect(r).toBe(null);
//	});
//	
//	it("{}返回'{}'", function() {
//		var r = CommonUtils.encode({});
//		expect(r).toBe('{}');
//	});
//	
//	it("简单对象", function() {
//		var r = CommonUtils.encode({key:'key'});
//		expect(r).toBe('{"key":"key"}');
//	});
//	
//	it("日期对象", function() {
//		var r = CommonUtils.encode({key:new Date('1900-12-03 00:00:00')});
//		expect(r).toBe('{"key":"1900-12-03T00:00:00"}');
//	});
//	
//	it("对象包含方法", function() {
//		var obj = {
//				key:'key',
//				fn : function(){}
//		};
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe('{"key":"key"}');
//	});
//	
//	it("对象包含对象", function() {
//		var obj = {
//			sub : {
//				key:'key'
//			}
//		};
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe('{"sub":{"key":"key"}}');
//	});
//	
//	it("对象包含数组", function() {
//		var obj = {
//			sub : [{
//				key:'key'
//			}]
//		};
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe('{"sub":[{"key":"key"}]}');
//	});
//	
//	it("[]返回'[]'", function() {
//		var r = CommonUtils.encode([]);
//		expect(r).toBe('[]');
//	});
//	
//	it("数组包含对象", function() {
//		var obj = [
//			{
//				key:'key'
//			}
//		];
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe('[{"key":"key"}]');
//	});
//	
//	it("数组包含数组", function() {
//		var obj = [
//			 [{
//				key:'key1'
//			 },{
//				key:'key2'
//			 }]
//		];
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe('[[{"key":"key1"},{"key":"key2"}]]');
//	});
//	
//	it("数组包含函数", function() {
//		var obj = [
//			function(){},
//			function(){}
//		];
//		var r = CommonUtils.encode(obj);
//		expect(r).toBe("[null,null]");
//	});
//});
