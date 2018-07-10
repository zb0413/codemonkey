package com.codemonkey.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codemonkey.utils.ValidateType;

public class ValidateTypeTest {

	@Test
	public void test(){
		//必须不为空
		assertEquals(false , ValidateType.NOT_BLANK.validate(null));
		assertEquals(false , ValidateType.NOT_BLANK.validate(""));
		assertEquals(false , ValidateType.NOT_BLANK.validate(" "));
		assertEquals(true  , ValidateType.NOT_BLANK.validate("aa"));
		
		//邮箱地址
		assertEquals(true  , ValidateType.EMAIL.validate("rudy0127@163.com"));
		//assertEquals(true , ValidateType.EMAIL.validate("rudy0127@163.com111"));
		assertEquals(true  , ValidateType.EMAIL.validate(""));
		assertEquals(false , ValidateType.EMAIL.validate("123123123"));
		assertEquals(false , ValidateType.EMAIL.validate("123abc.com"));
		
		//身份证号
		assertEquals(true  , ValidateType.ID_CARD.validate("210882198601271111"));
		assertEquals(true  , ValidateType.ID_CARD.validate("21088219860127641x"));
		assertEquals(true  , ValidateType.ID_CARD.validate("21088219860127641X"));
		assertEquals(true  , ValidateType.ID_CARD.validate("210882860127641"));
		assertEquals(false , ValidateType.ID_CARD.validate("21088219860127641"));
		assertEquals(false  , ValidateType.ID_CARD.validate("21088219860133641x"));
		assertEquals(false  , ValidateType.ID_CARD.validate("21088219862229641x"));
		
		//手机号码
		assertEquals(true  , ValidateType.MOBILE_PHONE.validate("13478968666"));
		assertEquals(true  , ValidateType.MOBILE_PHONE.validate("(086)13478965555"));
		assertEquals(true  , ValidateType.MOBILE_PHONE.validate("(+86)13478965555"));
		assertEquals(false , ValidateType.MOBILE_PHONE.validate("134789686666"));
		assertEquals(false , ValidateType.MOBILE_PHONE.validate("9999999"));
		assertEquals(false , ValidateType.MOBILE_PHONE.validate("(86)13478965555"));
		
		//固定电话
		assertEquals(true  , ValidateType.FIX_PHONE.validate("0411-5401579"));
		assertEquals(true  , ValidateType.FIX_PHONE.validate("0411-54015791"));
		assertEquals(true  , ValidateType.FIX_PHONE.validate("024-88880000"));
		assertEquals(false  , ValidateType.FIX_PHONE.validate("111-88880000"));
		assertEquals(false  , ValidateType.FIX_PHONE.validate("024-888800000"));
		assertEquals(false  , ValidateType.FIX_PHONE.validate("024-888000"));
		assertEquals(false , ValidateType.FIX_PHONE.validate("124-888800000"));
		assertEquals(false , ValidateType.FIX_PHONE.validate("01124-888800000"));
		assertEquals(false , ValidateType.FIX_PHONE.validate("02488880000"));
		
		//联系电话
		assertEquals(true  , ValidateType.CONTACT_PHONE.validate("0411-66880000"));
		assertEquals(true  , ValidateType.CONTACT_PHONE.validate("15898655555"));
		assertEquals(false , ValidateType.CONTACT_PHONE.validate("64654654"));
		
		//集团电话
		assertEquals(true  , ValidateType.GROUP_PHONE.validate("0411"));
		assertEquals(false , ValidateType.GROUP_PHONE.validate("411"));
		assertEquals(false , ValidateType.GROUP_PHONE.validate("55555"));
		
		//分机电话
		assertEquals(true  , ValidateType.EXTENSION_PHONE.validate("6666"));
		assertEquals(true  , ValidateType.EXTENSION_PHONE.validate("666"));
		assertEquals(false , ValidateType.EXTENSION_PHONE.validate("66"));
		assertEquals(false , ValidateType.EXTENSION_PHONE.validate("66666"));
		
		//必须为数字，字母或下划线
		assertEquals(true  , ValidateType.NUM_LETTER_UNDERLINE.validate("A"));
		assertEquals(true  , ValidateType.NUM_LETTER_UNDERLINE.validate("123"));
		assertEquals(true  , ValidateType.NUM_LETTER_UNDERLINE.validate("sds"));
		assertEquals(true  , ValidateType.NUM_LETTER_UNDERLINE.validate("_"));
		assertEquals(true  , ValidateType.NUM_LETTER_UNDERLINE.validate("aaa_12_BBB"));
		assertEquals(false , ValidateType.NUM_LETTER_UNDERLINE.validate("aaa_12_-BBB"));
		
		//必须为数字或字母
		assertEquals(true  , ValidateType.NUM_LETTER.validate("aa"));
		assertEquals(true  , ValidateType.NUM_LETTER.validate("AA"));
		assertEquals(true  , ValidateType.NUM_LETTER.validate("4a"));
		assertEquals(true  , ValidateType.NUM_LETTER.validate("555"));
		assertEquals(true  , ValidateType.NUM_LETTER.validate("Aa3"));
		assertEquals(false , ValidateType.NUM_LETTER.validate("+-"));
		
		
		//必须为大写英文字母
		assertEquals(true  , ValidateType.CAPITAL_LETTER.validate("ABF"));
		assertEquals(false , ValidateType.CAPITAL_LETTER.validate("aa"));
		assertEquals(false , ValidateType.CAPITAL_LETTER.validate("3.2"));
		
		
		//必须为非负实数
		assertEquals(true  , ValidateType.NON_REAL_NUM.validate("3.2"));
		assertEquals(true  , ValidateType.NON_REAL_NUM.validate("5"));
		assertEquals(true  , ValidateType.NON_REAL_NUM.validate("0"));
		assertEquals(false , ValidateType.NON_REAL_NUM.validate("aa"));
		assertEquals(false , ValidateType.NON_REAL_NUM.validate("-2.6"));
		
		//必须为正整数（包含0）
		assertEquals(false , ValidateType.NON_INT.validate("aa"));
		assertEquals(false , ValidateType.NON_INT.validate("-5"));
		assertEquals(false , ValidateType.NON_INT.validate("-2.3"));
		assertEquals(false , ValidateType.NON_INT.validate("8.9"));
		assertEquals(true  , ValidateType.NON_INT.validate("0"));
		assertEquals(true  , ValidateType.NON_INT.validate("5"));
		
		
		//必须为除0以外正整数
		assertEquals(false , ValidateType.POS_INT.validate("aa"));
		assertEquals(false , ValidateType.POS_INT.validate("-6"));
		assertEquals(false , ValidateType.POS_INT.validate("2.5"));
		assertEquals(true  , ValidateType.POS_INT.validate("109"));
		assertEquals(false , ValidateType.POS_INT.validate("0"));
		
		//IP地址
		assertEquals(true  , ValidateType.IP.validate("192.168.17.1"));
		assertEquals(true  , ValidateType.IP.validate("0.0.0.0"));
		assertEquals(true  , ValidateType.IP.validate("255.255.255.255"));
		assertEquals(false , ValidateType.IP.validate("192.168.17."));
		assertEquals(false , ValidateType.IP.validate("192.168.17"));
		assertEquals(false , ValidateType.IP.validate("10."));
		assertEquals(false , ValidateType.IP.validate("26445687"));
		assertEquals(false , ValidateType.IP.validate("nihao"));
		assertEquals(false , ValidateType.IP.validate("你好！"));
		
		//邮政编码
		assertEquals(true  , ValidateType.POSTCODE.validate("114542"));
		assertEquals(false , ValidateType.POSTCODE.validate("11234"));
		assertEquals(false , ValidateType.POSTCODE.validate("1234567"));
		
		//必须为中文
		assertEquals(false  , ValidateType.CHINESE.validate("114542"));
		assertEquals(false , ValidateType.CHINESE.validate("qEerqw"));
		assertEquals(false , ValidateType.CHINESE.validate("345ewrw"));
		assertEquals(true , ValidateType.CHINESE.validate("大家好"));

	}
}
