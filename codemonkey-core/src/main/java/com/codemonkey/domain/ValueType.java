package com.codemonkey.domain;

public enum ValueType implements IEnum {

	字符串 , 整数 , 小数 , 百分比;

	@Override
	public String getText() {
		return name();
	}

	@Override
	public String getName() {
		return name();
	}
	
	public static Object[] getNumberTypes(){
		Object[] array = {整数 , 小数 , 百分比};
		return array;
	}
	
}
