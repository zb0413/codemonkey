package com.codemonkey.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.codemonkey.error.SysError;

public class FormulaUtils {
	
	/*
	 * //数据项开始符号 public static final String START_ITEM = "@"; //系数开始符号 public
	 * static final String START_COEF = "#"; //公式开始符号 public static final String
	 * START_FORMULA = "$"; //平均奖开始符号 public static final String START_AVG =
	 * "~";
	 */

	/**
	 * 校验公式是否合法
	 * 
	 * @param str
	 * @param dataItemList
	 * @param coefList
	 */
	public static void validateFormula(String expr) {
		if (SysUtils.isEmpty(expr)) {
			throw new SysError("(E1): 公式是空字符串;");
		}
		// 公式不能以运算符开头
		if (RegUtils.matches(RegUtils.START_OPERATOR_REG, expr)) {
			throw new SysError("(E2): 公式不能以运算符开头;");
		}

		// 公式不能以运算符结尾
		if (RegUtils.matches(RegUtils.END_OPERATOR_REG, expr)) {
			throw new SysError("(E3): 公式不能以运算符结尾;");
		}

		// 括号不匹配
		if (countOf(RegUtils.START_BR_REG, expr) != countOf(
				RegUtils.END_BR_REG, expr)) {
			throw new SysError("(E4): 运算括号'()'不匹配;");
		}

		// 两个运算符不能连续出现
		if (RegUtils.find(RegUtils.OPERATOR_REG + "\\s*"
				+ RegUtils.OPERATOR_REG, expr)) {
			throw new SysError("(E5): 运算符之间不能没有运算项 ;");
		}

		/*
		 * //按照运算符拆分 String[] items = expr.split(RegUtils.OPERATOR_REG);
		 * 
		 * if(items!= null){//逐个分段判断 for(int i = 0 ; i < items.length ; i++){
		 * //判断是否包含数据项( 是否匹配数据项开头，是否匹配系数开头，是否匹配公式开头）
		 * if(RegUtils.matches(RegUtils.ITEM_REG, items[i]) ||
		 * RegUtils.matches(RegUtils.COEF_REG, items[i]) ||
		 * RegUtils.matches(RegUtils.FORMULA_REG, items[i]) ||
		 * RegUtils.matches(RegUtils.AVG_REG, items[i]) ){//判断是否包含数据项 String []
		 * subItems = items[i].split(RegUtils.START_ALL_ITEM_REG);
		 * if(subItems.length >2 ){//该项包含两个数据项目 throw new
		 * SysError("(E5): '"+items[i]+"' 两个运算项之间不能没有运算符;"); }else
		 * if(SysUtils.isNotEmpty(subItems[0]) &&
		 * !RegUtils.matches(RegUtils.LEFTBR_REG, subItems[0])){//数据项左侧不全是空格或左括号
		 * throw new SysError("(E6): '"+items[i]+"'不能识别或缺少运算符;"); } } //判断是不是纯数字
		 * else if(!RegUtils.matches(RegUtils.NUMBER_END_BR_REG,
		 * items[i].replaceAll(" ", ""))){ //不能识别 throw new
		 * SysError("(E7): '"+items[i]+"'不能识别或缺少运算符;"); } } }
		 */
	}

	/**
	 * 校验条件是否合法
	 * 
	 * @param str
	 * @param dataItemList
	 * @param coefList
	 */
	public static void validateCondition(String expr) {
		if (SysUtils.isEmpty(expr)) {
			throw new SysError("(E1): 条件是空字符串;");
		}

		// 条件不能以运算符开头
		if (RegUtils.matches(RegUtils.START_OPERATOR_REG, expr)) {
			throw new SysError("(E2): 条件不能以运算符开头;");
		}

		// 条件不能以运算符结尾
		if (RegUtils.matches(RegUtils.END_OPERATOR_REG, expr)) {
			throw new SysError("(E3): 条件不能以运算符结尾;");
		}

		// 括号不匹配
		if (countOf(RegUtils.START_BR_REG, expr) != countOf(
				RegUtils.END_BR_REG, expr)) {
			throw new SysError("(E4): 运算括号'()'不匹配;");
		}

		// 不能有空格
		if (RegUtils.find(RegUtils.EMPTY_REG, expr)) {
			throw new SysError("(E5): 条件中不能有空格;");
		}

	}

