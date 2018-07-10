package com.codemonkey.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtils {
	// 邮箱地址
	public static final String EMAIL_REG = "^[a-z0-9_+.-]+@([a-z0-9-]+.)+[a-z0-9]{2,4}";
	// 身份证号
	public static final String ID_CARD_REG = "(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}[0-9Xx]$)|"
			+ // 18闰年出生日期的
			"(^[1-9]\\d{5}[12]\\d{3}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}[0-9Xx]$)|"
			+ // 18平年出生日期的
			"(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|[1-2]\\d))\\d{3}$)|"
			+ // 15闰年出生日期的
			"(^[1-9]\\d{5}\\d{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2]\\d|3[0-1])|(04|06|09|11)(0[1-9]|[1-2]\\d|30)|02(0[1-9]|1\\d|2[0-8]))\\d{3}$)"; // 15平年出生日期的
	// 手机号码
	public static final String MOBILE_PHONE_REG = "(^((\\(\\d{3}\\))|(\\d{3}\\-)|(\\(\\+\\d{2}\\)))[12][034578]\\d{9}$)|(^[12][034578]\\d{9}$)";
	// 家庭电话（例如 0411-1234567）
	public static final String FIX_PHONE_REG = "(^[0]\\d{2,3}-\\d{7,8}$)";
	// 必须为数字，字母或下划线
	public static final String NUM_LETTER_UNDERLINE_REG = "^\\w+$";
	// 必须为数字或字母
	public static final String NUM_LETTER_REG = "^[a-z0-9A-Z]+$";
	// 必须为数字或横杆
	public static final String NUM_SPE_REG = "^[0-9|\\-]+$";
	// 必须为大写英文字母
	public static final String CAPITAL_LETTER_REG = "^[A-Z]+$";
	// 必须为非负实数
	public static final String NON_REAL_NUM_REG = "^[0-9]+((\\.{1}?[0-9]{1,13})|(\\.{0}))?$";
	// 必须为正整数（包括0的）
	public static final String INT_REG = "^\\d+$";
	// 必须为除0以外的正整数
	public static final String POS_INT_REG = "^[0-9]*[1-9][0-9]*$";
	// IP地址
	public static final String IP_REG = "^(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})$";
	// 邮编
	public static final String POSTCODE_REG = "^[0-9]{1}(\\d){5}$";
	// 日期
	public static final String DATE_REG = "^\\d{4}-\\d{2}-\\d{2}$";
	// 带有时分秒的日期
	public static final String DATE_TIME_REG = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";

	public static final String T_DATE_TIME_REG = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";
	// 必须为中文
	public static final String CHINESE_REG = "[\\u4e00-\\u9fa5]+";
	// 集团电话
	public static final String GROUP_PHONE_REG = "^\\d{4}$";

	public static final String EXTENSION_PHONE_REG = "^\\d{3,4}$";

	public static final String NAME_REG = "[\\w\\d\\u4e00-\\u9fa5]*";
	// 运算符
	public static final String OPERATOR_REG = "(\\s*[\\+,\\-,\\*,/]{1}\\s*){1}";
	
	// 多元运算符
	public static final String ALL_OPERATOR_REG = "(\\s*[\\+,\\-,\\?,\\:,\\>,\\<,\\=,\\!,\\&,\\|,\\*,/]{1}\\s*){1}";
	
	// 运算符开头
	public static final String START_OPERATOR_REG = "^([\\+,\\-,\\*,/]{1}).*";
	// 运算符结尾
	public static final String END_OPERATOR_REG = ".*([\\+,\\-,\\*,/]{1})$";
	// 数据项开始符号
	public static final String START_ITEM_REG = "@";
	// 系数开始符号
	public static final String START_COEF_REG = "#";
	// 公式开始符号
	public static final String START_FORMULA_REG = "\\$";
	// 平均奖开始符号
	public static final String START_AVG_REG = "~";
	// 有效的项目开头 @ 或 # 或 $ 或 ~
	public static final String START_ALL_ITEM_REG = "[\\@|\\#|\\$|\\~]";
	// 左括号
	public static final char START_BR_REG = '(';
	// 右括号
	public static final char END_BR_REG = ')';
	// 逗号
	public static final String COMMA = ",";
	// 空格
	public static final String EMPTY_REG = "\\s";
	// 空格和括号
	public static final String EMPTY_AND_BR_REG = "[\\s\\(\\)]*";
	// 匹配是否是数据项
	public static final String COEF_REG = "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*"
			+ START_COEF_REG + "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*";
	// 匹配是否是公式
	public static final String FORMULA_REG = "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*"
			+ START_FORMULA_REG + "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*";
	// 匹配是否是系数
	public static final String ITEM_REG = "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*"
			+ START_ITEM_REG + "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*";
	// 匹配是否是平均奖
	public static final String AVG_REG = "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*"
			+ START_AVG_REG + "[\\(\\)\\w\\d\\s\\u4e00-\\u9fa5]*";
	// 匹配是否是数字（含小数）
	public static final String NUMBER_REG = "^[+,-]?\\d+(\\.)*\\d*";
	// 匹配是否是数字（含小数）结尾可以有右括号
	public static final String NUMBER_END_BR_REG = "^[+,-]?\\d+(\\.)*\\d*(\\))*$";
	// 匹配是否是左括号或空格
	public static final String LEFTBR_REG = "(\\s*\\(\\s*)+|\\s+";
	// 逻辑与或
	public static final String LOGOCAL_REG = "[&|<=>]";
	// 逻辑运算符和关系运算符（需要测试）
	public static final String LOGOCALRELATIONAL_REG = "(\\s*([><!]=?|=|&&|\\|\\|)\\s*)";

	public static boolean matches(String reg, String value) {
		
		if(SysUtils.isEmpty(value)){
			return false;
		}
		
		Matcher matcher = buildMatcher(reg, value);
		return matcher.matches();
	}

	public static boolean find(String reg, String value) {
		
		if(SysUtils.isEmpty(value)){
			return false;
		}
		
		Matcher matcher = buildMatcher(reg, value);
		return matcher.find();
	}

	public static List<String> extract(String reg, String value) {
		
		if(SysUtils.isEmpty(value)){
			return null;
		}
		
		return extract(reg, value, 0);
	}

	public static List<String> extract(String reg, String value, int group) {

		if(SysUtils.isEmpty(value)){
			return null;
		}
		
		List<String> list = new ArrayList<String>();
		
		Matcher matcher = buildMatcher(reg, value);

		while (matcher.find()) {
			String s = matcher.group(group);
			list.add(s);
		}
		return list;
	}

	public static Matcher buildMatcher(String reg, String value) {
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(value);
		return matcher;
	}

	public static String group(String reg, String value) {
		if(SysUtils.isEmpty(value)){
			return null;
		}
		return group(reg, value, 0);
	}

	public static String group(String reg, String value, int group) {
		
		if(SysUtils.isEmpty(value)){
			return null;
		}
		
		Matcher matcher = buildMatcher(reg, value);
		if (matcher.find()) {
			return matcher.group(group);
		}
		return null;
	}

}