	/**
	 * 分割公式
	 * 
	 * @param str
	 * @param dataItemList
	 * @param coefList
	 * @param formulaList
	 */
	public static void splitFormula(String expr, List<String> itemNameList) {

		if (SysUtils.isEmpty(expr)) {
			return;
		}
		// 按照运算符拆分
		String[] items = expr.split(RegUtils.OPERATOR_REG);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				// 替换空格和括号
				String name = items[i]
						.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
				itemNameList.add(name);
			}
		}
	}

	/**
	 * 分割公式(多元运算)
	 * 
	 * @param str
	 * @param dataItemList
	 * @param coefList
	 * @param formulaList
	 */
	public static void splitAllFormula(String expr, List<String> itemNameList) {

		if (SysUtils.isEmpty(expr)) {
			return;
		}
		// 按照多元运算符拆分
		String[] items = expr.split(RegUtils.ALL_OPERATOR_REG);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				// 替换空格和括号
				String name = items[i].replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
				
				if(SysUtils.isNotEmpty(name)){
					name = name.trim();
					if (!NumberValidationUtils.isRealNumber(name) && !itemNameList.contains(name) && !name.equals("null")) {
						itemNameList.add(name);
					}
				}
			}
		}
	}

	/**
	 * 分割条件
	 * 
	 * @param str
	 * @param dataItemList
	 * @param coefList
	 * @param formulaList
	 */
	public static void splitCondition(String expr, List<String> itemNameList) {

		if (SysUtils.isEmpty(expr)) {
			return;
		}
		// 替换空格和括号
		String temp = expr.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
		// 按照运算符拆分
		String[] items = temp.split(RegUtils.LOGOCALRELATIONAL_REG);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				// 替换数字
				if (!RegUtils.matches(RegUtils.NUMBER_REG, items[i])) {
					itemNameList.add(items[i]);
				}
			}
		}
	}

	/*
	 * 分割判断逻辑表达式
	 * 
	 * @param str
	 * 
	 * @param dataItemList
	 * 
	 * @param coefList
	 * 
	 * @param formulaList
	 * 
	 * public static void splitConditonStr (String expr , List<String>
	 * dataItemNameList , List<String> coefNameList , List<String>
	 * formulaNameList , List<String> avgNameList) {
	 * 
	 * if (SysUtils.isEmpty(expr)) { return; } validateConditonStr(expr);
	 * 
	 * //替换空格括号 String newExpr = expr.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
	 * //替换逻辑运算符 newExpr = newExpr.replaceAll(RegUtils.LOGOCAL_REG,
	 * RegUtils.COMMA); //替换运算符 newExpr =
	 * newExpr.replaceAll(RegUtils.OPERATOR_REG, RegUtils.COMMA); //按逗号分隔
	 * String[] items = newExpr.split(RegUtils.COMMA);
	 * 
	 * if(items!= null){ for(int i = 0 ; i < items.length ; i++){
	 * if(RegUtils.matches(RegUtils.ITEM_REG, items[i])){//判断是否包含数据项 //去掉开头
	 * String name = items[i].replaceAll(RegUtils.START_ITEM_REG, ""); //替换空格和括号
	 * name = name.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
	 * dataItemNameList.add(name); }else if(RegUtils.matches(RegUtils.COEF_REG,
	 * items[i]) ){//判断是否包含系数 //去掉开头 String name =
	 * items[i].replaceAll(RegUtils.START_COEF_REG, ""); //替换空格和括号 name =
	 * name.replaceAll(RegUtils.EMPTY_AND_BR_REG, ""); coefNameList.add(name);
	 * }else if(RegUtils.matches(RegUtils.FORMULA_REG, items[i]) ){//判断是否包含公式
	 * //去掉开头 String name = items[i].replaceAll(RegUtils.START_FORMULA_REG, "");
	 * //替换空格和括号 name = name.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
	 * formulaNameList.add(name); }else if(RegUtils.matches(RegUtils.AVG_REG,
	 * items[i]) ){//判断是否包含平均奖 //去掉开头 String name =
	 * items[i].replaceAll(RegUtils.START_AVG_REG, ""); //替换空格和括号 name =
	 * name.replaceAll(RegUtils.EMPTY_AND_BR_REG, ""); avgNameList.add(name); }
	 * } } }
	 */

	/*
	 * 校验公式是否合法
	 * 
	 * @param str
	 * 
	 * @param dataItemList
	 * 
	 * @param coefList
	 * 
	 * public static void validateConditonStr (String expr ) { if
	 * (SysUtils.isEmpty(expr)) { return; } //括号不匹配
	 * if(countOf(RegUtils.START_BR_REG , expr) != countOf(RegUtils.END_BR_REG ,
	 * expr)){ throw new SysError("(C1): 判断替换条件时运算括号'()'不匹配;出错条件："+expr); }
	 * 
	 * //替换空格括号 String newExpr = expr.replaceAll(RegUtils.EMPTY_AND_BR_REG, "");
	 * //替换逻辑运算符 newExpr = newExpr.replaceAll(RegUtils.LOGOCAL_REG,
	 * RegUtils.COMMA); //替换运算符 newExpr =
	 * newExpr.replaceAll(RegUtils.OPERATOR_REG, RegUtils.COMMA); //按逗号分隔
	 * String[] items = newExpr.split(RegUtils.COMMA);
	 * 
	 * if(items!= null){//逐个分段判断 for(int i = 0 ; i < items.length ; i++){
	 * if(SysUtils.isNotEmpty(items[i])){ //判断是否包含数据项(
	 * 是否匹配数据项开头，是否匹配系数开头，是否匹配公式开头） if(RegUtils.matches(RegUtils.ITEM_REG,
	 * items[i]) || RegUtils.matches(RegUtils.COEF_REG, items[i]) ||
	 * RegUtils.matches(RegUtils.FORMULA_REG, items[i]) ||
	 * RegUtils.matches(RegUtils.AVG_REG, items[i]) ){//判断是否包含数据项 String []
	 * subItems = items[i].split(RegUtils.START_ALL_ITEM_REG);
	 * if(subItems.length >2 ){//该项包含两个数据项目 throw new
	 * SysError("(C2): 判断替换条件时出错：两个运算项之间不能没有运算符; 出错条件："+expr+
	 * "； 出错地点：'"+items[i]+"'; "); } } //判断是不是纯数字 else
	 * if(!RegUtils.matches(RegUtils.NUMBER_END_BR_REG, items[i])){ //不能识别 throw
	 * new SysError("(C3): 判断替换条件时出错：不能识别或缺少运算符; 出错条件："+expr+
	 * "； 出错地点：'"+items[i]+"'; " ); } } } } }
	 */

	private static int countOf(char c, String expr) {

		int count = 0;

		if (SysUtils.isEmpty(expr)) {
			return count;
		}

		for (int i = 0; i < expr.length(); i++) {
			if (expr.charAt(i) == c) {
				count++;
			}
		}

		return count;
	}

	public static String getReplaceStr(String expr, Map<String, Number> params) {
		Set<String> keySet = params.keySet();
		if (SysUtils.isNotEmpty(keySet)) {
			// 替换
			for (String name : keySet) {
				expr = expr.replace(name, params.get(name).toString());
			}
		}

		return expr;
	}

	public static Number calculate(String expr, Map<String, Number> params,
			int scale, RoundingMode roundingMode) {

		Number n = calculate(expr, params);
		
		if (n != null) {
			BigDecimal d = new BigDecimal(n.doubleValue());
			d = d.setScale(scale , roundingMode);
			return d.doubleValue();
		}

		return null;

	}
	
	private static Map<String, Number> buildParamsMap(Map<String, Number> params) {
		Map<String, Number> map = new HashMap<String, Number>();
		
		if(params == null){
			return map;
		}
		
		Set<Entry<String, Number>> set = params.entrySet();
		
		if(SysUtils.isNotEmpty(set)){
			for(Entry<String, Number> e : set){
				map.put(replaceSpecialCharactors(e.getKey()), e.getValue());
			}
		}
		return map;
	}

	private static void validateFormula(String formulaStr, Map<String, Number> params) {
		List<String> itemNameList = new ArrayList<String>();
		FormulaUtils.splitAllFormula(formulaStr, itemNameList);
		
		if(SysUtils.isNotEmpty(itemNameList)){
			for(String p : itemNameList){
				if(params.get(p) == null){
					throw new ArithmeticException("计算公式[" + formulaStr + "]出错，参数[" + p + "]不存在");
//					throw new SysError("计算公式[" + formulaStr + "]出错， 参数[" + p + "]不存在");
				}
			}
		}
	}
	
	private static String replaceSpecialCharactors(String s) {
		if(SysUtils.isNotEmpty(s)){
			return s.replace("（", "_").replace("）", "_")
					.replace("、", "_").replace("±", "_")
					.replace("：", "_")
					.replace("《", "_").replace("》", "_")
					.replace("：", "_")
					.replace("Ⅰ", "_1")
					.trim();
		}
		return "";
	}

	public static Number calculate(String expr, Map<String, Number> params) {
		
		validateFormula(expr, params);
		
		String fomular = replaceSpecialCharactors(expr);
		
		Map<String, Number> p = buildParamsMap(params);
		Number n = (Number) OgnlUtils.objectValue(fomular, p);
		return n;
	}

	public static Number getScaleValue(Number value, int scale,
			RoundingMode roundingMode) {

		BigDecimal d = new BigDecimal(value.doubleValue());
		d = d.setScale(scale, roundingMode);
		return d.doubleValue();
	}

	public static String getErrorMessage(Exception e) {
		e.printStackTrace();
		if (e instanceof SysError) {
			return ((SysError) e).getDetailMessage();
		}
		return "运算数据出现未知错误，一般是由于使用未验证通过的公式计算造成的。请再次校验公式并核对数据后再试。";
	}

	public static boolean isConditionMatch(String condtion,
			Map<String, Number> replacement) {
		condtion = FormulaUtils.getReplaceStr(condtion, replacement);
		condtion += " ? true : false ";
		String result = OgnlUtils.stringValue(condtion,
				new HashMap<String, Number>());
		if ("true".equals(result)) {
			return true;
		}

		return false;
	}

	public static List<String> extractParmas(String expr) {

		String regOper = "[\\+,\\-,\\*,/,\\?,:]{1}";
		String regBracket = "\\(.*\\)";

		List<String> params = new ArrayList<String>();

		if (SysUtils.isEmpty(expr)) {
			return params;
		}
		// 按照运算符拆分
		String[] items = expr.split(regOper);

		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				// 替换空格和括号
				String name = items[i].trim();
				// 是否有括号包围
				while (RegUtils.matches(regBracket, name)) {
					name = name.substring(1, name.length() - 1);
					name = name.trim();
				}

				if (!NumberValidationUtils.isRealNumber(name)) {
					params.add(name);
				}
			}
		}

		return params;
	}
}
